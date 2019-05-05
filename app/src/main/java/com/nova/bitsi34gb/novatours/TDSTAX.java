package com.nova.bitsi34gb.novatours;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class TDSTAX extends Activity {
SharedPreferences sharedPref;
    TextView textView,textView1,textView2,textView3,textView4,textView5;
    Integer earning;
    Integer valusgst;
    Integer valuservice;
    Integer valu2;
    String earningfortext;
    String valusgsts;
    String valuservices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdstax);
        sharedPref = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        textView = (TextView)findViewById(R.id.textView54);
        textView1= (TextView)findViewById(R.id.textView55);
        textView2= (TextView)findViewById(R.id.textView56);
        textView3= (TextView)findViewById(R.id.textView57);

      textView5= (TextView)findViewById(R.id.textView59);
      textView.setText(sharedPref.getString("earnings","0"));
      String value = sharedPref.getString("earnings","0");
     if(!value.equals("")) {
         int valu = Integer.parseInt(value);
         valusgst = valu * 9 / 100;
         valuservice = valu * 5 / 100;
         valu2 = valusgst * 2;
         earning = valu - valu2 - valuservice;
         earningfortext = earning.toString();
         valusgsts = valusgst.toString();
         valuservices = valuservice.toString();
     }

      textView1.setText(valusgsts);
      textView2.setText(valusgsts);
      textView3.setText(valuservices);
      textView5.setText(earningfortext);

    }
}
