package lv.tti.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScheduleActivity extends Activity implements OnClickListener {
	
	private String studentKey = "St54494";
	private String date;
	private int offset;
	
	private ImageButton bPrevious;
	private ImageButton bNext;
	private TextView tDate;
	private ListView lvLessons;
	private LessonsAdapter lessonsAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        
        update();
    }

	private void init() {
        setContentView(R.layout.main);
        bPrevious = (ImageButton) findViewById(R.id.previous);
        bNext = (ImageButton) findViewById(R.id.next);
        tDate = (TextView) findViewById(R.id.date);
        lvLessons = (ListView) findViewById(R.id.lessons);
        offset = 1;
        
        bPrevious.setOnClickListener(this);
        bNext.setOnClickListener(this);		
	}

	private void update() {
		List<Lesson> lessonsList = getSchedule();
	    if(null == lessonsList){
            return;
        }
        tDate.setText(date); 
	        
	    lessonsAdapter = new LessonsAdapter(this, lessonsList);
	    lvLessons.setAdapter(lessonsAdapter);
	}

	@Override
	public void onClick(View v) {
		if(v.equals(bPrevious)){
			offset -=1;
		} else if (v.equals(bNext)){
			offset +=1;
		}
		update();
	}
    
	
	private List<Lesson> getSchedule(){
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			String offsetStr =  "&offset=" + offset;
			
			String url = "http://m.tsi.lv/schedule/default.aspx?login=" + studentKey + offsetStr;
			Log.i("URL", url);
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
			  result.append(reader.readLine());
			}

			Log.i("!Page", result.toString());
			Pattern dayPattern = Pattern.compile("<table .*?>(.*?)<");
			Pattern lessonPattern = Pattern.compile(
                    "<td.*?><b>(.*?)</b></td>.*?" +
                    "<td.*?>(.*?)</td>.*?" +
                    "<td.*?>(.*?)</td>.*?" +
                    "<td.*?>(.*?)</td>.*?");
			
			Matcher matcher = dayPattern.matcher(result);
			matcher.find();
            Log.i("!Day", matcher.group(1));
			setDate(matcher.group(1));
			
			matcher = lessonPattern.matcher(result);
			List<Lesson> lessons = new ArrayList<Lesson>();
			while(matcher.find()){
				Lesson lesson = new Lesson(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
				Log.i("!lesson", lesson.toString());
				lessons.add(lesson);
			}
			return lessons;
		} catch (Exception e){
			Log.w("!!!parsing webpage", e.getLocalizedMessage());
		}
		 
		return null;
	}
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}