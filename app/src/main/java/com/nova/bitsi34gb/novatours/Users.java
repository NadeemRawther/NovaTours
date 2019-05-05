package com.nova.bitsi34gb.novatours;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Users extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    TextView nameView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences sharedPref;
    ArrayList<NameAndEarnings> object = new ArrayList<>();
    static int counts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        TextView nameView = findViewById(R.id.textView);
        TextView userId = findViewById(R.id.textView2);

if (!isNetworkAvailable()){

    Toast.makeText(Users.this,"Internet not available please connect ",Toast.LENGTH_LONG).show();
}
        sharedPref = getApplicationContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        //findcount(sharedPref.getString("userid","d"));
        String name = sharedPref.getString("name","name");
        String userid = sharedPref.getString("userid","userid");
        nameView.setText(name);
        userId.setText(userid);
        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, BankDetails.class);
                startActivity(intent);
            }
        });
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, Reports.class);
                startActivity(intent);
            }
        });

        Button button8 = findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, Profile.class);
                startActivity(intent);
            }
        });
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, TDSTAX.class);
                startActivity(intent);
            }
        });
        Button button9 = findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, OtherExtraFeatures.class);
                startActivity(intent);
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, ContactUs.class);
                startActivity(intent);
            }
        });
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Users.this, ViewTrips.class);
                startActivity(intent);
            }
        });
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (Users.this, Binary.class);

                startActivity(intent);
            }
        });
        ImageButton button = findViewById(R.id.menuicu);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(Users.this, v);

                MenuInflater inflater = popup.getMenuInflater();
                popup.setOnMenuItemClickListener(Users.this);
                inflater.inflate(R.menu.menu_user_detail_for_admin, popup.getMenu());
                popup.show();
            }
        });


    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Users.this, MainActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.changpass:
                AlertDialog.Builder builder = new AlertDialog.Builder(Users.this);
                View mView = View.inflate(Users.this,R.layout.formforchangingpass,null);
                /*editText = (EditText)mView.findViewById(R.id.editText36);*/
                builder.setView(mView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final EditText submitpassforchang = mView.findViewById(R.id.newpass);
                final EditText submitoldpass = mView.findViewById(R.id.oldpass);
                Button button2 = mView.findViewById(R.id.submitforchangepass);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String textforoldpass;
                        String textfornewpass;

                        if(submitoldpass.getText().toString()!= null && submitpassforchang.getText().toString()!= null) {
                            textforoldpass = submitoldpass.getText().toString();
                            textfornewpass = submitpassforchang.getText().toString();
                            checkandchangedatabase(textfornewpass,textforoldpass);
                            alertDialog.cancel();
                        }
                        else {
                            Toast.makeText(Users.this,"Fill the old and new password coloumns ",Toast.LENGTH_LONG);
                        }
                    }
                });
                return true;
            default:
                return false;
        }
    }



    /*  public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_detail_for_admin, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
               //3 startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void checkandchangedatabase(final String newpass , final String oldpass){

        final DatabaseReference myRef = database.getReference("users").child(sharedPref.getString("userid","0"));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("password").getValue().toString().equals(oldpass)){
                    myRef.child("password").setValue(newpass);
                    Toast.makeText(Users.this,"Password changed",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(Users.this,"Password doesn't matches",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);

                              /*  Intent intent = new Intent(Users.this, MainActivity.class);
                                intent.putExtra("finish", true);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);*/
                                finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }






}