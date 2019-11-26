package top.peixy.ibmmq.server;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.*;

/**
 * @author peixinyi
 * @version v1.0
 * @since 2019/11/22 10:18
 * <p>
 */
public class MQSSL3 {
    public static void main(String[] args) throws JMSException {

        MQQueueConnectionFactory queueConnectionFactory = new MQQueueConnectionFactory();

        // Setting connection properties to connection factory
        queueConnectionFactory.setHostName("192.168.10.177");
        queueConnectionFactory.setPort(5556);

        // Setting queue manager and channel names
        queueConnectionFactory.setQueueManager("ssldemo1");
        queueConnectionFactory.setChannel("Channel");

        // Set transport type to MQ Client
        queueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

        // Creating a connection from factory
//        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection("administrator", "Ewell123");
        queueConnection.start();

        // Create a queue and a session
        Queue queue = new MQQueue("TEST");
        QueueSession queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create and send a TextMessage
        QueueSender queueSender = queueSession.createSender(queue);
        Message m = queueSession.createTextMessage("Hello, World!");
        queueSender.send(m);

        // Create a QueueReceiver and wait for one message to be delivered
        QueueReceiver queueReceiver = queueSession.createReceiver(queue);
        Message response = queueReceiver.receive();

        System.out.println("Received message: " + response);

        // Finally closing connections
        queueSession.close();
        queueConnection.close();
    }
}
