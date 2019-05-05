package com.nova.bitsi34gb.novatours;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class ContactUs extends Activity {
    Button button1,button2,button3,button4,button5,button6;
static HashMap<String,String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        button1 = findViewById(R.id.contact1);
        button2 = findViewById(R.id.contact2);
        button3 = findViewById(R.id.contact3);
        button4 = findViewById(R.id.contact4);
        button5 = findViewById(R.id.contact5);
        button6 = findViewById(R.id.contact6);
        getDatafrom();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pho = hashMap.get("abouttripphone");
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pho, null));
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = hashMap.get("abouttripemail");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pho = hashMap.get("foraccountphone");
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pho, null));
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = hashMap.get("foraccountemail");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pho = hashMap.get("welcomekitphone");

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", pho, null));
                startActivity(intent);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String emails = hashMap.get("welcomekitemail");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));

            }
        });
    }
public void getDatafrom(){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("contact" );
    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
           for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                         hashMap.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
           }
        }
        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });
}

    public void onDestroy() {

        super.onDestroy();
        hashMap.clear();
    }
}
