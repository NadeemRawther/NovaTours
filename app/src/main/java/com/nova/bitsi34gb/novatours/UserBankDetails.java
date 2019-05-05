package com.nova.bitsi34gb.novatours;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserBankDetails extends Fragment {
ProgressDialog progressDialog;
Context context;
    static HashMap<String,String> hashMap1 = new HashMap<>();
    public UserBankDetails() {
        // Required empty public constructor
    }
TextView textView,textView1,textView2,textView3,textView4,textView5,textView6;
    static HashMap<String,String> hashMap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_user_bank_details, container, false);

        textView = (TextView)view.findViewById(R.id.textView73);
        textView1 = (TextView)view.findViewById(R.id.textView78);
        textView2 = (TextView)view.findViewById(R.id.textView74);
        textView3 = (TextView)view.findViewById(R.id.textView80);
        textView4 = (TextView)view.findViewById(R.id.textView82);
         String userid = getArguments().getString("userids");
textView5 = (TextView)view.findViewById(R.id.textView77);
        textView6 = (TextView)view.findViewById(R.id.textView81);
        textView6.setText(userid);
//need to make it only listen to its path
        getuserbankdetails(userid);




        return view;
    }


    public void getuserbankdetails(final String userid){
        progressDialog = new ProgressDialog(UserBankDetails.this.getActivity());
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bank" );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.child(userid).getChildren()){
                    hashMap1.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                }
                textView.setText(hashMap1.get("name"));
                textView1.setText(hashMap1.get("bankname"));
                textView2.setText(hashMap1.get("branch"));
                textView3.setText(hashMap1.get("accountno"));
                textView4.setText(hashMap1.get("ifsccode"));
                textView5.setText(hashMap1.get("name"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    progressDialog.dismiss();
    }

}
