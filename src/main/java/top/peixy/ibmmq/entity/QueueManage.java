/**
 * @class QueueManage
 * @author peixinyi
 * @date 2019-06-10 10:25
 * @describe
 */
package top.peixy.ibmmq.entity;

import lombok.Data;
import lombok.Value;

@Data
public class QueueManage {
    private String queueManageName;
    private String ip;
    private String channel;
    private int port;
    private String sslCipherSuite;
    private String[] queue;
    private String name;
    private int CCSID = 1381;
    private int timeOut = 1000;

    public QueueManage() {
    }

    public QueueManage(String queueManageName, String ip, String channel, int port, String sslCipherSuite) {
        this.queueManageName = queueManageName;
        this.ip = ip;
        this.channel = channel;
        this.port = port;
        this.sslCipherSuite = sslCipherSuite;
    }

    public QueueManage(String queueManageName, String ip, String channel, int port, String sslCipherSuite, String[] queue, int CCSID, int timeOut, String name) {
        this.queueManageName = queueManageName;
        this.ip = ip;
        this.channel = channel;
        this.port = port;
        this.sslCipherSuite = sslCipherSuite;
        this.queue = queue;
        this.CCSID = CCSID;
        this.timeOut = timeOut;
        this.name = name;
    }

    @Override
    public String toString() {
        return "QueueManage{" +
                "queueManageName='" + queueManageName + '\'' +
                ", ip='" + ip + '\'' +
                ", channel='" + channel + '\'' +
                ", port=" + port +
                ", sslCipherSuite='" + sslCipherSuite + '\'' +
                '}';
    }
}
