package test;
import java.util.*;
import java.io.*;
import java.net.*;

import org.imdst.client.ImdstKeyValueClient;
import org.batch.lang.BatchException;


public class TestSockM {

    public static String[] args = null;

    public static void main(String[] args) {
        TestSockM.args = args;
        TestSockM me = new TestSockM();
        me.exec(args);
    }

    // ������
    // ���s�^�C�v 1:�o�^ 2:�擾
    // IP:Port
    // �����X���b�h��
    public void exec (String[] args) {
        try {
            long total = 0;
            Object[] list = new Object[Integer.parseInt(args[2])];
            int threadCount = Integer.parseInt(args[2]);
            MTest m = null;
            for (int i= 0; i < threadCount; i++) {

                m = new MTest();
                m.threadNo = i;
                list[i] = m;
            }


            for (int i= 0; i < list.length; i++) {

                m = (MTest)list[i];
                m.start();
                list[i] = m;
            }
            if (args[0].equals("1") || args[0].equals("2")) {
                Thread.sleep(59998);
            } else if (args[0].equals("3")) {
                Thread.sleep(120000);
            }
            System.out.println("end");
            for (int i= 0; i < list.length; i++) {

                m = (MTest)list[i];
                total = total + m.getExecCounter();
            }


            double one = total / threadCount;

            System.out.println("Total Query Count = " + total);
            System.out.println("1 Thread Avg Query Count = " + one);
            System.out.println("QPS = " + (total / 60));
            System.exit(1);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}