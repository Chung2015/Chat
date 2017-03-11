package cc.heicoco.chat.model.dao.impl;

import cc.heicoco.chat.model.dao.DealRegularDao;

public class DealRegularDaoImpl implements DealRegularDao {

    @Override
    public String[] udpPackage(String info) {
        // TODO Auto-generated method stub
        return info.split(",", 2);
    }

    @Override
    public String[] udpholeReqInfo(String info) {
        // TODO Auto-generated method stub
        return info.split(",",4);
    }

}
