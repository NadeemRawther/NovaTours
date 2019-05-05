package com.nova.bitsi34gb.novatours;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ViewTrips extends Activity {
   // List<> arra = new ArrayList<>();
   static List<String> list;
   ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trips);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("images");
        final RecyclerView bikeRecycler = (RecyclerView)findViewById(R.id.recylerimage);
        bikeRecycler.setHasFixedSize(true);
        //nce to an image file in Cloud Storage


        list = new ArrayList<>();
        progressDialog = new ProgressDialog(ViewTrips.this);
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bikeRecycler.setLayoutManager(layoutManager);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    list.add(dataSnapshot1.getValue().toString());


                }
                RecycleForImage playAdapter = new RecycleForImage(ViewTrips.this,list);
                bikeRecycler.setAdapter(playAdapter);
                progressDialog.dismiss();

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
        list.clear();

    }


}
