package cc.heicoco.chat.model.entity;

public class User {
    private String account;
    private String password;
    private String userName;
    private String ip;
    
    public User() {
        super();
        // TODO Auto-generated constructor stub
    }
    public User(String account, String password, String userName, String ip) {
        super();
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.ip = ip;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
