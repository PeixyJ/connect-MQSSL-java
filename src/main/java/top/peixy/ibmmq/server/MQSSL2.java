package top.peixy.ibmmq.server;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.jmqi.JmqiUtils;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * @author peixinyi
 * @version v1.0
 * @since 2019/11/20 14:45
 * <p>
 */
public class MQSSL2 {
    public static void main(String[] args) throws Exception {
        System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", "false");
        MQEnvironment.hostname = "192.168.60.27";
        MQEnvironment.channel = "C.S99.C";
        MQEnvironment.sslCipherSuite = "TLS_RSA_WITH_AES_128_CBC_SHA";
        MQEnvironment.port = 5099;
        MQEnvironment.sslSocketFactory = null;
        MQQueueManager qmgr = new MQQueueManager("QMGR.S99");
    }
}
