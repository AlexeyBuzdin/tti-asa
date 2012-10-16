package lv.tti.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import lv.tti.app.R;
import lv.tti.app.ScheduleApplication;

public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        final int welcomeScreenDisplay = 1000;
        Thread welcomeThread = new Thread() {

            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    Looper.prepare();
                    if(ScheduleApplication.getInstance().checkForNetworkConnection(SplashScreenActivity.this)){
                        startActivity(new Intent(SplashScreenActivity.this,
                                ScheduleActivity.class));
                        finish();
                    }
                    Looper.loop();
                }
            }
        };
        welcomeThread.start();

    }
}