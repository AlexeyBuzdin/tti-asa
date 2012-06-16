package lv.tti.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class ScheduleActivity extends Activity implements OnClickListener {
	
	private ImageButton bPrevious;
	private ImageButton bNext;
	private ListView lvLessons;
	private LessonsAdapter lessonsAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bPrevious = (ImageButton) findViewById(R.id.previous);
        bNext = (ImageButton) findViewById(R.id.next);
        lvLessons = (ListView) findViewById(R.id.lessons);
        Lesson a = new Lesson();
        List<Lesson> lessonsList = new ArrayList<Lesson>();
        lessonsList.add(a);
        lessonsList.add(a);
        lessonsList.add(a);
        lessonsList.add(a);
        
        lessonsAdapter = new LessonsAdapter(this, lessonsList);
        lvLessons.setAdapter(lessonsAdapter);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
}