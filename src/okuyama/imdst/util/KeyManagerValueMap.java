package okuyama.imdst.util;


import java.util.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import okuyama.base.util.ILogger;
import okuyama.base.util.LoggerFactory;
import okuyama.base.lang.BatchException;
import okuyama.imdst.util.StatusUtil;
import okuyama.imdst.util.io.*;


import org.apache.commons.codec.digest.DigestUtils;


/**
 * KeyとValueを管理する独自Mapクラス.<br>
 * メモリモードとファイルモードで動きが異なる.<br>
 * メモリモード:KeyとValueを親クラスであるHashMapで管理する.<br>
 * ファイルモード:Keyは親クラスのMapに、Valueはファイルに記録する<br>
 *                KeyとValueが格納させている行数を記録している.<br>
 *                行数から、ファイル内からValueを取り出す.<br>
 *
 * @author T.Okuyama
 * @license GPL(Lv3)
 */
public class KeyManagerValueMap extends CoreValueMap implements Cloneable, Serializable {

    private boolean memoryMode = true;

    private transient FileOutputStream fos = null;
    private transient OutputStreamWriter osw = null;
    private transient BufferedWriter bw = null;
    private transient RandomAccessFile raf = null;
    private transient Object sync = new Object();
    private transient boolean vacuumExecFlg = false;
    private transient List vacuumDiffDataList = null;

    private ConcurrentHashMap dataSizeMap = new ConcurrentHashMap();

    private String lineFile = null;
    private String tmpVacuumeLineFile = null;
    private String[] tmpVacuumeCopyMapDirs = null;

    private int lineCount = 0;
    private int oneDataLength = ImdstDefine.dataFileWriteMaxSize;
    private int seekOneDataLength = ImdstDefine.dataFileWriteMaxSize + 1;
    private long lastDataChangeTime = 0L;
    private int nowKeySize = 0;

    private transient boolean readObjectFlg = false;

    private int overSizeDataParallelSize = 2000;
    private Object[] overSizeDataParallelSyncs = new Object[overSizeDataParallelSize];
    // キャッシュ
    private ValueCacheMap valueCacheMap = null;


    // コンストラクタ
    public KeyManagerValueMap(int size, boolean memoryMode, String[] virtualStoreDirs) {

        super(size, new Double(size * 0.9).intValue(), 512, memoryMode, virtualStoreDirs);

        this.memoryMode = memoryMode;
    }


    // コンストラクタ
    public KeyManagerValueMap(String[] dirs, int numberOfDataSize) {

        super(dirs, numberOfDataSize);
        this.memoryMode = false;
    }


    /**
     * 本メソッドは使用前に必ず呼び出す<br>
     * Objectに書き出した後でも必須
     */
    public void initNoMemoryModeSetting(String lineFile) {
        try {
            if (sync == null) 
                sync = new Object();

            readObjectFlg  = true;

            this.tmpVacuumeLineFile = lineFile + ".vacuumtmp";
            this.tmpVacuumeCopyMapDirs = new String[5];
            this.tmpVacuumeCopyMapDirs[0] = lineFile + ".cpmapdir1/";
            this.tmpVacuumeCopyMapDirs[1] = lineFile + ".cpmapdir2/";
            this.tmpVacuumeCopyMapDirs[2] = lineFile + ".cpmapdir3/";
            this.tmpVacuumeCopyMapDirs[3] = lineFile + ".cpmapdir4/";
            this.tmpVacuumeCopyMapDirs[4] = lineFile + ".cpmapdir5/";

            // サイズオーバーのValueを格納するディレクトリを作成
            for (int dirIdx = -19; dirIdx < 20; dirIdx++) {
                File overDataFileDir = new File(lineFile + "_/" + dirIdx);
                overDataFileDir.mkdirs();
            }

            // サイズオーバーファイルへのパラレルアクセス並列数
            for (int overSizeParallelIdx = 0; overSizeParallelIdx < this.overSizeDataParallelSize; overSizeParallelIdx++) {
                this.overSizeDataParallelSyncs[overSizeParallelIdx] = new Object();
            }

            this.fos = new FileOutputStream(new File(lineFile), true);
            this.osw = new OutputStreamWriter(this.fos, ImdstDefine.keyWorkFileEncoding);
            this.bw = new BufferedWriter (osw);
            //this.raf = new RandomAccessFile(new File(lineFile) , "rw");
            this.raf = new CustomRandomAccessFile(new File(lineFile) , "rw");
            
            FileInputStream fis = new FileInputStream(new File(lineFile));
            InputStreamReader isr = new InputStreamReader(fis , ImdstDefine.keyWorkFileEncoding);
            BufferedReader br = new BufferedReader(isr);
            this.lineFile = lineFile;
            int counter = 0;

            // 現在のファイルの終端
            while(br.readLine() != null){
                counter++;
            }
            this.lineCount = counter;
            br.close();
            isr.close();
            fis.close();
            this.nowKeySize = super.size();
        } catch(Exception e) {
            e.printStackTrace();
            // 致命的
            StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - init - Error [" + e.getMessage() + "]");
        }
    }


