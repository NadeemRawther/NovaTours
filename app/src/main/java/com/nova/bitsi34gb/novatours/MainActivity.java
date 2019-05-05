package com.nova.bitsi34gb.novatours;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.ContentValues.TAG;
//this is the main activity or launcher and login activity where we need to go to user page or admin page from here
public class MainActivity extends Activity {
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 12 ;
    private FirebaseAuth mAuth;
    String valueforadmin,emailforadmin,passwordforadmin;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    SharedPreferences sharedPref;
ProgressDialog progressDialog;

    SharedPreferences.Editor editor;
    static HashMap<String,String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isNetworkAvailable()){
            Toast.makeText(MainActivity.this,"Internet not available please connect ",Toast.LENGTH_LONG).show();

        }




        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?


            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
        final Button button = findViewById(R.id.button);
        final AutoCompleteTextView autoCompleteTextView;
        final EditText editText;
        sharedPref = getApplicationContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        autoCompleteTextView = findViewById(R.id.email);
        editText = findViewById(R.id.password);
       // mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("please Wait while loading");
                progressDialog.show();
                final String email = autoCompleteTextView.getText().toString();
                final String password = editText.getText().toString();
                if(email.isEmpty()|| password.isEmpty()) {
                Toast.makeText(MainActivity.this,"Userid and Password should not be empty",Toast.LENGTH_LONG).show();
                }
                else {
                                        signinuser(email,password);
                }
            }
        });

//Method to recover or change password
   /*     TextView forgetpass = (TextView)findViewById(R.id.textView95);
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View mView = View.inflate(MainActivity.this,R.layout.formforforgetpass,null);
                editText = (EditText)mView.findViewById(R.id.editText36);
                editText2 = (EditText) mView.findViewById(R.id.editText38);
                final EditText submituseridforrec = (EditText)mView.findViewById(R.id.useridforrec);
                Button button2 =(Button)mView.findViewById(R.id.submitforpassrec);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String textforuserid;
                        if(submituseridforrec.getText().toString()!= null) {
                            textforuserid = submituseridforrec.getText().toString();
                        //url = https://console.firebase.google.com/project/novatours-9e426/overview
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Fill the userid ",Toast.LENGTH_LONG);
                        }
                    }
                });

                builder.setView(mView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                String emails = hashMap.get("abouttripemail");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
                email.putExtra(Intent.EXTRA_SUBJECT, list1.get(position).toString());
                email.putExtra(Intent.EXTRA_TEXT,  preferences.getString("phoneno","defa") );
                email.setType("message/rfc822");
                context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    */
}
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       // FirebaseUser currentUser = mAuth.getCurrentUser();
        //if (currentUser!= null)getIn();
        sharedPref = getApplicationContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        String email1 =  sharedPref.getString("userid","d");
        String password = sharedPref.getString("password","d");
        signinuser(email1 ,password);

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    public void signinuser(final String email, final String password){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//need to make it only listen to its path
        DatabaseReference myRef2 = database.getReference("admin" );





        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.getChildren().toString();
             for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                 if(dataSnapshot1.child("userid").getValue().equals(email)&& dataSnapshot1.child("password").getValue().equals(password)){

                     sharedPref = getApplicationContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
                     editor = sharedPref.edit();
                          editor.putString("userid",dataSnapshot1.child("userid").getValue().toString());
                          editor.putString("password",dataSnapshot1.child("password").getValue().toString());
                          editor.putString("start",dataSnapshot1.child("start").getValue().toString());
                          editor.putString("person", dataSnapshot1.getKey());
                          editor.apply();
                       Intent intent = new Intent(MainActivity.this,Admin.class);
                       //intent.putExtra(valueforadmin,dataSnapshot1.child("start").getValue().toString());
                       //intent.putExtra(emailforadmin,dataSnapshot1.child("userid").getValue().toString());
                       //intent.putExtra(passwordforadmin,dataSnapshot1.child("password").getValue().toString());
                       startActivity(intent);
                 }
                 else{
                     signinnormaluser(email,password);
                 }
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });









    }

    public void signinnormaluser(final String email, final String password){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(email)){
                    hashMap.put("userid",email);
                    for(DataSnapshot dataSnapshot1:dataSnapshot.child(email).getChildren()){

                        hashMap.put(dataSnapshot1.getKey(),dataSnapshot1.getValue().toString());
                    }
                    if (hashMap.get("password").equals(password)){
                        saveinsharedpref();
                        Intent intent = new Intent(MainActivity.this,Users.class);
                        startActivity(intent);
                    }
                    else{
                       // Toast.makeText(getApplicationContext(),"Cant login without correct password ",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Cant login without correct userid and  password " ,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value. may be internet not connected", error.toException());
            }
        });




    }



    public void saveinsharedpref(){
        editor.putString("password",hashMap.get("password"));
        editor.putString("name",hashMap.get("name"));
        editor.putString("phoneno",hashMap.get("phoneno"));
        editor.putString("panno",hashMap.get("panno"));
        editor.putString("maritalstatus",hashMap.get("maritalstatus"));
        editor.putString("gender",hashMap.get("gender"));
        editor.putString("left",hashMap.get("left"));
        editor.putString("right",hashMap.get("right"));
        editor.putString("parent",hashMap.get("parent"));
        editor.putString("dateofbirth",hashMap.get("dateofbirth"));
        editor.putString("earnings",hashMap.get("earnings"));
        editor.putString("rank",hashMap.get("rank"));
        editor.putString("adhaar",hashMap.get("adhaar"));
        editor.putString("userid",hashMap.get("userid"));
        editor.putString("dateofpayment",hashMap.get("dateofpayment"));
        editor.putString("dateofjoining",hashMap.get("dateofjoining"));
        editor.apply();
        Toast.makeText(getApplicationContext(),"Its connecting",Toast.LENGTH_LONG).show();
    }

}
