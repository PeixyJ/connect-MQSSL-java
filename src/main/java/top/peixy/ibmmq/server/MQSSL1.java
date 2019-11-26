/**
 * @class MQSSL1
 * @author peixinyi
 * @date 2019-06-10 10:32
 * @describe
 */
package top.peixy.ibmmq.server;

import com.ibm.mq.*;
import top.peixy.ibmmq.entity.QueueManage;

import javax.net.ssl.SSLSocketFactory;
import javax.xml.crypto.Data;
import java.util.Date;

public class MQSSL1 {


    public static void main(String[] args) throws MQException {

//        System.setProperty("javax.net.debug", "ssl:handshake");
        String channel = "Channel";
        int port = 5556;
        String hostname = "192.168.10.177";
        String sslCipherSuite = "TLS_RSA_WITH_AES_256_CBC_SHA256";
        String queueManageName = "ssldemo1";

        QueueManage queueManage = new QueueManage(queueManageName, hostname, channel, port, sslCipherSuite);
        queueManage.setName("administrator");

        Date startDate = new Date();
        MQQueueManager queueManager = connect(queueManage);
        Date endDate = new Date();
        System.out.println(endDate.getTime() - startDate.getTime());
        queueManager.disconnect();
    }

    static MQQueueManager connect(QueueManage queueManage) throws MQException {
        //客户机的证书库
        System.setProperty("javax.net.ssl.keyStore", "/Users/peixinyi/Desktop/Key/javaClient.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "ewell123");
        //JVM的证书库
        System.setProperty("javax.net.ssl.trustStore", "/Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/jre/lib/security/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "ewell123");
        //TCP DEBUG
        //不使用IBM的JDK
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        MQEnvironment.channel = queueManage.getChannel();
        MQEnvironment.port = queueManage.getPort();
        MQEnvironment.hostname = queueManage.getIp();
        MQEnvironment.CCSID = queueManage.getCCSID();
        MQEnvironment.userID = queueManage.getName();
        MQEnvironment.sslCipherSuite = queueManage.getSslCipherSuite();
        return new MQQueueManager(queueManage.getQueueManageName());
    }
}