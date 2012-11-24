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
import lv.tti.app.models.ScheduleEvent;
import lv.tti.app.models.User;
import lv.tti.app.utils.*;

import java.util.List;

public class ScheduleActivity extends Activity implements OnClickListener, ScheduleUpdater {

    // Constants
    private static final int SET_STUDENT_CODE = 0;
    private static final int CALENDAR = 1;
    private static final int ABOUT = 2;

    // Utils
    private EventAdapter eventAdapter;
    private ScheduleApplication context;
    private DialogCreator dialogCreator;
    private EventParseManager eventParseManager;

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
        context.setOffset(0);

        dialogCreator = new DialogCreator(this);
        eventParseManager = new EventParseManager(context);

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
        lvLessons.setAdapter(null);
        List<ScheduleEvent> eventList = eventParseManager.getSchedule();
        tDate.setText(context.getDate());

        if(null == eventList) return;

        eventAdapter = new EventAdapter(this, eventList);

        lvLessons.setAdapter(eventAdapter);
	}

	@Override
	public void onClick(View v) {
		if(v.equals(bPrevious)){
			context.decOffset();
		} else if (v.equals(bNext)){
			context.incOffset();
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