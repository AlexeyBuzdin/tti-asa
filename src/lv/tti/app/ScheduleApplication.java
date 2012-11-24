package lv.tti.app;

import android.app.Application;
import android.content.Context;
import lv.tti.app.dialogs.DialogCreator;
import lv.tti.app.models.User;
import lv.tti.app.utils.LessonParser;
import lv.tti.app.utils.NetworkChecker;
import lv.tti.app.utils.ScheduleUpdater;

public class ScheduleApplication extends Application{
    private static ScheduleApplication instance;

    private User user;
    private int offset;
    private int offsetVector;

    // Data
    private String date;
    private String dateCache;

    private LessonParser lessonParser;

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

    public void checkCacheForUser(ScheduleUpdater scheduleUpdater) {
        User user = this.user;
        if(user == null){
            lessonParser = new LessonParser((Context)scheduleUpdater);
            user = lessonParser.readUser();
            if(user == null){
                DialogCreator dialogCreator = new DialogCreator(scheduleUpdater);
                dialogCreator.showStudentCodeDialog(false);
                setOffset(0);
            } else {
                this.user = user;
            }
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateCache() {
        return dateCache;
    }

    public void setDateCache(String dateCache) {
        this.dateCache = dateCache;
    }

    public LessonParser getLessonParser() {

        return lessonParser;
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

    public void incOffset() {
        this.offset++;
    }

    public void decOffset() {
        this.offset--;
    }
}
