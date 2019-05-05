package com.nova.bitsi34gb.novatours;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class OtherExtraFeatures extends Activity {
    private ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    static List<String> list;
    static List<String> list1;
    static HashMap<String,String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_extra_features);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("imagesfordts");
         list = new ArrayList<>();
         list1 = new ArrayList<>();
        progressDialog = new ProgressDialog(OtherExtraFeatures.this);
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        getDatafrom();
        final RecyclerView bikeRecycler = findViewById(R.id.recylerimagefordts);
        bikeRecycler.setHasFixedSize(true);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.canScrollVertically();

        bikeRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bikeRecycler.setLayoutManager(gridLayoutManager);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    list.add(dataSnapshot1.getValue().toString());
                    list1.add(dataSnapshot1.getKey());

                }
                RecyclerViewForDts playAdapter = new RecyclerViewForDts(OtherExtraFeatures.this,list,list1,hashMap);
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
    @Override
    public void onDestroy() {

        super.onDestroy();
        list.clear();
        list1.clear();
        hashMap.clear();
    }
}
