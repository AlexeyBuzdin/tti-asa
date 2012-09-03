package lv.tti.app.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import lv.tti.app.R;
import lv.tti.app.ScheduleUpdater;
import lv.tti.app.models.User;

public class StudentCodeDialog extends Dialog implements OnClickListener {

    private EditText editCode;
    private ScheduleUpdater scheduleUpdater;

    public StudentCodeDialog(ScheduleUpdater scheduleUpdater) {
        super(scheduleUpdater.getContext());
        this.scheduleUpdater = scheduleUpdater;
        init();
    }

    private void init(){

        this.setContentView(R.layout.enter_code_dialog);
        this.setTitle(super.getContext().getString(R.string.enter_code_title));
        this.setCancelable(false);

        editCode = (EditText) findViewById(R.id.editCode);

        Button save = (Button)findViewById(R.id.saveCode);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        hide();
        User user = new User(editCode.getText().toString(), null);
        scheduleUpdater.updateSchedule(user);
    }
}
