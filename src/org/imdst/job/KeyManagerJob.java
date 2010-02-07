package org.imdst.job;

import java.io.*;
import java.net.*;

import org.batch.lang.BatchException;
import org.batch.job.AbstractJob;
import org.batch.job.AbstractHelper;
import org.batch.job.IJob;
import org.batch.util.ILogger;
import org.batch.util.LoggerFactory;
import org.imdst.util.KeyMapManager;
import org.imdst.util.StatusUtil;

/**
 * keyとValueの関係を管理するJob、自身でポートを上げて待ち受ける<br>
 *
 * @author T.Okuyama
 * @license GPL(Lv3)
 */
public class KeyManagerJob extends AbstractJob implements IJob {

    // ポート番号
    private int portNo = 5554;

    // サーバーソケット
    ServerSocket serverSocket = null;

    // KeyMapManagerインスタンス
    private KeyMapManager keyMapManager = null;

    /**
     * Logger.<br>
     */
    private static ILogger logger = LoggerFactory.createLogger(KeyManagerJob.class);

    /**
     * 初期化メソッド定義
     */
    public void initJob(String initValue) {
        logger.debug("KeyManagerJob - initJob - start");

        this.portNo = Integer.parseInt(initValue);

        logger.debug("KeyManagerJob - initJob - end");
    }

    /** 
     * Jobメイン処理定義
     */
    public String executeJob(String optionParam) throws BatchException {
        logger.debug("KeyManagerJob - executeJob - start");

        String ret = SUCCESS;

        Object[] helperParams = null;
        String[] keyMapFiles = null;

        String keySizeStr = null;
        int keySize = 500000;

        boolean memoryMode = false;
        String memoryModeStr = null;

        Socket socket = null;

        try{

            // Option値を分解
            keyMapFiles = optionParam.split(",");

            // KeyMapManagerの設定値を取得
            memoryModeStr = super.getPropertiesValue(super.getJobName() + ".memoryMode");
            keySizeStr = super.getPropertiesValue(super.getJobName() + ".keySize");

            
            if (memoryModeStr != null && memoryModeStr.equals("true")) memoryMode = true;
            if (keySizeStr != null) keySize = Integer.parseInt(keySizeStr);

            this.keyMapManager = new KeyMapManager(keyMapFiles[0], keyMapFiles[1], memoryMode, keySize);
            this.keyMapManager.start();

            // サーバソケットの生成
            this.serverSocket = new ServerSocket(this.portNo);
            // 共有領域にServerソケットのポインタを格納
            super.setJobShareParam(super.getJobName() + "_ServeSocket", this.serverSocket);

            while (true) {
                if (StatusUtil.getStatus() == 1 || StatusUtil.getStatus() == 2) break;
                try {

                    // クライアントからの接続待ち
                    logger.debug("KeyManagerJob - ServerSocket - Access Wait");
                    socket = serverSocket.accept();
                    logger.debug(socket.getInetAddress() + " ACCESS");
                    helperParams = new Object[2];
                    helperParams[0] = this.keyMapManager;
                    helperParams[1] = socket;
                    super.executeHelper("KeyManagerHelper", helperParams);
                } catch (Exception e) {
                    if (StatusUtil.getStatus() == 2) {
                        logger.info("KeyManagerJob - executeJob - ServerEnd");
                        break;
                    }
                    logger.error(e);
                }
            }
        } catch(Exception e) {
            logger.error("KeyManagerJob - executeJob - Error", e);
            throw new BatchException(e);
        }

        //logger.debug("KeyManagerJob - executeJob - end");
        return ret;
    }


}