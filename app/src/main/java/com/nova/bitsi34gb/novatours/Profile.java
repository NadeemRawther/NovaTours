package com.nova.bitsi34gb.novatours;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Profile extends Activity {
    TextView textView,textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8;
    SharedPreferences preferences ;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferences = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(Profile.this);
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        textView = (TextView)findViewById(R.id.textView38);
        textView1 = (TextView)findViewById(R.id.textView39);
        textView2 = (TextView)findViewById(R.id.textView44);
        textView3 = (TextView)findViewById(R.id.textView45);
        textView4 = (TextView)findViewById(R.id.textView46);
        textView5 = (TextView)findViewById(R.id.textView47);
        textView6 = (TextView)findViewById(R.id.textView48);
        textView7 = (TextView)findViewById(R.id.textView49);
        textView8 = (TextView)findViewById(R.id.textView51);
        textView.setText(preferences.getString("name","defa").toUpperCase());
        textView1.setText(preferences.getString("adhaar","defa").toUpperCase());
        textView2.setText(preferences.getString("phoneno","defa").toUpperCase());
        textView3.setText(preferences.getString("panno","defa").toUpperCase());
        textView4.setText(preferences.getString("dateofbirth","defa").toUpperCase());
        textView5.setText(preferences.getString("gender","defa").toUpperCase());
        textView6.setText(preferences.getString("rank","defa").toUpperCase());
        textView7.setText(preferences.getString("maritalstatus","defa").toUpperCase());
        textView8.setText(preferences.getString("parent","defa").toUpperCase());

        progressDialog.dismiss();




    }
}
