package lv.tti.app.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lv.tti.app.R;
import lv.tti.app.models.ScheduleEvent;

import java.util.List;

public class EventAdapter extends ArrayAdapter<ScheduleEvent> {

	private final Context context;
	private final List<ScheduleEvent> values;

	private TextView tStartTime;
	private TextView tSubject;
	private TextView tClassroom;
	private TextView tProfessor;

	public EventAdapter(Context context, List<ScheduleEvent> values) {
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

        if(position % 2 == 0){
            rowView.setBackgroundResource(R.color.list_items1);
        } else {
            rowView.setBackgroundResource(R.color.list_items2);
        }
        rowView.invalidate();

        ScheduleEvent event = values.get(position);

        tStartTime.setText(event.time);
        tSubject.setText(event.name);
        tClassroom.setText(event.rooms);
        tProfessor.setText(event.teacher);

		return rowView;
	}

}