package lv.tti.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LessonsAdapter extends ArrayAdapter<Lesson> {
	
	private final Context context;
	private final List<Lesson> values;

	private TextView tStartTime;
	private TextView tSubject;
	private TextView tClassroom;
	private TextView tProfessor;
	
	public LessonsAdapter(Context context, List<Lesson> values) {
		super(context, R.layout.lessonslayout, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.lessonslayout, parent, false);
		tStartTime = (TextView) rowView.findViewById(R.id.startTime);
		tSubject = (TextView) rowView.findViewById(R.id.subject);
		tClassroom = (TextView) rowView.findViewById(R.id.classroom);
		tProfessor = (TextView) rowView.findViewById(R.id.professor);
		
		Lesson lesson = values.get(position);
		
		tStartTime.setText(lesson.getTime());
		tSubject.setText(lesson.getSubject());
		tClassroom.setText(lesson.getClassroom());
		tProfessor.setText(lesson.getProfessor());
		
		return rowView;
	}
	
}
