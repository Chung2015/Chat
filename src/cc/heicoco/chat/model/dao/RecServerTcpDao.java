package cc.heicoco.chat.model.dao;

import java.util.Queue;
import java.util.Scanner;

public interface RecServerTcpDao {
    public void tcpReceive(Queue<String> serverMsgQueue, Scanner in);
}
