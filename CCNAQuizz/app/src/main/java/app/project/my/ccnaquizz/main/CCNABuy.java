package app.project.my.ccnaquizz.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.project.my.ccnaquizz.R;


/**
 * Created by asus on 7/16/2014.
 */
public class CCNABuy extends Activity {
    Button submit;
    AlertDialog buyDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy);
        Intent intent = getIntent();
        String topic = intent.getExtras().getString("topic");
        buyDialog = new AlertDialog.Builder(
               CCNABuy.this).create();

        // Setting Dialog Title
        // correctDialog.setTitle("Correct Answer!");

        // Setting Dialog Message
        buyDialog.setMessage("Now you have bought the "+topic);

        // Setting Icon to Dialog
        // correctDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        buyDialog.setButton("Go.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent i = new Intent(CCNABuy.this,CCNAQuizList.class);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CCNAQuizList.bought=true;
                buyDialog.show();
                /*Intent i = new Intent(CCNAQuizList.this,CCNABuy.class);
                startActivity(i);*/

            }
        });

    }
}
