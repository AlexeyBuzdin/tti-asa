package lv.tti.app.utils;

import android.content.Context;
import android.util.Log;
import lv.tti.app.models.User;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class LessonXMLService {
    
    private final String fileName = "schedule.xml";
    private File directory;

    public LessonXMLService(Context context) {
        init(context);
    }
    
    private void init(Context context) {
        directory = context.getExternalCacheDir();
        Log.e("directoryPath", directory.getAbsolutePath());
        if(!directory.exists()){
            directory.mkdirs();
        }
    }

    public void writeUser(User user) {
        try{
            Serializer serializer = new Persister();
            File cache = new File(directory, fileName);
            if(!cache.exists()){
                cache.createNewFile();
            }
            serializer.write(user, cache);
        } catch (Exception e){
            //Ignore
        }
    }
    
    public User readUser(){
        try{
            Serializer serializer = new Persister();
            File cache = new File(directory, fileName);
            User user = serializer.read(User.class, cache);
            return user;
        } catch (Exception e){
            return null;
        }
    }

}
