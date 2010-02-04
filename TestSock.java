import java.util.*;
import java.io.*;
import java.net.*;

import org.imdst.client.ImdstKeyValueClient;
import org.batch.lang.BatchException;

public class TestSock {
    public static void main(String[] args) {
        try {
            if (args == null || args.length ==0) {

                System.out.println("{�L�[�l�������ŌJ��Ԃ������ϓ������ēo�^}                        �R�}���h����{args[0]=1, args[1]=�}�X�^�m�[�h�T�[�oIP, args[2]=�}�X�^�m�[�h�T�[�oPort�ԍ�, args[3]=�o�^����}");
                System.out.println("{�L�[�l�������ŌJ��Ԃ������ϓ������Ď擾}                        �R�}���h����{args[0]=2, args[1]=�}�X�^�m�[�h�T�[�oIP, args[2]=�}�X�^�m�[�h�T�[�oPort�ԍ�, args[3]=�擾��}");
                System.out.println("{Tag��4�p�^�[���Ŏ����I�ɕϓ������ăL�[�l�͎����ϓ��œo�^}        �R�}���h����{args[0]=3, args[1]=�}�X�^�m�[�h�T�[�oIP, args[2]=�}�X�^�m�[�h�T�[�oPort�ԍ�, args[3]=�o�^����}");
                System.out.println("{�w�肵��Tag�Ŋ֘A����L�[�l���w��񐔎擾}                       �R�}���h����{args[0]=4, args[1]=�}�X�^�m�[�h�T�[�oIP, args[2]=�}�X�^�m�[�h�T�[�oPort�ԍ�, args[3]=�擾��, args[4]=�w��Tag�l (tag1 or tag2 or tag3 or tag4)}");
                System.out.println("{�w�肵���t�@�C�����o�C�i���f�[�^�Ƃ��Ďw�肵���L�[�l�ŕۑ�����}  �R�}���h����{args[0]=5, args[1]=�}�X�^�m�[�h�T�[�oIP, args[2]=�}�X�^�m�[�h�T�[�oPort�ԍ�, args[3]=�o�^��, args[4]=�t�@�C���p�X, args[5]=�L�[�l}");
                System.out.println("{�w�肵���L�[�l�Ńo�C�i���f�[�^���擾���ăt�@�C��������}          �R�}���h����{args[0]=6, args[1]=�}�X�^�m�[�h�T�[�oIP, args[2]=�}�X�^�m�[�h�T�[�oPort�ԍ�, args[3]=�擾��, args[4]=�쐬�t�@�C���p�X, args[5]=�L�[�l}");
                System.exit(0);
            }
            int port = Integer.parseInt(args[2]);

            if (args[0].equals("1")) {

                // ImdstKeyValueClient���g�p���ăf�[�^��ۑ�(Tag�Ȃ�)

                // �N���C�A���g�C���X�^���X���쐬
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();

                // �}�X�^�T�[�o�ɐڑ�
                imdstKeyValueClient.connect(args[1], port);

                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    // �f�[�^�o�^
                    if (!imdstKeyValueClient.setValue("datasavekey_" + new Integer(i).toString(), "savedatavaluestr_" + new Integer(i).toString())) {
                        System.out.println("ImdstKeyValueClient - error");
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start) + "milli second");

                imdstKeyValueClient.close();
            } else if (args[0].equals("2")) {

                // ImdstKeyValueClient���g�p���ăf�[�^���擾(Key�̂�)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] ret = null;

                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    ret = imdstKeyValueClient.getValue("datasavekey_" + new Integer(i).toString());
                    if (ret[0].equals("true")) {
                        // �f�[�^�L��
                        System.out.println(ret[1]);
                    } else if (ret[0].equals("false")) {
                        System.out.println("�f�[�^�Ȃ�");
                    } else if (ret[0].equals("error")) {
                        System.out.println(ret[1]);
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start) + "milli second");

                imdstKeyValueClient.close();
            } else if (args[0].equals("2.1")) {

                // ImdstKeyValueClient���g�p���ăf�[�^���擾(Key�̂�)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] ret = null;

                long start = new Date().getTime();
                for (int i = 0; i < Integer.parseInt(args[3]);i++) {
                    ret = imdstKeyValueClient.getValue("datasavekey_" + new Integer(i).toString());
                    if (ret[0].equals("true")) {
                        // �f�[�^�L��
                    } else if (ret[0].equals("false")) {
                        System.out.println("�f�[�^�Ȃ�");
                    } else if (ret[0].equals("error")) {
                        System.out.println(ret[1]);
                    }
                }
                long end = new Date().getTime();
                System.out.println((end - start) + "milli second");

                imdstKeyValueClient.close();

            } else if (args[0].equals("3")) {

                // ImdstKeyValueClient���g�p���ăf�[�^��ۑ�(Tag����)
                ImdstKeyValueClient imdstKeyValueClient = new ImdstKeyValueClient();
                imdstKeyValueClient.connect(args[1], port);
                String[] tag1 = {"tag1"};
                String[] tag2 = {"tag1","tag2"};
                String[] tag3 = {"tag1","tag2","tag3"};
                String[] tag4 = {"tag4"};
                String[] setTag = null;
                int counter = 0;

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

                    if (!imdstKeyValueClient.setValue("tagsampledatakey_" + new Integer(i).toString(), setTag, "tagsamplesavedata_" + new Integer(i).toString())) {
                        System.out.println("ImdstKeyValueClient - error");
                    }
                }

                imdstKeyValueClient.close();
            } else if (args[0].equals("4")) {

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

            } else if (args[0].equals("5")) {

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

            } else if (args[0].equals("6")) {

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