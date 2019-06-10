/**
 * @class MQSSL1
 * @author peixinyi
 * @date 2019-06-10 10:32
 * @describe
 */
package top.peixy.ibmmq.server;

import com.ibm.mq.*;
import top.peixy.ibmmq.entity.QueueManage;

import javax.xml.crypto.Data;
import java.util.Date;

public class MQSSL1 {


    public static void main(String[] args) {
        String channel = "C.S01.C";
        int port = 5001;
        String hostname = "192.168.10.175";
        String sslCipherSuite = "TLS_RSA_WITH_AES_128_CBC_SHA";
        String queueManageName = "QMGR.S01_1";

        QueueManage queueManage = new QueueManage(queueManageName, hostname, channel, port, sslCipherSuite);
        queueManage.setName("administrator");

        try {
            for (int i = 0; i <100 ; i++) {
                Date startDate = new Date();
                MQQueueManager queueManager = connect(queueManage);
                Date endDate = new Date();
                System.out.println(endDate.getTime() - startDate.getTime());
                queueManager.disconnect();
            }
//            sendMsg("Hello World", queueManager);
        } catch (MQException e) {
            e.printStackTrace();
        }
    }

    static MQQueueManager connect(QueueManage queueManage) throws MQException {
        //客户机的证书库
        System.setProperty("javax.net.ssl.keyStore", "/Users/peixinyi/Desktop/key2/bo.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        //JVM的证书库
        System.setProperty("javax.net.ssl.trustStore", "/Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/jre/lib/security/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        //不使用IBM的JDK
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        MQEnvironment.channel = queueManage.getChannel();
        MQEnvironment.port = queueManage.getPort();
        MQEnvironment.hostname = queueManage.getIp();
        MQEnvironment.CCSID = queueManage.getCCSID();
        MQEnvironment.userID = queueManage.getName();
        MQEnvironment.sslCipherSuite = queueManage.getSslCipherSuite();
        MQQueueManager queueManager = new MQQueueManager(queueManage.getQueueManageName());
        return queueManager;
    }

    public static void sendMsg(String msgStr, MQQueueManager queueString) {
        int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT | MQC.MQOO_INQUIRE;
        MQQueue queue = null;
        try {
            // 建立Q1通道的连接
            queue = queueString.accessQueue("EQ.TEST", openOptions, "QMGR.S01_1", null, null);
            MQMessage msg = new MQMessage();// 要写入队列的消息
            msg.format = MQC.MQFMT_STRING;
            msg.characterSet = 1381;
            msg.encoding = 1381;
            // msg.writeObject(msgStr); //将消息写入消息对象中
            msg.writeString(msgStr);
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            msg.expiry = -1; // 设置消息用不过期
            queue.put(msg, pmo);// 将消息放入队列
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (queue != null) {
                try {
                    queue.close();
                } catch (MQException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
