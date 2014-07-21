package app.project.my.ccnaquizz.main;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import app.project.my.ccnaquizz.R;

import static app.project.my.ccnaquizz.R.*;

public class CorrectDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;

    public CorrectDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.custom_dialog);
        yes = (Button) findViewById(id.btn_yes);
        //no = (Button) findViewById(id.btn_no);
        yes.setOnClickListener(this);
        //no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case id.btn_yes:
                dismiss();
               // CCNATopic.i=CCNATopic.i+1;
                break;
           /* case id.btn_no:
                c.finish();
                break;*/
            default:
                break;
        }
        dismiss();
    }
}