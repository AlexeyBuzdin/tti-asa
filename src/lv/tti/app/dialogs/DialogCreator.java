package lv.tti.app.dialogs;

import lv.tti.app.utils.ScheduleUpdater;

public class DialogCreator {

    private ScheduleUpdater context;

    public DialogCreator(ScheduleUpdater scheduleUpdater) {
        this.context = scheduleUpdater;
    }

    public void showStudentCodeDialog(boolean cancelable) {
        StudentCodeDialog studentCodeDialog = new StudentCodeDialog(context);
        studentCodeDialog.setCancelable(cancelable);
        studentCodeDialog.show();
    }

    public void showAboutDialog() {
        AboutDialog aboutDialog = new AboutDialog(context);
    }
}
