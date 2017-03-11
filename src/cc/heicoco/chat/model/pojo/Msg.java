package cc.heicoco.chat.model.pojo;

public class Msg {
    
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;
    private String friendId;
    public Msg(String content, int type) {
        super();
        this.content = content;
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public int getType() {
        return type;
    }
    public String getFriendId() {
        return friendId;
    }
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
