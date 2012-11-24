package lv.tti.app.utils;

import android.util.Log;
import com.google.gson.Gson;
import lv.tti.app.ScheduleApplication;
import lv.tti.app.models.ScheduleEvent;
import lv.tti.app.models.ScheduleResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class EventParseManager {

    private final String url = "http://www.tsi-api.tk/api/";

    private ScheduleApplication context;

    public EventParseManager(ScheduleApplication context) {
        this.context = context;        
    }

    public List<ScheduleEvent> getSchedule(){
        int offset = context.getOffset();
        long time = System.currentTimeMillis() / 1000 + 86400 * offset;

        Date date = new Date(time*1000);
        String formattedDate = new SimpleDateFormat("EEEE, dd.MM.yyyy").format(date);
        context.setDate(formattedDate);
        context.setDateCache(formattedDate);

        try{
            Gson gson = new Gson();

            InputStream source = retrieveStream(apiScheduleUrl("group", time, time));
            Reader reader = new InputStreamReader(source);
            ScheduleResponse response = gson.fromJson(reader, ScheduleResponse.class);

            List<ScheduleEvent> events;
            if(response.data.isEmpty() && response.data.get(0).events.isEmpty()) {
                events = new ArrayList<ScheduleEvent>();
            } else {
                events = response.data.get(0).events;
            }

            return events;
        } catch (Exception e){
            Log.w("Parsing webpage", "error");
        }
        return null;
    }

    private String apiItemsUrl() {
        return url + "items/all.json";
    }

    private String apiScheduleUrl(String type, long from, long to) {
        String group = context.getUser().getStudentCode();
        if("" == group) group = "dummy";
        return url + "schedule/" + type + "/" + group + "/from/" + from + "/to/" + to + ".json";
    }

    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url);
                return null;
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();
        }
        catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }
        return null;
    }

}
