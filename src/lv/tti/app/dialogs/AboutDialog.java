package lv.tti.app.dialogs;

import android.app.Dialog;
import lv.tti.app.R;
import lv.tti.app.utils.ScheduleUpdater;

public class AboutDialog extends Dialog {

    public AboutDialog(ScheduleUpdater context) {
        super(context.getContext());
        init();
    }

    private void init(){
        setContentView(R.layout.about_dialog);
        setTitle(super.getContext().getString(R.string.about_menu));
        setCancelable(true);
        show();
    }

}