    /**
     * データを無加工で取り出す.<br>
     * 無加工とは、valueをファイルで保持する場合はpadding文字列をが付加されているが、それを削除せずに返す.<br>
     *
     * @param key
     * @return Object
     * @throw
     */
    public Object getNoCnv(Object key) {
        Object ret = null;

        if (this.memoryMode) {
            ret = super.get(key);
        } else {
            try {

                int i = 0;
                long seekPoint = 0L;
                byte[] buf = new byte[this.oneDataLength];

                // seek値取得
                if ((seekPoint = this.calcSeekDataPoint(key)) == -1) return null;

                synchronized (sync) {
                    // Vacuum中の場合はデータの格納先が変更されている可能性があるので、
                    // ここでチェック
                    if (vacuumExecFlg) {
                        if(seekPoint != this.calcSeekDataPoint(key)) {
                            // 再起呼び出し
                            return get(key);
                        }
                    }

                    this.readDataFile(buf, seekPoint, this.oneDataLength);
                }

                ret = new String(buf, ImdstDefine.keyWorkFileEncoding);
            } catch (Exception e) {
                e.printStackTrace();
                // 致命的
                StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - get - Error [" + e.getMessage() + "]");
                
            }
        }
        return ret;
    }

    /**
     * getをオーバーライド.<br>
     * MemoryモードとFileモードで取得方法が異なる.<br>
     *
     * @param key 登録kye値(全てStringとなる)
     * @return Object 返却値(全てStringとなる)
     */
    public Object get(Object key) {
        Object ret = null;
        if (this.memoryMode) {
            ret = super.get(key);
        } else {
            try {

                // Vacuum中はsyncを呼び出す
                if (vacuumExecFlg) {
                    ret = syncGet(key);
                } else {

                    int i = 0;
                    int readRet = 0;
                    long seekPoint = 0L;
                    byte[] buf = new byte[this.oneDataLength];

                    // seek値取得
                    if ((seekPoint = this.calcSeekDataPoint(key)) == -1) return null;

                    synchronized (sync) {
                        readRet = this.readDataFile(buf, seekPoint, this.oneDataLength);
                        if (readRet == -1) 
                                return null;
                    }

                    if (readRet > this.oneDataLength) {

                        // データ長がデータファイルの1レコード当たりの規定値を超えている
                        // その場合はキー名でファイルが存在するはず
                        synchronized(this.overSizeDataParallelSyncs[((key.toString().hashCode() << 1) >>> 1) % this.overSizeDataParallelSize]) {

                            File overDataFile = new File(this.lineFile + "_/" + (key.toString().hashCode() % 20) + "/" +  DigestUtils.md5Hex(key.toString().getBytes()));
                            if (overDataFile.exists()) {
                                FileInputStream fis = null;
                                InputStreamReader isr = null;
                                BufferedReader br = null;

                                try {

                                    fis = new FileInputStream(overDataFile);
                                    isr = new InputStreamReader(fis , ImdstDefine.keyWorkFileEncoding);
                                    br = new BufferedReader(isr);

                                    StringBuilder retTmpBuf = new StringBuilder(this.oneDataLength);
                                    retTmpBuf.append(new String(buf, 0, this.oneDataLength, ImdstDefine.keyWorkFileEncoding));
                                    retTmpBuf.append(br.readLine());

                                    ret = retTmpBuf.toString();
                                } catch (Exception inE) {
                                    inE.printStackTrace();
                                    // 致命的
                                    StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - Inner File Read[get] - Error [" + inE.getMessage() + "]");
                                } finally {
                                    try {
                                        if (br != null) br.close();
                                        if (isr != null) isr.close();
                                        if (fis != null) fis.close();
                                    } catch (Exception inE2) {
                                    }
                                }
                            } else { 
                                return null;
                            }
                        }
                    } else {

                        for (; i < buf.length; i++) {
                            if (buf[i] == 38) break;
                        }
                        ret = new String(buf, 0, i, ImdstDefine.keyWorkFileEncoding);
                    }
                    buf = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 致命的
                StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - get - Error [" + e.getMessage() + "]");
            }
        }

        return ret;
    }


    // Vacuum中に使用するsynchronizedしながらGetする
    private Object syncGet(Object key) {

        Object ret = null;

        try{
            int i = 0;
            byte[] buf = new byte[this.oneDataLength];
            long seekPoint = 0L;
            int readRet = 0;

            synchronized (sync) {

                // Vacuum中はMap内の行数指定と実際のデータファイルでの位置が異なる場合があるため、
                // Vacuum中で且つ、Mapを更新中の場合はここで同期化する。
                // seek値取得
                if ((seekPoint = this.calcSeekDataPoint(key)) == -1) return null;

                readRet = this.readDataFile(buf, seekPoint, this.oneDataLength);

                if (readRet > this.oneDataLength) {

                    // データ長がデータファイルの1レコード当たりの規定値を超えている
                    // その場合はキー名でファイルが存在するはず
                    synchronized(this.overSizeDataParallelSyncs[((key.toString().hashCode() << 1) >>> 1) % this.overSizeDataParallelSize]) {

                        File overDataFile = null;
                        FileInputStream fis = null;
                        InputStreamReader isr = null;
                        BufferedReader br = null;

                        try {
                            overDataFile = new File(this.lineFile + "_/" + (key.toString().hashCode() % 20) + "/" +  DigestUtils.md5Hex(key.toString().getBytes()));
                            fis = new FileInputStream(overDataFile);
                            isr = new InputStreamReader(fis , ImdstDefine.keyWorkFileEncoding);
                            br = new BufferedReader(isr);
                            StringBuilder retTmpBuf = new StringBuilder(this.oneDataLength);

                            retTmpBuf.append(new String(buf, 0, this.oneDataLength, ImdstDefine.keyWorkFileEncoding));
                            retTmpBuf.append(br.readLine());

                            ret = retTmpBuf.toString();
                        } catch (Exception inE) {
                            inE.printStackTrace();
                            // 致命的
                            StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - syncGet - Inner File Read Error [" + inE.getMessage() + "]");
                        } finally {
                            try {
                                if (br != null) br.close();
                                if (isr != null) isr.close();
                                if (fis != null) fis.close();

                            } catch (Exception inE2) {
                            }
                        }
                    }
                } else {

                    for (; i < buf.length; i++) {
                        if (buf[i] == 38) break;
                    }
                    ret = new String(buf, 0, i, ImdstDefine.keyWorkFileEncoding);
                }

                buf = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 致命的
            StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - syncGet - Error [" + e.getMessage() + "]");
            
        }
        return ret;
    }


    /**
     * putをオーバーライド.<br>
     * MemoryモードとFileモードで保存方法が異なる.<br>
     *
     * @param key 登録kye値(全てStringとなる)
     * @param value 登録value値(全てStringとなる)
     * @return Object 返却値(Fileモード時は返却値は常にnull)
     */
    public Object put(Object key, Object value) {

        Object ret = null;
        this.totalDataSizeCalc(key, value);

        if (this.memoryMode) {
            ret = super.put(key, value);
        } else {

            StringBuilder writeBuf = new StringBuilder(this.oneDataLength + 2);
            int valueSize = (value.toString()).length();

            try {

                if (readObjectFlg == true) {

                    long seekPoint = 0L;
                    boolean overSizeFlg = false;


                    if (((String)value).length() > this.oneDataLength) {
                        writeBuf.append(((String)value).substring(0, (this.oneDataLength)));
                        overSizeFlg = true;
                        valueSize = this.oneDataLength;
                    } else {
                        writeBuf.append((String)value);
                    }

                    // 渡されたデータが固定の長さ分ない場合は足りない部分を補う
                    // 足りない文字列は固定の"&"で補う(38)
                    byte[] appendDatas = new byte[this.oneDataLength - valueSize];

                    for (int i = 0; i < appendDatas.length; i++) {
                        appendDatas[i] = 38;
                    }

                    writeBuf.append(new String(appendDatas));
                    writeBuf.append("\n");


                    if ((seekPoint = this.calcSeekDataPoint(key)) == -1) {

                        // 書き込む行を決定
                        synchronized (sync) {

                            if (vacuumExecFlg) {
                                // Vacuum差分にデータを登録
                                Object[] diffObj = {"1", (String)key, (String)value};
                                this.vacuumDiffDataList.add(diffObj);
                            }

                            this.bw.write(writeBuf.toString());
                            this.bw.flush();
                            this.lineCount++;
                            super.put(key, new Integer(this.lineCount));
                            this.nowKeySize = super.size();
                        }
                    } else {

                        // すでにファイル上に存在する
                        synchronized (sync) {
                            if (vacuumExecFlg) {
                                // Vacuum差分にデータを登録
                                Object[] diffObj = {"1", key, value};
                                this.vacuumDiffDataList.add(diffObj);
                            }

                            if (raf != null) {
                                raf.seek(seekPoint);
                                raf.write(writeBuf.toString().getBytes(), 0, this.oneDataLength);
                            }
                        }
                    }

                    // サイズオーバーの場合
                    if (overSizeFlg) {

                        // データ長が8192を超えている
                        // その場合はキー名でファイルが存在するはず
                        synchronized(this.overSizeDataParallelSyncs[((key.toString().hashCode() << 1) >>> 1) % this.overSizeDataParallelSize]) {

                            File overDataFile = new File(this.lineFile + "_/" + (key.toString().hashCode() % 20) + "/" +  DigestUtils.md5Hex(key.toString().getBytes()));
                            BufferedWriter overBw = null;
                            try {

                                overBw = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(overDataFile, false), ImdstDefine.keyWorkFileEncoding));
                                overBw.write(((String)value).substring(this.oneDataLength, ((String)value).length()));
                                overBw.flush();
                            } catch (Exception inE) {
                                inE.printStackTrace();
                                // 致命的
                                StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - Inner File Write - Error [" + inE.getMessage() + "]");
                            } finally {
                                try {
                                    if (overBw != null) overBw.close();
                                } catch (Exception inE2) {
                                }
                            }
                        }
                    }
                } else {
                    super.put(key, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 致命的
                StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - put - Error [" + e.getMessage() + "]");
            }
        }
        return ret;
    }



    /**
     * removeをオーバーライド.<br>
     * MemoryモードとFileモード両方で同じ動きをする.<br>
     *
     * @param key 削除kye値(全てStringとなる)
     * @return Object 返却値
     */
    public Object remove(Object key) {
        Object ret = null;

        synchronized (sync) {
            this.totalDataSizeCalc(key, null);
            ret = super.remove(key);
            this.nowKeySize = super.size();

            if (vacuumExecFlg) {

                Object diffObj[] = {"2", (String)key};
                this.vacuumDiffDataList.add(diffObj);
            }
        }

        return ret;
    }

    /**
     * containsKeyをオーバーライド.<br>
     * MemoryモードとFileモード両方で同じ動きをする.<br>
     *
     * @param key 登録kye値(全てStringとなる)
     * @return boolean 返却値
     */
    public boolean containsKey(Object key) {
        if (vacuumExecFlg) {
            synchronized (sync) {
                return super.containsKey(key);
            }
        }

        return super.containsKey(key);
    }


    private void totalDataSizeCalc(Object key, Object value) {
        if (!ImdstDefine.calcSizeFlg) return;

        long addSize = 0L;

        if (value != null) addSize = ((String)key).length() + ((String)value).length();

        if (addSize != 0L)
            addSize = 2 * (addSize) + 38 + 2 + 32;

        String unique = null;
        String keyStr = (String)key;
        int beforeSize = 0;
        AtomicLong size = null;
        Object val = this.get(key);

       if(keyStr.indexOf("#") == 0) {

            unique = keyStr.substring(0, 6);
        } else {
            unique = "all";
        }

        if (val != null) {
            beforeSize = ((String)val).length();
            beforeSize = beforeSize + ((String)key).length();
            beforeSize = 2 * (beforeSize) + 38 + 2 + 32;
            beforeSize = beforeSize * -1;
        }

        if(!dataSizeMap.containsKey(unique)) {
            size = new AtomicLong(0L);
            dataSizeMap.put(unique, size);
        } else {
            size = (AtomicLong)dataSizeMap.get(unique);
        }

        size.getAndAdd(beforeSize);
        size.getAndAdd(addSize);
    }


    public long getDataUseSize(String unique) {

        AtomicLong size = new AtomicLong(0L);

        if (unique == null) unique = "all";

        if(dataSizeMap.containsKey(unique)) {

            size = (AtomicLong)dataSizeMap.get(unique);
        }

        return size.longValue();
    }


    public String[] getAllDataUseSize() {

        if (dataSizeMap == null || dataSizeMap.size() == 0) return null;

        String[] sizeList = new String[dataSizeMap.size()];
        Set entrySet = dataSizeMap.entrySet();
        Iterator entryIte = entrySet.iterator(); 
        int idx = 0;
        while(entryIte.hasNext()) {

            Map.Entry obj = (Map.Entry)entryIte.next();
            if (obj == null) continue;

            String key = (String)obj.getKey();
            AtomicLong size = (AtomicLong)obj.getValue();

            sizeList[idx] = key + "=" + size.toString();
            idx++;
        }

        return sizeList;
    }


    /**
     * データファイルの不要領域を掃除して新たなファイルを作りなおす.<br>
     * 方法はMapを一時Mapをつくり出して、現在のsuperクラスのMapから全てのKeyを取り出し、<br>
     * そのキー値を使用してvalueを自身から取得し、新しいDataファイルに書き出すと同時に、<br>
     * 一時MapにKeyと書き込んだファイル内の行数をputする.<br>
     * 全てのKeyの取得が終わったタイミングで新しいファイルには、フラグメントはないので、<br>
     * 次に、同期を実施し同期中に、superのMapをクリアし、一時Mapの内容をsuperのMapにputする.<br>
     * これでsuperのMapはフラグメントの存在しない新しいファイルの行数を保持できているので、
     * あとは元のデータファイルを削除し、新しく作成したフラグメントのないファイルをデータファイル名に
     * リネームすれば、Vacuum完了.<br>
     */
    public boolean vacuumData() {
        boolean ret = false;
        FileOutputStream tmpFos = null;
        OutputStreamWriter tmpOsw = null;
        BufferedWriter tmpBw = null;
        RandomAccessFile raf = null;
        Map vacuumWorkMap = null;
        boolean userMap = false;
        String dataStr = null;
        Set entrySet = null;
        Iterator entryIte = null;
        String key = null;
        int putCounter = 0;


        synchronized (sync) {

            if (this.vacuumDiffDataList != null) {
                this.vacuumDiffDataList.clear();
                this.vacuumDiffDataList = null;
            }

            this.vacuumDiffDataList = new FileBaseDataList(this.tmpVacuumeLineFile);
            vacuumExecFlg = true;
        }

        //vacuumWorkMap = new ConcurrentHashMap(super.size());
        if (JavaSystemApi.getUseMemoryPercent() > 40) {
            userMap = true;
            vacuumWorkMap = new FileBaseDataMap(this.tmpVacuumeCopyMapDirs, super.size(), 0.20);
        } else {
            vacuumWorkMap = new HashMap(super.size());
        }

        try {

            tmpFos = new FileOutputStream(new File(this.lineFile + ".tmp"), true);
            tmpOsw = new OutputStreamWriter(tmpFos, ImdstDefine.keyWorkFileEncoding);
            tmpBw = new BufferedWriter(tmpOsw);
            raf = new RandomAccessFile(new File(this.lineFile) , "r");

            entrySet = super.entrySet();
            entryIte = entrySet.iterator();

            while(entryIte.hasNext()) {

                Map.Entry obj = (Map.Entry)entryIte.next();
                key = (String)obj.getKey();
                if (key != null && (dataStr = (String)getNoCnv(key)) != null) {
                    tmpBw.write(dataStr);
                    tmpBw.write("\n");
                    putCounter++;
                    vacuumWorkMap.put(key, new Integer(putCounter).toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 致命的
            StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - vacuumData - Error [" + e.getMessage() + "]");
        } finally {
            try {
                // 正常終了の場合のみ、ファイルを変更
                if (StatusUtil.getStatus() == 0)  {

                    // 新ファイルflush
                    tmpBw.flush();
                    //
                    tmpBw.close();
                    tmpOsw.close();
                    tmpFos.close();

                    synchronized (sync) {

                        raf.close();

                        if(this.raf != null) this.raf.close();
                        if(this.bw != null) this.bw.close();
                        if(this.osw != null) this.osw.close();
                        if(this.fos != null) this.fos.close();

                        File dataFile = new File(this.lineFile);
                        if (dataFile.exists()) {
                            dataFile.delete();
                        }
                        dataFile = null;
                        // 一時KeyMapファイルをKeyMapファイル名に変更
                        File tmpFile = new File(this.lineFile + ".tmp");
                        tmpFile.renameTo(new File(this.lineFile));

                        // superのMapを初期化
                        super.clear();

                        // workMapからデータコピー
                        Integer workMapData = null;
                        Set workEntrySet = vacuumWorkMap.entrySet();
                        Iterator workEntryIte = workEntrySet.iterator();
                        String workKey = null;

                        while(workEntryIte.hasNext()) {

                            Map.Entry obj = (Map.Entry)workEntryIte.next();
                            workKey = (String)obj.getKey();
                            if (workKey != null) {
                                workMapData = new Integer((String)vacuumWorkMap.get(workKey));
                                super.put(workKey, workMapData);
                            }
                        }

                        // サイズ格納
                        this.nowKeySize = super.size();
                        // ファイルポインタ初期化
                        this.initNoMemoryModeSetting(this.lineFile);


                        // Vacuum中でかつ、synchronized前に登録、削除されたデータを追加登録
                        int vacuumDiffDataSize = this.vacuumDiffDataList.size();

                        if (vacuumDiffDataSize > 0) {

                            Object[] diffObj = null;
                            for (int i = 0; i < vacuumDiffDataSize; i++) {

                                // 差分リストからデータを作成
                                diffObj = (Object[])this.vacuumDiffDataList.get(i);
                                if (diffObj[0].equals("1")) {
                                    // put
                                    put(diffObj[1], diffObj[2]);
                                } else if (diffObj[0].equals("2")) {
                                    // remove
                                    remove(diffObj[1]);
                                }
                            }
                        }

                        this.vacuumDiffDataList.clear();
                        this.vacuumDiffDataList = null;

                        if (userMap) {
                            ((FileBaseDataMap)vacuumWorkMap).finishClear();
                        }
                        vacuumWorkMap = null;

                        // Vacuum終了をマーク
                        vacuumExecFlg = false;
                        ret = true;
                    }
                }
            } catch(Exception e2) {
                e2.printStackTrace();
                try {
                    File tmpFile = new File(this.lineFile + ".tmp");
                    if (tmpFile.exists()) {
                        tmpFile.delete();
                    }
                } catch(Exception e3) {
                    e3.printStackTrace();
                    // 致命的
                    StatusUtil.setStatusAndMessage(1, "KeyManagerValueMap - vacuumData - Error [" + e3.getMessage() + e3.getMessage() + "]");
                }
            }
        }

        return ret;
    }


    public void close() {
        try {
            synchronized (sync) {
                if(this.raf != null) this.raf.close();
                if(this.bw != null) this.bw.close();
                if(this.osw != null) this.osw.close();
                if(this.fos != null) this.fos.close();
            }
        } catch(Exception e3) {
        }
    }

    /**
     * Diskモード時にデータストリームを閉じて、データファイルを削除する.<br>
     *
     * @throw Exception
     */
    public void deleteMapDataFile() throws Exception{
        try {
            synchronized (sync) {
                if(this.raf != null) {
                    this.raf.close();
                    this.raf = null;
                }

                if(this.bw != null) {
                    this.bw.close();
                    this.bw = null;
                }

                if(this.osw != null) {
                    this.osw.close();
                    this.osw = null;
                }

                if(this.fos != null) {
                    this.fos.close();
                    this.fos = null;
                }

                File dataFile = new File(this.lineFile);
                if(dataFile.exists()) {
                    dataFile.delete();
                }
            }
        } catch(Exception e3) {
            e3.printStackTrace();
            throw e3;
        }
    }


    /**
     * 読み込み開始位置を渡すことでデータファイルからValue値を読み込む.<br>
     * 返却値は読み込んだデータ数.<br>
     * 1Valueが終端に達していない場合は、読み込み指定された値の+1を返す.<br>
     * 読み込みに失敗した場合は-1を返す.<br>
     *
     * @param buf 読み込み用Buffer
     * @param seekPoint seek値
     * @param readLength 読み込み指定地
     * @return 読み込んだデータサイズ
     */
    private int readDataFile(byte[] buf, long seekPoint, int readLength) throws Exception {
        int ret = readLength;

        if (raf != null) {

            raf.seek(seekPoint);
            raf.read(buf, 0, this.oneDataLength);
        } else {
            return -1;
        }


        if (buf[buf.length - 1] != 38 && buf[buf.length - 2] != 33 && buf[buf.length - 1] != 48) ret++;

        return ret;
    }


    /**
     * Key値を渡すことでそのKeyの対となるValueがデータファイルのどこにあるかを.<br>
     * データファイル中のバイト位置で返す.<br>
     *
     * @param key Key値
     * @return long ファイル中の開始位置 データが存在しない場合は-1が返却される
     */
    private long calcSeekDataPoint(Object key) {

        Integer lineInteger = (Integer)super.get(key);
        int line = 0;
        if (lineInteger != null) {
            line = lineInteger.intValue();
        } else {
            return -1;
        }

        // seek計算
        return new Long(this.seekOneDataLength).longValue() * new Long((line - 1)).longValue();
    }


    /**
     * getKeySize.<br>
     *
     * @param
     * @return int
     * @throws
     */
    public int getKeySize() {
        return this.nowKeySize;
    }


    /**
     * getAllDataCount.<br>
     *
     * @param
     * @return int
     * @throws
     */
    public int getAllDataCount() {
        return this.lineCount;
    }


    /**
     * データを変更した最終時間を記録する.<br>
     * @param time 変更時間
     */
    public void setKLastDataChangeTime(long time) {
        this.lastDataChangeTime = time;
    }

    /**
     * データを変更した最終時間を取得する.<br>
     * @return long 変更時間
     */
    public long getKLastDataChangeTime() {
        return this.lastDataChangeTime;
    }
}
