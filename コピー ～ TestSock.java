import java.util.*;
import java.io.*;
import java.net.*;

import org.imdst.client.ImdstKeyValueClient;
import org.batch.lang.BatchException;

public class TestSock {
    public static void main(String[] args) {
        try {
            if (args == null || args.length ==0) {
                System.out.println("{�L�[�l�������ŌJ��Ԃ������ϓ������ēo�^}                 ������{args[0]=1,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{�L�[�l�������ŌJ��Ԃ������ϓ������Ď擾}                 ������{args[0]=2,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{Tag��4�p�^�[���Ŏ����I�ɕϓ������ăL�[�l�͎����ϓ��œo�^} ������{args[0]=3,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{Tag�l���Œ�1�p�^�[����Key�Q���擾}                        ������{args[0]=4,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{Client���g�p���āu1�v�̏������s��}                        ������{args[0]=5,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{Client���g�p���āu2�v�̏������s��}                        ������{args[0]=6,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{Client���g�p���āu3�v�̏������s��}                        ������{args[0]=7,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���}");
                System.out.println("{Client���g�p���Ďw�肵��Tag�̃L�[�l���w��񐔎擾}        ������{args[0]=8,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���,args[4]=�w��Tag�l}");
                System.out.println("{Client���g�p���Ďw�肵���t�@�C�����w�肵���L�[�ŕۑ�����} ������{args[0]=9,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���,args[4]=�t�@�C����,args[5]=�L�[�l}");
                System.out.println("{Client���g�p���Ďw�肵���L�[�Ńo�C�i���f�[�^���擾���ăt�@�C��������} ������{args[0]=10,args[1]=�T�[�o��,args[2]=port�ԍ�,args[3]=�����J��Ԃ���,args[4]=�쐬�t�@�C����,args[5]=�L�[�l}");
                System.exit(0);
            }
            int port = Integer.parseInt(args[2]);

            if (args[0].equals("1")){
                // �ۑ�����(�L�[�FfileNames1-100000)
                // �\�P�b�g�𐶐�
                Socket socket = new Socket(args[1], port);


                OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream() , "UTF-8");
                PrintWriter pw = new PrintWriter(new BufferedWriter(osw));


                InputStreamReader isr = new InputStreamReader(socket.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(isr);

                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    String paramsStr = "";
                    paramsStr = "1" + "#imdst3674#" + new Integer(("fileNames" + i).hashCode()).toString() + "#imdst3674#" + "dataNode:" + args[1];
                    pw.println(paramsStr);
                    pw.flush();


                    String retParam = br.readLine();

                    //String[] retParams = retParam.split("#imdst3674#");
                    
                    //System.out.println(retParams[0]);
                    //System.out.println(retParams[1]);
                }
                long end = new Date().getTime();
                System.out.println((end - start));

            } else if (args[0].equals("2")){
                // �擾
                // �\�P�b�g�𐶐�
                Socket socket = new Socket(args[1], port);
                // �N���C�A���g�ւ̃A�E�g�v�b�g
                OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream() , "UTF-8");
                PrintWriter pw = new PrintWriter(new BufferedWriter(osw));

                // �N���C�A���g����̃C���v�b�g
                InputStreamReader isr = new InputStreamReader(socket.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(isr);

                long start = new Date().getTime();
                String retParam = null;
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {

                    String paramStr = "2" + "#imdst3674#" + new Integer(("fileNames" + i).hashCode()).toString();

                    //long start2 = new Date().getTime();
                    pw.println(paramStr);
                    pw.flush();
                    //long end2 = new Date().getTime();
                    //System.out.println("writeTime[" + (end2 - start2) + "]");

                    //long start3 = new Date().getTime();
                    retParam = (String)br.readLine();
                    //long end3 = new Date().getTime();
                    //System.out.println("readTime[" + (end3 - start3) + "]");

                    //String[] retParams = retParam.split("#imdst3674#");

                    //System.out.println(retParams[0]);
                    //System.out.println(retParams[1]);
                    //if (retParams.length > 2) {
                    //    System.out.println(retParams[2]);
                    //}
                }
                long end = new Date().getTime();
                System.out.println((end - start));

                String[] retParams = retParam.split("#imdst3674#");

                System.out.println(retParams[0]);
                System.out.println(retParams[1]);
                if (retParams.length > 2) {
                    System.out.println(retParams[2]);
                }

            } else if (args[0].equals("3")){
                // Tag�ۑ�����(�L�[�FfileNames1-100000)
                // �\�P�b�g�𐶐�
                Socket socket = new Socket(args[1], port);


                OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream() , "UTF-8");
                PrintWriter pw = new PrintWriter(new BufferedWriter(osw));


                InputStreamReader isr = new InputStreamReader(socket.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(isr);

                long start = new Date().getTime();
                int num = 0;
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    String paramsStr = "";
                    if (num == 0) {
                        paramsStr = "3" + "#imdst3674#" + new Integer(("TagNum" + num).hashCode()).toString() + "#imdst3674#" + ("fileNames" + i);
                        num = 1;
                    } else if (num == 1) {
                        paramsStr = "3" + "#imdst3674#" + new Integer(("TagNum" + num).hashCode()).toString() + "#imdst3674#" + ("fileNames" + i);
                        num = 2;
                    } else if (num == 2) {
                        paramsStr = "3" + "#imdst3674#" + new Integer(("TagNum" + num).hashCode()).toString() + "#imdst3674#" + ("fileNames" + i);
                        num = 3;
                    } else if (num == 3) {
                        paramsStr = "3" + "#imdst3674#" + new Integer(("TagNum" + num).hashCode()).toString() + "#imdst3674#" + ("fileNames" + i);
                        num = 0;
                    }
                    pw.println(paramsStr);
                    pw.flush();


                    String retParam = br.readLine();

                    //String[] retParams = retParam.split("#imdst3674#");
                    
                    //System.out.println(retParams[0]);
                    //System.out.println(retParams[1]);
                }
                long end = new Date().getTime();
                System.out.println((end - start));

            } else if (args[0].equals("4")) {
                // �擾
                // �\�P�b�g�𐶐�
                Socket socket = new Socket(args[1], port);
                // �N���C�A���g�ւ̃A�E�g�v�b�g
                OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream() , "UTF-8");
                PrintWriter pw = new PrintWriter(new BufferedWriter(osw));

