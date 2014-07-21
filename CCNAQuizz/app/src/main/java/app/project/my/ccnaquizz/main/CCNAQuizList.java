package app.project.my.ccnaquizz.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import app.project.my.ccnaquizz.R;
import app.project.my.ccnaquizz.user.Login;


public class CCNAQuizList extends Activity {
    String[] topic=null ;
    String[] desc=null ;
    public static boolean bought=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccna_row_list);

       topic = new String[] { "Cisco CCNA 640-461 Voic Quiz","Cisco CCNA 640-721 Wireless Quiz","Cisco CCNA 640-802 Quiz",
               "Cisco CCNA 640-802 Quiz"," Cisco CCNA 640-822 Quiz",
               "Cisco DCICN 640-911 Quiz","Cisco DCICT 640-916 Quiz","Cisco CCNP 642-813 Switch Quiz","Cisco CCNP 642-902 Route Quiz" }
        ;

        desc=new String[]{"Description01","Description02","Description03","Description04","Description05",
        "Description06","Description07","Description08","Description09"};
        ListView ll = (ListView) findViewById(R.id.listview);

        CustomAdapter cus = new CustomAdapter(topic,desc);
        ll.setAdapter(cus);


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent i = new Intent(CCNAQuizList.this,Login.class);
            //i.putExtra("topic", vh.name.getText().toString());
            startActivity(i);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    class CustomAdapter extends BaseAdapter
    {
        LayoutInflater mInflater;
        String[] topic,desc;

        public CustomAdapter(String[] topicArr,String[] descArr)
        {
            topic=topicArr;
            desc=descArr;
            mInflater = (LayoutInflater) CCNAQuizList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return topic.length;//listview item count.
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ViewHolder vh;
            vh= new ViewHolder();

            if(convertView==null )
            {
                convertView=mInflater.inflate(R.layout.ccna_row_listitem, parent,false);
                //inflate custom layour
                vh.name= (TextView)convertView.findViewById(R.id.text);
                vh.des = (TextView) convertView.findViewById(R.id.des);

                vh.trial = (Button) convertView.findViewById(R.id.trial);

                vh.buy = (Button) convertView.findViewById(R.id.buy);
                //vh.tv2.setText("Position = "+position);
                vh.name.setText(topic[position]);
                //set text of second textview based on position
                vh.des.setText(desc[position]);
                vh.trial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Intent i = new Intent(CCNAQuizList.this,MainActivity.class);
                        i.putExtra("topic", vh.name.getText().toString());
                        startActivity(i);*/
                        Intent i = new Intent(CCNAQuizList.this,CCNATopic.class);
                        i.putExtra("topic", vh.name.getText().toString());
                        startActivity(i);

                    }
                });
                if(bought==true){
                    vh.buy.setText("bought");
                }

                vh.buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(bought==true){
                            Intent i = new Intent(CCNAQuizList.this,CCNATopic.class);
                            i.putExtra("topic", vh.name.getText().toString());
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(CCNAQuizList.this, CCNABuy.class);
                            i.putExtra("topic", vh.name.getText().toString());

                            startActivity(i);
                        }

                    }
                });

            }
            else
            {
                convertView.setTag(vh);
            }

            return convertView;
        }

        class ViewHolder
        {
            TextView name,des;
           public Button trial,buy;
        }

    }
}