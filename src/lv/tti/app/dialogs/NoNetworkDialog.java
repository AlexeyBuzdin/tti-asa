package lv.tti.app.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import lv.tti.app.R;

public class NoNetworkDialog extends Dialog implements View.OnClickListener {

    private Button button;
    private Activity context;

    public NoNetworkDialog(Activity context) {
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        setContentView(R.layout.no_network_dialog);
        setTitle(super.getContext().getString(R.string.check_internet));
        setCancelable(false);
        button = (Button) findViewById(R.id.close_program);
        button.setOnClickListener(this);
        show();
    }

    @Override
    public void onClick(View view) {
        context.finish();
    }
}
