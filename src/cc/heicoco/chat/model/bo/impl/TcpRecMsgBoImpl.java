package cc.heicoco.chat.model.bo.impl;

import cc.heicoco.chat.model.bo.TcpRecMsgBo;
import cc.heicoco.chat.model.dao.RecServerTcpDao;
import cc.heicoco.chat.model.dao.impl.RecServerTcpDaoImpl;
import cc.heicoco.chat.model.utils.MyGlobal;

public class TcpRecMsgBoImpl implements TcpRecMsgBo{

    private RecServerTcpDao rstDao = new RecServerTcpDaoImpl();
    @Override
    public void tcpReceive() {
        // TODO Auto-generated method stub
        rstDao.tcpReceive(MyGlobal.getServerMsgQueue(), MyGlobal.getIn());
    }

}
