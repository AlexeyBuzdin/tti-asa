package lv.tti.app;

import android.app.Application;
import android.content.Context;
import lv.tti.app.models.User;
import lv.tti.app.utils.NetworkChecker;

public class ScheduleApplication extends Application{
    private static ScheduleApplication instance;

    private User user;
    private int offset;
    private byte offsetVector;

    @Override
    public void onCreate() {
        instance = new ScheduleApplication();
    }

    public boolean checkForNetworkConnection(Context context) {
        NetworkChecker networkChecker = NetworkChecker.getInstance();
        if(!networkChecker.isConnectedToNetwork(context)){
            networkChecker.displayNoNetworkDialog(context);
            return false;
        }
        return true;
    }

    public static ScheduleApplication getInstance() {
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        offsetVector = 0;
    }


    public void changeOffset(byte offsetVector){
        this.offsetVector = offsetVector;
        offset += offsetVector;
    }

    public byte getOffsetVector() {
        return offsetVector;
    }

}
