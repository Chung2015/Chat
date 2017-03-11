package cc.heicoco.chat.model.bo;

import cc.heicoco.chat.model.entity.User;

public interface LogRegBo {
    public String log(User user);
    public String reg(User user);
}
