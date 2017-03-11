package cc.heicoco.chat.view;

import android.app.Application;
import android.util.Log;
import cc.heicoco.chat.model.entity.User;

public class MyApplication extends Application {

    private User currentUser = null;
    
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i("MyApplication", "-----onCreate()-----");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    

}
