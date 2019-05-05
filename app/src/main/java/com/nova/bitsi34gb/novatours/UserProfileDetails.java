package com.nova.bitsi34gb.novatours;


import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileDetails extends Fragment implements View.OnTouchListener {
    EditText editText,editText2,editText3,editText4,editText5,editText6,editText7,editText11,editText12,editText13,editText14,editText15,editText16;
    static HashMap<String,String> hashMap = new HashMap<>();
    Spinner editText8,editText9,editText10;
    int mYear,mMonth,mDay;
    public UserProfileDetails() {
        // Required empty public constructor
    }
    String name,adhaar,panno,phoneno,dateofbirth,gender,rank,maritalstatus,parent,earnings,dateofpayment,dateofjoining,left,right;


    private AwesomeValidation awesomeValidation;
    Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile_details, container, false);

        editText3 = (EditText)view.findViewById(R.id.editText17);
        editText4 = (EditText)view.findViewById(R.id.editText24);
        editText5 = (EditText)view.findViewById(R.id.editText23);
        editText6 = (EditText)view.findViewById(R.id.editText25);
        editText7 = (EditText)view.findViewById(R.id.editText27);
        editText8 = (Spinner)view.findViewById(R.id.editText28);
        editText9 = (Spinner)view.findViewById(R.id.editText29);
        editText10 = (Spinner)view.findViewById(R.id.editText30);

        editText11 = (EditText)view.findViewById(R.id.editText26);

        editText12 = (EditText)view.findViewById(R.id.editText32);
        editText13 = (EditText)view.findViewById(R.id.editText33);
        editText14=(EditText)view.findViewById(R.id.editText31);
        editText15 = (EditText)view.findViewById(R.id.editText34);
        editText16 = (EditText)view.findViewById(R.id.editText35);
        editText13.setOnTouchListener(this);
        editText14.setOnTouchListener(this);
        editText7.setOnTouchListener(this);
        final TextView textView = (TextView)view.findViewById(R.id.textView102);
        final TextView textView1 = (TextView)view.findViewById(R.id.textView83);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        button = (Button)view.findViewById(R.id.button10);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final String userid = getArguments().getString("userids");
        textView.setText(userid);
        //need to make it only listen to its path


        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editText8.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.rank, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editText9.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.marital, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editText10.setAdapter(adapter2);




        DatabaseReference myRef = database.getReference("users" );
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userid)){
                    hashMap.put("userid",userid);
                    for(DataSnapshot dataSnapshot1:dataSnapshot.child(userid).getChildren()){
                        hashMap.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());

                    }
                    editText3.setText(hashMap.get("name"));
                    editText4.setText(hashMap.get("adhaar"));
                    editText5.setText(hashMap.get("panno"));
                    editText6.setText(hashMap.get("phoneno"));
                    editText7.setText(hashMap.get("dateofbirth"));
                    editText8.setSelection(getIndex(editText8,hashMap.get("gender").toString()));
                    editText9.setSelection(getIndex(editText9,hashMap.get("rank").toString()));
                    editText10.setSelection(getIndex(editText10,hashMap.get("maritalstatus").toString()));
                    editText11.setText(hashMap.get("parent"));
                    editText12.setText(hashMap.get("earnings"));
                    editText13.setText(hashMap.get("dateofpayment"));
                    editText14.setText(hashMap.get("dateofjoining"));
                    editText15.setText(hashMap.get("left"));
                    editText16.setText(hashMap.get("right"));
                    textView1.setText(hashMap.get("name"));

                }
                else{
                    //Toast.makeText(UserBankDetails.this.getActivity(),"Cant login without correct userid and  password " ,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value. may be internet not connected", error.toException());
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText17,".{3,}",R.string.nameerror);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText24,".{4,}",R.string.adhaarno);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText23,".*\\S.*",R.string.panerror);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText25,".{6,}",R.string.phoneerror);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText26,"^$|^[A-Za-z\\s][\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText34,"^$|^[0]$|^[A-Za-z\\s][\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        awesomeValidation.addValidation(this.getActivity(),R.id.editText35,"^$|^[0]$|^[A-Za-z\\s][\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);
        button.setOnClickListener(new View.OnClickListener() {
            String useride = getArguments().getString("userids");
            @Override
            public void onClick(View v) {
                setTextforuser(useride);
            }
        });
    }

    public void setTextforuser(String userid){
              if (awesomeValidation.validate()){
                    name =      editText3.getText().toString();
                    adhaar =       editText4.getText().toString();
                       panno = editText5.getText().toString();
                     phoneno =     editText6.getText().toString();
                     dateofbirth =      editText7.getText().toString();
                       gender =   editText8.getSelectedItem().toString();
                        rank =  editText9.getSelectedItem().toString();
                        maritalstatus =  editText10.getSelectedItem().toString();
                        parent =   editText11.getText().toString();
                        earnings = editText12.getText().toString();
                        dateofpayment =  editText13.getText().toString();
                        dateofjoining =   editText14.getText().toString();
                        left =  editText15.getText().toString();
                        right =   editText16.getText().toString();




checkdatabase(userid,name,adhaar,panno,phoneno,dateofbirth,gender,rank,maritalstatus,parent,earnings,dateofpayment,dateofjoining,left,right);

              }

    }
    public void checkdatabase(final String userid ,final String name, final String adhaar, final String panno, final String phoneno, final String dateofbirth, final String gender, final String rank, final String maritalstatus, final String parent, final String earnings, final String dateofpayment, final String dateofjoining, final String left, final String right){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final DatabaseReference myRef = database.getReference("users" );


                    myRef.child(userid).child("name").setValue(name);
                    myRef.child(userid).child("adhaar").setValue(adhaar);
                    myRef.child(userid).child("panno").setValue(panno);
                    myRef.child(userid).child("phoneno").setValue(phoneno);
                    myRef.child(userid).child("dateofbirth").setValue(dateofbirth);
                    myRef.child(userid).child("gender").setValue(gender);
                    myRef.child(userid).child("rank").setValue(rank);
                    myRef.child(userid).child("maritalstatus").setValue(maritalstatus);
                    myRef.child(userid).child("parent").setValue(parent);
                    myRef.child(userid).child("earnings").setValue(earnings);
                    myRef.child(userid).child("dateofpayment").setValue(dateofpayment);
                    myRef.child(userid).child("dateofjoining").setValue(dateofjoining);
                    myRef.child(userid).child("left").setValue(left);
                    myRef.child(userid).child("right").setValue(right);
        Toast.makeText(this.getActivity(),"DataBase edited ",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent hasFocus) {
        if (v == editText13){

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editText13.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();



        }
        if(v == editText14){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editText14.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        }
        if(v == editText7){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(),
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
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


}

