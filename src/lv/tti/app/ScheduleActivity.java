package lv.tti.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import lv.tti.app.dialogs.StudentCodeDialog;
import lv.tti.app.models.Lesson;
import lv.tti.app.models.User;
import lv.tti.app.utils.LessonParser;
import lv.tti.app.utils.LessonsAdapter;
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

public class ScheduleActivity extends Activity implements OnClickListener, ScheduleUpdater {

    // Constants
    private static final int SET_STUDENT_CODE = 0;
    private static final int CALENDAR = 1;
    private static final int ABOUT = 2;

    private ScheduleApplication context;

    // Data
    private User user;
    private String date;
    private String dateCache;
    private int offsetProblemCounter;

    // Utils
    private LessonsAdapter lessonsAdapter;
    private LessonParser lessonParser;

    
    // Views
    private ImageButton bPrevious;
    private ImageButton bNext;
    private TextView tDate;
    private ListView lvLessons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ScheduleApplication.getInstance().checkForNetworkConnection(this)){
            updateSchedule();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem city = menu.add(0, SET_STUDENT_CODE, 0, getString(R.string.set_student_code_menu));
        city.setIcon(android.R.drawable.ic_menu_manage);
//        MenuItem refresh = menu.add(0, CALENDAR, 2, getString(R.string.calendar_menu));
//        refresh.setIcon(android.R.drawable.ic_menu_today);
        MenuItem about = menu.add(0, ABOUT, 3, getString(R.string.about_menu));
        about.setIcon(android.R.drawable.ic_menu_info_details);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case SET_STUDENT_CODE:
                enterStudentCode(true);
                return true;
            case CALENDAR:
                return true;
            case ABOUT:
                return true;
        }

        return false;
    }

    private void init() {
        setContentView(R.layout.main);

        context = ScheduleApplication.getInstance();

        bPrevious = (ImageButton) findViewById(R.id.previous);
        bNext = (ImageButton) findViewById(R.id.next);
        tDate = (TextView) findViewById(R.id.date);
        lvLessons = (ListView) findViewById(R.id.lessons);

        bPrevious.setOnClickListener(this);
        bNext.setOnClickListener(this);

        lessonParser = new LessonParser(this);

        checkCacheForUser();
        updateSchedule();
	}

    private void checkCacheForUser() {
        if(context.getUser() == null){
            user = lessonParser.readUser();
            if(user == null){
                enterStudentCode(false);
                return;
            } else {
                context.setUser(user);
            }
        } else {
            user = context.getUser();
        }
    }



    private void updateSchedule() {
		List<Lesson> lessonsList = getSchedule();
	    if(null == lessonsList){
            return;
        }
        tDate.setText(date);

	    lessonsAdapter = new LessonsAdapter(this, lessonsList);
	    lvLessons.setAdapter(lessonsAdapter);
	}

    private void enterStudentCode(boolean cancelable) {
        StudentCodeDialog studentCodeDialog = new StudentCodeDialog(this);
        studentCodeDialog.setCancelable(cancelable);
        studentCodeDialog.show();
        context.setOffset(0);
    }


	@Override
	public void onClick(View v) {
		if(v.equals(bPrevious)){
			context.changeOffset((byte)-1);
		} else if (v.equals(bNext)){
			context.changeOffset((byte)1);
		}
		updateSchedule();
	}

	private List<Lesson> getSchedule(){
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			String offsetStr =  "&offset=" + context.getOffset();

			String url = "http://m.tsi.lv/schedule/default.aspx?login=" + user.getStudentCode() + offsetStr;
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
			  result.append(reader.readLine());
			}

			Log.e("!Page", result.toString());
			Pattern dayPattern = Pattern.compile("<table .*?>(.*?)<");
			Pattern lessonPattern = Pattern.compile(
                    "<td.*?><b>(.*?)</b></td>.*?" +
                    "<td.*?>(.*?)</td>.*?" +
                    "<td.*?>(.*?)</td>.*?" +
                    "<td.*?>(<a href=.*?>)?(.*?)(</a>)?</td>.*?");

			Matcher matcher = dayPattern.matcher(result);
			matcher.find();
            Log.i("!Day", matcher.group(1));

			date = matcher.group(1).trim();
            if(date.equals(dateCache) || result.toString().contains("Server Error")){
                offsetProblemCounter++;
                if(offsetProblemCounter < 5){
                    context.changeOffset(context.getOffsetVector());
                    return getSchedule();
                }
            }
            dateCache = date;
            offsetProblemCounter = 0;


			matcher = lessonPattern.matcher(result);
			List<Lesson> lessons = new ArrayList<Lesson>();
			while(matcher.find()){
				Lesson lesson = new Lesson(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5));
				Log.i("!lesson", lesson.toString());
				lessons.add(lesson);
			}
			return lessons;
		} catch (Exception e){
			Log.w("!!!parsing webpage", "error");
		}

		return null;
	}

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateSchedule(User user) {
        this.user = user;
        context.setUser(user);
        lessonParser.writeUser(user);
        updateSchedule();
    }
}