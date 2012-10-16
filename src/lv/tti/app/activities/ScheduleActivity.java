package lv.tti.app.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import lv.tti.app.R;
import lv.tti.app.ScheduleApplication;
import lv.tti.app.dialogs.DialogCreator;
import lv.tti.app.models.Lesson;
import lv.tti.app.models.User;
import lv.tti.app.utils.LessonsAdapter;
import lv.tti.app.utils.ScheduleParseManager;
import lv.tti.app.utils.ScheduleUpdater;

import java.util.List;

public class ScheduleActivity extends Activity implements OnClickListener, ScheduleUpdater {

    // Constants
    private static final int SET_STUDENT_CODE = 0;
    private static final int CALENDAR = 1;
    private static final int ABOUT = 2;

    // Utils
    private LessonsAdapter lessonsAdapter;
    private ScheduleApplication context;
    private DialogCreator dialogCreator;
    private ScheduleParseManager scheduleParseManager;

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
                dialogCreator.showStudentCodeDialog(true);
                return true;
//            case CALENDAR:
//                return true;
            case ABOUT:
                dialogCreator.showAboutDialog();
                return true;
        }
        return false;
    }

    private void init() {
        setContentView(R.layout.main);

        context = ScheduleApplication.getInstance();
        context.changeOffset(0);

        dialogCreator = new DialogCreator(this);
        scheduleParseManager = new ScheduleParseManager(context);

        bPrevious = (ImageButton) findViewById(R.id.previous);
        bNext = (ImageButton) findViewById(R.id.next);
        tDate = (TextView) findViewById(R.id.date);
        lvLessons = (ListView) findViewById(R.id.lessons);

        bPrevious.setOnClickListener(this);
        bNext.setOnClickListener(this);

        context.checkCacheForUser(this);
        updateSchedule();
	}

    private void updateSchedule() {
		List<Lesson> lessonsList = scheduleParseManager.getSchedule();
	    if(null == lessonsList){
            return;
        }
        tDate.setText(context.getDate());

	    lessonsAdapter = new LessonsAdapter(this, lessonsList);
	    lvLessons.setAdapter(lessonsAdapter);
	}

	@Override
	public void onClick(View v) {
		if(v.equals(bPrevious)){
			context.changeOffset(-1);
		} else if (v.equals(bNext)){
			context.changeOffset(1);
		}
		updateSchedule();
	}

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateSchedule(User user) {        
        context.setUser(user);
        context.getLessonParser().writeUser(user);
        updateSchedule();
    }

}