                // �N���C�A���g����̃C���v�b�g
                InputStreamReader isr = new InputStreamReader(socket.getInputStream(),"UTF-8");
                BufferedReader br = new BufferedReader(isr);

                long start = new Date().getTime();
                String retParam = null;
                //for (int i = 0; i < Integer.parseInt(args[3]);i++) {

                    String paramStr = "4" + "#imdst3674#" + new Integer(("TagNum1").hashCode()).toString();

                    //long start2 = new Date().getTime();
                    pw.println(paramStr);
                    pw.flush();
                    //long end2 = new Date().getTime();
                    //System.out.println("writeTime[" + (end2 - start2) + "]");

                    long start3 = new Date().getTime();
                    retParam = (String)br.readLine();
                    long end3 = new Date().getTime();
                    System.out.println("readTime[" + (end3 - start3) + "]");

                    String[] retParams = retParam.split("#imdst3674#");

                    System.out.println(retParams[0]);
                    System.out.println(retParams[1]);
                    if (retParams.length > 2) {
                        System.out.println(retParams[2]);
                    }
                //}
                long end = new Date().getTime();
                System.out.println((end - start));
            } else if (args[0].equals("5")) {

                // ImdstKeyValueClient���g�p���ăf�[�^��ۑ�(Tag�Ȃ�)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);

                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    if (!imdstKeyValueClient.setValue("cookie_dfa5647ad76" + new Integer(i).toString(), "http://yahoo.co.jp/abc1/efg1/" + new Integer(i).toString())) {
                        System.out.println("ImdstKeyValueClient - error");
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start));
                imdstKeyValueClient.close();
            } else if (args[0].equals("6")) {

                // ImdstKeyValueClient���g�p���ăf�[�^���擾(Key�̂�)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] ret = null;
                long start = new Date().getTime();
                
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    ret = imdstKeyValueClient.getValue("cookie_dfa5647ad76" + new Integer(i).toString());
                    if (ret[0].equals("true")) {
                        // �f�[�^�L��
                        //System.out.println(ret[1]);
                    } else if (ret[0].equals("false")) {
                        System.out.println("�f�[�^�Ȃ�");
                    } else if (ret[0].equals("error")) {
                        System.out.println(ret[1]);
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start));
                System.out.println(ret[1]);
                imdstKeyValueClient.close();
            } else if (args[0].equals("7")) {

                // ImdstKeyValueClient���g�p���ăf�[�^��ۑ�(Tag����)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] tag1 = {"tag1"};
                String[] tag2 = {"tag1","tag2"};
                String[] tag3 = {"tag1","tag2","tag3"};
                String[] tag4 = {"tag4"};
                String[] setTag = null;
                int counter = 0;

                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    if (counter == 0) {
                        setTag = tag1;
                        counter++;
                    } else if (counter == 1) {
                        setTag = tag2;
                        counter++;
                    } else if (counter == 2) {
                        setTag = tag3;
                        counter++;
                    } else if (counter == 3) {
                        setTag = tag4;
                        counter = 0;
                    }

                    if (!imdstKeyValueClient.setValue("session_klo657pf09" + new Integer(i).toString(), setTag, "http://google.com/hij1/klm1/" + new Integer(i).toString())) {
                        System.out.println("ImdstKeyValueClient - error");
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start));
                imdstKeyValueClient.close();

            } else if (args[0].equals("8")) {

                // ImdstKeyValueClient���g�p���ăf�[�^���擾(Tag�ł̎擾)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] keys = null;
                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                    Object[] ret = imdstKeyValueClient.getTagKeys(args[4]);
                    if (ret[0].equals("true")) {
                        // �f�[�^�L��
                        keys = (String[])ret[1];
                        /*for (int i = 0; i < keys.length; i++) {
                            System.out.println(keys[i]);
                        }*/
                    } else if (ret[0].equals("false")) {
                        System.out.println("�f�[�^�Ȃ�");
                    } else if (ret[0].equals("error")) {
                        System.out.println(ret[1]);
                    }
                }

                if (keys != null) {
                    for (int ii = 0; ii < keys.length; ii++) {
                        System.out.println(keys[ii]);
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start));
                imdstKeyValueClient.close();

            } else if (args[0].equals("9")) {

                // ImdstKeyValueClient���t�@�C�����L�[�l�ŕۑ�����
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] keys = null;
                long start = new Date().getTime();
                // args[4]�̓t�@�C�����Aargs[5]�̓L�[�l
                for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                    // �t�@�C�����o�C�i���œǂݍ���
                    byte[] fileByte = null;
                    File file = new File(args[4]);
                    fileByte = new byte[new Long(file.length()).intValue()];
                    FileInputStream fis = new FileInputStream(file);
                    fis.read(fileByte, 0, fileByte.length);
                    if (!imdstKeyValueClient.setByteValue(args[5], fileByte)) {
                        System.out.println("ImdstKeyValueClient - error");
                    }
                    fis.close();
                }
                long end = new Date().getTime();
                System.out.println((end - start));
                imdstKeyValueClient.close();

            } else if (args[0].equals("10")) {

                // ImdstKeyValueClient���g�p���ăf�[�^���擾(Key�̂�)(�o�C�i��)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                Object[] ret = null;
                long start = new Date().getTime();
                
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    ret = imdstKeyValueClient.getByteValue(args[5]);
                    if (ret[0].equals("true")) {
                        // �f�[�^�L��
                        byte[] fileByte = null;
                        File file = new File(args[4]);
                        FileOutputStream fos = new FileOutputStream(file);
                        fileByte = (byte[])ret[1];
                        fos.write(fileByte, 0, fileByte.length);
                        fos.close();
                    } else if (ret[0].equals("false")) {
                        System.out.println("�f�[�^�Ȃ�");
                    } else if (ret[0].equals("error")) {
                        System.out.println(ret[1]);
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start));
                imdstKeyValueClient.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}