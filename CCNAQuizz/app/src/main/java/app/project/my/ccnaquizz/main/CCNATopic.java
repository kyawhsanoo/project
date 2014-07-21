package app.project.my.ccnaquizz.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.project.my.ccnaquizz.R;
import app.project.my.ccnaquizz.user.Login;


public class CCNATopic extends Activity {
	Button previous,next,submit;
    AlertDialog correctDialog,wrongDialog;
    LinearLayout questionlayout,optionlayout;
    String myjsonstring=null;
    String question_id =null;
    String question_name =null;
    String option_a =null;
    String option_b =null;
    String option_c=null;
    String option_d=null;
    String option_e=null;
    String answer=null;
    public static int i=0;
    boolean correct=false;
    int size=0;
    int mark=0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccna_topic_ques);
        Intent intent = getIntent();
        String topic = intent.getExtras().getString("topic");
        setTitle(topic);
        previous=(Button)findViewById(R.id.previous);
        next=(Button)findViewById(R.id.next);
        submit=(Button)findViewById(R.id.submit);
        questionlayout=(LinearLayout)findViewById(R.id.questionlayout);
        optionlayout=(LinearLayout)findViewById(R.id.optionlayout);
        updateLayouts();
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i>0)i=i-1;
                else i=0;

                updateLayouts();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=i+1;

                if(i<size){

                    updateLayouts();
                }
                else {
                    i=0;
                    showResultLayout();


                }

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(correct==true)
               {
               /* CustomDialogClass d=new CustomDialogClass(CCNATopic.this);
                d.show();*/
                correctDialog.show();
               }
               if(correct==false){
                /*CustomDialogClass2 d=new CustomDialogClass2(CCNATopic.this);
                   d.show();
                   */
                wrongDialog.show();
               }

            }
        });
        correctDialog = new AlertDialog.Builder(
                CCNATopic.this).create();

        // Setting Dialog Title
        // correctDialog.setTitle("Correct Answer!");

        // Setting Dialog Message
        correctDialog.setMessage("Correct Answer!");

        // Setting Icon to Dialog
        // correctDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        correctDialog.setButton("Proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                if(i<size-1){
                    i=i+1;
                    updateLayouts();
                }
                else{
                    showResultLayout();
                }

                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        wrongDialog = new AlertDialog.Builder(
                CCNATopic.this).create();

        // Setting Dialog Title
        // correctDialog.setTitle("Correct Answer!");

        // Setting Dialog Message
        wrongDialog.setMessage("Wrong Answer!");

        // Setting Icon to Dialog
        // correctDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        wrongDialog.setButton("Try again.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                i=i;
                updateLayouts();
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(CCNATopic.this,CCNAQuizList.class);
            //i.putExtra("topic", vh.name.getText().toString());
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private void showResultLayout(){
        next.setEnabled(false);
        submit.setEnabled(false);
        questionlayout.removeAllViews();
        optionlayout.removeAllViews();
        TextView congratulation = new TextView(getApplicationContext());
        congratulation.setTextColor(Color.parseColor("#FFFFFF"));
        congratulation.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.titletextsize));
        congratulation.setTypeface(null, Typeface.BOLD);
        congratulation.setText("Congratulations!");
        LinearLayout.LayoutParams questionnolayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //questionnolayoutParam.gravity=Gravity.CENTER;
        congratulation.setLayoutParams(questionnolayoutParam);
        questionlayout.addView(congratulation);
        TextView result = new TextView(getApplicationContext());
        result.setTextColor(Color.parseColor("#FFFFFF"));
        result.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.questiontextsize));
        result.setTypeface(null, Typeface.BOLD);
        result.setText("You got  ("+ mark+"/10)  "+ (mark*100/10)+"%" + " correct answers." );
        LinearLayout.LayoutParams resultlayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //questionnolayoutParam.gravity=Gravity.CENTER;
        congratulation.setLayoutParams(resultlayoutParam);
        optionlayout.addView(result);


    }
    public void updateLayouts(){

        parseJsonAndSaveData(getJsonFromAssetFolder());
        next.setEnabled(true);
        submit.setEnabled(true);
        questionlayout.removeAllViews();
        optionlayout.removeAllViews();
        TextView questionno = new TextView(getApplicationContext());
        questionno.setTextColor(Color.parseColor("#FFFFFF"));
        questionno.setTextSize(TypedValue.COMPLEX_UNIT_PX,
               getResources().getDimension(R.dimen.questiontextsize));
        questionno.setTypeface(null, Typeface.BOLD);
        questionno.setText(question_id);
        LinearLayout.LayoutParams questionnolayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //questionnolayoutParam.gravity=Gravity.CENTER;
        questionno.setLayoutParams(questionnolayoutParam);
        questionlayout.addView(questionno);

        TextView questionbody= new TextView(getApplicationContext());
        questionbody.setTextColor(Color.parseColor("#FFFFFF"));
        questionbody.setTextSize(TypedValue.COMPLEX_UNIT_PX,
               getResources().getDimension(R.dimen.questiontextsize));
        questionbody.setText(question_name);
        LinearLayout.LayoutParams questionbodylayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        questionbodylayoutParam.gravity=Gravity.CENTER;
        questionbody.setLayoutParams(questionbodylayoutParam);
        questionlayout.addView(questionbody);

        final RadioButton[] rb = new RadioButton[5];
        RadioGroup rg = new RadioGroup(getApplicationContext()); //create the RadioGroup
        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
        LinearLayout.LayoutParams optionParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        optionParam.gravity=Gravity.CENTER;
        for(int i=0; i<5; i++){
           rb[i]  = new RadioButton(getApplicationContext());
           rb[i].setId(i);
           rb[i].setLayoutParams(optionParam
           );
           rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
           if(i==0)
               rb[i].setText(option_a);
           if(i==1)
               rb[i].setText(option_b);
           if(i==2)
               rb[i].setText(option_c);
           if(i==3)
               rb[i].setText(option_d);
           if(i==4)
               rb[i].setText(option_e);


        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch(checkedId)
                {
                    case 0:
                        if(answer.equalsIgnoreCase("option_a")) {
                            correct=true;
                            mark+=1;

                            /*CustomDialogClass d=new CustomDialogClass(CCNATopic.this);
                            d.show();*/
                            //correctDialog.show();

                        }
                        else{
                            correct=false;

                            /*CustomDialogClass2 d=new CustomDialogClass2(CCNATopic.this);
                            d.show();*/
                            //wrongDialog.show();
                        }
                        break;
                    case 1:
                        if(answer.equalsIgnoreCase("option_b")) {
                            correct=true;
                            mark+=1;
                            /*CustomDialogClass d=new CustomDialogClass(CCNATopic.this);
                            d.show();*/
                           // correctDialog.show();


                        }
                        else{
                            correct=false;
                            /*
                            CustomDialogClass2 d=new CustomDialogClass2(CCNATopic.this);
                            d.show();*/
                            //wrongDialog.show();

                        }
                        break;
                    case 2:
                        if(answer.equalsIgnoreCase("option_c")){
                            correct=true;
                            mark+=1;
                            /*CustomDialogClass d=new CustomDialogClass(CCNATopic.this);
                            d.show();*/
                            //correctDialog.show();


                        }
                        else{
                            correct=false;
                            /*CustomDialogClass2 d=new CustomDialogClass2(CCNATopic.this);
                            d.show();*/
                            //wrongDialog.show();

                        }
                        break;
                    case 3:
                        if(answer.equalsIgnoreCase("option_d")) {
                            correct = true;
                            mark+=1;
                            /*CustomDialogClass d=new CustomDialogClass(CCNATopic.this);
                            d.show();*/
                           // correctDialog.show();


                        }
                        else{
                            correct=false;
                            /*CustomDialogClass2 d=new CustomDialogClass2(CCNATopic.this);
                            d.show();*/
                            //wrongDialog.show();

                        }
                        break;
                    case 4:
                        if(answer.equalsIgnoreCase("option_e")) {
                            correct=true;
                            mark+=1;
                            /*CustomDialogClass d=new CustomDialogClass(CCNATopic.this);
                            d.show();*/
                            //correctDialog.show();


                        }
                        else{
                            correct=false;
                            /*CustomDialogClass2 d=new CustomDialogClass2(CCNATopic.this);
                            d.show();*/
                            //wrongDialog.show();

                        }
                        break;
                }
            }
        });
        optionlayout.addView(rg);

    }

    private String getJsonFromAssetFolder(){
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getAssets().open(
                    "TOPIC01.txt")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        myjsonstring = sb.toString();
        return myjsonstring;

    }
    private void parseJsonAndSaveData(String jsonString){

        try {

            JSONObject jsonObjMain = new JSONObject(myjsonstring);



            JSONArray jsonArray = jsonObjMain.getJSONArray("questions");

            size=jsonArray.length();
            //for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jsonObj = jsonArray.getJSONObject(i);


                question_id = jsonObj.getString("question_id");
                question_name = jsonObj.getString("question_name");
                option_a = jsonObj.getString("option_a");
                option_b = jsonObj.getString("option_b");
                option_c = jsonObj.getString("option_c");
                option_d = jsonObj.getString("option_d");
                option_e = jsonObj.getString("option_e");
                answer=jsonObj.getString("answer");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}