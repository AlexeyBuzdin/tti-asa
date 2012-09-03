package lv.tti.app;

import android.app.Application;
import lv.tti.app.models.User;

public class ScheduleApplication extends Application{
    private static ScheduleApplication instance;

    private User user;
    private int offset;
    private byte offsetVector;

    @Override
    public void onCreate() {
        instance = new ScheduleApplication();
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
    }


    public void changeOffset(byte offsetVector){
        this.offsetVector = offsetVector;
        offset += offsetVector;
    }

    public byte getOffsetVector() {
        return offsetVector;
    }
}
