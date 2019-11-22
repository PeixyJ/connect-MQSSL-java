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
        String channel = "C.S99.C";
        int port = 5099;
        String hostname = "192.168.60.27";
        String sslCipherSuite = "TLS_RSA_WITH_AES_128_CBC_SHA";
        String queueManageName = "QMGR.S99";

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
        System.setProperty("javax.net.ssl.keyStore", "/Users/peixinyi/Desktop/S99_KEY/key.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "ewell123");
        //JVM的证书库
        System.out.println(System.setProperty("javax.net.ssl.trustStore", "/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/lib/security/cacerts"));
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        //不使用IBM的JDK
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        MQEnvironment.channel = queueManage.getChannel();
        MQEnvironment.port = queueManage.getPort();
        MQEnvironment.hostname = queueManage.getIp();
        MQEnvironment.CCSID = queueManage.getCCSID();
        MQEnvironment.userID = queueManage.getName();
//        MQEnvironment.sslSocketFactory = SSLSocketFactory.getDefault();
        MQEnvironment.sslCipherSuite = queueManage.getSslCipherSuite();
        MQEnvironment.sslPeerName = "CN=192.168.60.27";
        return new MQQueueManager(queueManage.getQueueManageName());
    }
}