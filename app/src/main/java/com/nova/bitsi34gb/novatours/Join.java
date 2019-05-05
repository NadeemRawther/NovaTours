package com.nova.bitsi34gb.novatours;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class Join extends AppCompatActivity implements View.OnTouchListener {
EditText editText,editText2,editText3,editText4,editText7,editText8,editText9,editText10,editText12,editText13,editText14,editText15,editText16;
Button button ;
int mYear,mDay,mMonth;
Spinner spinner5,spinner6,spinner7;
    private DatabaseReference mDatabase;
    private AwesomeValidation awesomeValidation;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    SharedPreferences sharedPref;
    String rank;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        sharedPref = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editText = findViewById(R.id.userid);
        editText2 = findViewById(R.id.password);
        editText3 = findViewById(R.id.name);
        editText4 = findViewById(R.id.rightuserid);
        spinner6 = findViewById(R.id.gender);
        spinner5 = findViewById(R.id.marital);
        editText7 = findViewById(R.id.dateofbirth);
        editText8 = findViewById(R.id.earnings);
        editText9 = findViewById(R.id.leftuserid);
        editText10 = findViewById(R.id.rightuserid);

        editText12 = findViewById(R.id.parent);
        editText13 = findViewById(R.id.pannos);
        editText14 = findViewById(R.id.phone);
        editText15 = findViewById(R.id.dateofpayment);
        editText16 = findViewById(R.id.dateofjoining);
        editText15.setOnTouchListener(this);
        editText16.setOnTouchListener(this);
        editText7.setOnTouchListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.marital, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner6.setAdapter(adapter1);

        switch (sharedPref.getString("person","NA")){
            case "gold":
                rank = "gold";
                break;
            case "platinum":
                rank = "platinum";
                break;
            case "silver":
                rank = "silver";
                break;
            case "platinumthree":
                rank = "platinum";
                break;
            case "platinumtwo":
                rank = "platinum";
                break;
        }
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(Join.this,R.id.userid,"^[A-Za-z\\s]{8,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        awesomeValidation.addValidation(Join.this,R.id.password,".{8,}",R.string.passworderror);
        awesomeValidation.addValidation(Join.this,R.id.name,".{3,}",R.string.nameerror);
        awesomeValidation.addValidation(Join.this,R.id.rightuserid,".{4,}",R.string.adhaarno);
        awesomeValidation.addValidation(Join.this,R.id.dateofbirth,".{5,}",R.string.dateofbirtherror);
        awesomeValidation.addValidation(Join.this,R.id.earnings,"^[0-9]*$",R.string.earningserror);
        awesomeValidation.addValidation(Join.this,R.id.leftuserid,"^$|^[A-Za-z\\s][\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        awesomeValidation.addValidation(Join.this,R.id.rightuserid,"^$|^[A-Za-z\\s][\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        awesomeValidation.addValidation(Join.this,R.id.parent,"^$|^[A-Za-z\\s]{3,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        awesomeValidation.addValidation(Join.this,R.id.pannos,".*\\S.*",R.string.panerror);
        awesomeValidation.addValidation(Join.this,R.id.phone,".{6,}",R.string.phoneerror);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        button = findViewById(R.id.button17);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = editText.getText().toString();
                String password = editText2.getText().toString();
                String name = editText3.getText().toString();
                String adhaar = editText4.getText().toString() ;
                String gender = spinner6.getSelectedItem().toString();
                String maritalstatus = spinner5.getSelectedItem().toString();
                String dateofbirth = editText7.getText().toString();
                String Earnings = editText8.getText().toString();
                String left = editText9.getText().toString();
                String right = editText10.getText().toString();
                String parent = editText12.getText().toString();
                String panno = editText13.getText().toString();
                String phoneno = editText14.getText().toString();
                String dateofpayment = editText15.getText().toString();
                String dateofjoining = editText16.getText().toString();
                if( awesomeValidation.validate()){
                    Toast.makeText(Join.this,"there is errror",Toast.LENGTH_LONG).show();
                    if (left.equals("") && right.equals("")){
                        left = "0";
                        right = "0";
                    }
                    checkdatabase(userid,password,name,adhaar,gender,maritalstatus,dateofbirth,Earnings,left,right,rank,parent,panno,phoneno,dateofpayment,dateofjoining);

                }

            }
        });
    }





    public void checkdatabase(final String uid, final String password, final String name, final String adhaar, final String gender, final String maritalstatus, final String dateofbirth, final String Earnings , final String left, final String right , final String rank , final String parent,final String panno, final String phoneno,final String dateofpayment,final String dateofjoining){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("users" );
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(uid))
                {
                    Toast.makeText(Join.this,"Userid already exists",Toast.LENGTH_LONG).show();
                    return ;

                     }
                     else {

                    User user = new User(password , name , adhaar, gender, maritalstatus,dateofbirth,Earnings , left,right,rank, parent,panno,phoneno,dateofjoining,dateofpayment);

                    myRef.child(uid).setValue(user);
                    Toast.makeText(Join.this,"Userid Created",Toast.LENGTH_LONG).show();
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
    public boolean onTouch(View v,MotionEvent motionEvent) {
        if (v == editText15){

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editText15.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();



        }
         else if(v == editText16){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editText16.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }
       else if(v == editText7){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editText7.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        return false;
    }
}
