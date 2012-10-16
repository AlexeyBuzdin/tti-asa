package lv.tti.app.utils;

import android.util.Log;
import lv.tti.app.ScheduleApplication;
import lv.tti.app.models.Lesson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleParseManager {

    private final String url = "http://m.tsi.lv/schedule/default.aspx?login=";
    private final String dayPatternString = "<caption>(.*?)</";
    private final String lessonPatternString = "<td.*?><b>(.*?)</b></td>.*?" +
                                               "<td.*?>(.*?)</td>.*?" +
                                               "<td.*?>(.*?)</td>.*?" +
                                               "<td.*?>(<a href=.*?>)?(.*?)(</a>)?</td>.*?";
    
    private int offsetProblemCounter;
    private ScheduleApplication context;

    public ScheduleParseManager(ScheduleApplication context) {
        this.context = context;        
    }

    public List<Lesson> getSchedule(){
        try{
            String pageContent = getPageContent();
            
            Pattern dayPattern = Pattern.compile(dayPatternString);
            Pattern lessonPattern = Pattern.compile(lessonPatternString);

            Matcher matcher = dayPattern.matcher(pageContent);
            matcher.find();

            String date = matcher.group(1).trim();
            String cachedDate = context.getDateCache();
            Log.i("!Day", date);

            context.setDate(date);
            if(date.equals(cachedDate) || pageContent.contains("Server Error")){
                offsetProblemCounter++;
                if(offsetProblemCounter < 5){
                    context.changeOffset(context.getOffsetVector());
                    return getSchedule();
                }
            }
            context.setDateCache(date);
            offsetProblemCounter = 0;


            matcher = lessonPattern.matcher(pageContent);
            List<Lesson> lessons = new ArrayList<Lesson>();
            while(matcher.find()){
                Lesson lesson = new Lesson(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5));
                Log.i("!lesson", lesson.toString());
                lessons.add(lesson);
            }
            return lessons;
        } catch (Exception e){
            Log.w("Parsing webpage", "error");
        }

        return null;
    }
    
    private String getPageContent() throws IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String offsetStr = "&offset=" + context.getOffset();

        String url = this.url + context.getUser().getStudentCode() + offsetStr;
        Log.e("URL", url);
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet, localContext);
        StringBuilder result = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        response.getEntity().getContent()
                )
        );

        String line = null;
        while ((line = reader.readLine()) != null){
            result.append(line);
        }
        Log.e("Page", result.toString());       
        return result.toString();
    }

}
