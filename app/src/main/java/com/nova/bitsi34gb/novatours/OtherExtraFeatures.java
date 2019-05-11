package com.nova.bitsi34gb.novatours;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
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
    SharedPreferences preferences ;
    static List<String> list;
    static List<String> list1;
    static HashMap<String,String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_extra_features);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        preferences = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
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

        ImageButton sellerbut = (ImageButton)findViewById(R.id.sellerbutt);
        sellerbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtherExtraFeatures.this);
                View mView = View.inflate(OtherExtraFeatures.this,R.layout.formforseller,null);
                /*editText = (EditText)mView.findViewById(R.id.editText36);*/
                builder.setView(mView);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final EditText nameofseller = mView.findViewById(R.id.nameofseller);
                final EditText productofseller = mView.findViewById(R.id.productofseller);
                Button button2 = mView.findViewById(R.id.buttonforseller);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String productname;
                        String sellername;

                        if(nameofseller.getText().toString()!= null && productofseller.getText().toString()!= null) {
                            sellername = nameofseller.getText().toString();
                            productname = productofseller.getText().toString();
                            sendforseller(sellername,productname,preferences.getString("userid","0").toUpperCase());
                            alertDialog.cancel();
                        }
                        else {
                            Toast.makeText(OtherExtraFeatures.this,"Fill the old and new password coloumns ",Toast.LENGTH_LONG);
                        }
                    }
                });

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

    public void sendforseller(String selle,String prod,String name){
        String emails = hashMap.get("aboutseller");
        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        intent.putExtra(Intent.EXTRA_TEXT, selle+ "  "+ prod);
        intent.setPackage("com.google.android.gm");
        if (intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
        else
            Toast.makeText(this,"Gmail App is not installed",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
        list.clear();
        list1.clear();
        hashMap.clear();
    }
}
