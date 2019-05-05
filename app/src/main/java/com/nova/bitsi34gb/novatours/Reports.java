package com.nova.bitsi34gb.novatours;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Reports extends Activity {
SharedPreferences sharedPref;
TextView textView,textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        textView = (TextView)findViewById(R.id.textView63);
        textView1 = (TextView)findViewById(R.id.textView65);
        textView2 = (TextView)findViewById(R.id.textView67);
        sharedPref = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        textView.setText(sharedPref.getString("dateofjoining","defa"));
        textView1.setText(sharedPref.getString("dateofpayment","defa"));
        textView2.setText(sharedPref.getString("earnings","defa"));

    }
}
