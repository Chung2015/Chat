package cc.heicoco.chat.model.dao;

import java.io.IOException;


public interface UdpHolePunchingDao {
    public boolean sendHeartBeatPackage(String userName) throws IOException, InterruptedException;
    public boolean sendDetectionPackage(String userName, String ipAddrB, int portB, int portA) throws IOException, InterruptedException;
}
