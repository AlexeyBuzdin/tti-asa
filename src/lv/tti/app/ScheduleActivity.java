package lv.tti.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class ScheduleActivity extends Activity implements OnClickListener {
	
	private String studentKey = "St54494";
	private String date;
	
	private ImageButton bPrevious;
	private ImageButton bNext;
	private TextView tDate;
	private ListView lvLessons;
	private LessonsAdapter lessonsAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bPrevious = (ImageButton) findViewById(R.id.previous);
        bNext = (ImageButton) findViewById(R.id.next);
        tDate = (TextView) findViewById(R.id.date);
        lvLessons = (ListView) findViewById(R.id.lessons);
        Lesson a = new Lesson();
        List<Lesson> lessonsList = new ArrayList<Lesson>();
        lessonsList.add(a);
        lessonsList.add(a);
        lessonsList.add(a);
        lessonsList.add(a);
        getSchedule(0);
        tDate.setText(date); 
        
        lessonsAdapter = new LessonsAdapter(this, lessonsList);
        lvLessons.setAdapter(lessonsAdapter);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
	
	private List<Lesson> getSchedule(int offset){
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpGet = new HttpGet("http://m.tsi.lv/schedule/default.aspx?login=" + studentKey);
			HttpResponse response = httpClient.execute(httpGet, localContext);
			String result = "";
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			 
			String line = null;
			while ((line = reader.readLine()) != null){
			  result += line + "\n";
			}
			Log.i("!Page", result);
			Pattern day = Pattern.compile("<caption>\\s*([(\\w\\s]*[)])\n*");
			
			Matcher matcher = day.matcher(result);
			matcher.find();
			setDate(matcher.group(1));
			Log.i("!Day",getDate());
			
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