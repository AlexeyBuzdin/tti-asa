package lv.tti.app.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import lv.tti.app.dialogs.NoNetworkDialog;

public class NetworkChecker {

    private static NetworkChecker instance = new NetworkChecker();

    public boolean isConnectedToNetwork(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void displayNoNetworkDialog(Context context){
       NoNetworkDialog noNetworkDialog = new NoNetworkDialog((Activity)context);
    }

    public static NetworkChecker getInstance() {
        return instance;
    }

}
