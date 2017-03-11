package cc.heicoco.chat.model.bo.impl;

import cc.heicoco.chat.model.bo.LogRegBo;
import cc.heicoco.chat.model.dao.ConServerTcpDao;
import cc.heicoco.chat.model.dao.impl.ConServerTcpDaoImpl;
import cc.heicoco.chat.model.entity.User;
import cc.heicoco.chat.model.utils.MyGlobal;

/**
 * <br>Title:TODO 登录注册bo
 * <br>Description:TODO 处理全部连接信息，调用对应dao处理分发数据包
 * <br>Author:董嘉文(roco2015@163.com)
 * <br>Date:2016年5月10日
 */
public class LogRegBoImpl implements LogRegBo {
    
    ConServerTcpDao cstDao = new ConServerTcpDaoImpl();
    
    @Override
    public String log(User user) {
        // TODO Auto-generated method stub
        String responCode = "10";
        try {
            responCode = cstDao.log(user,MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return responCode;
    }

    /**
     * <br>Description:TODO 先发送请求代码0，通知服务器接下来为注册
     * <br>Author:董嘉文(roco2015@163.com)
     * <br>Date:2016年5月10日
     * @see cc.heicoco.chat.model.bo.LogRegBo#reg(cc.heicoco.chat.model.entity.User)
     */
    @Override
    public String reg(User user) {
        // TODO Auto-generated method stub
        String responCode = "10";
        try {
            responCode = cstDao.reg(user,MyGlobal.getOut());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return responCode;
        
    }

}
