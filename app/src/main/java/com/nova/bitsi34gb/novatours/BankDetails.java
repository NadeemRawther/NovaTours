package com.nova.bitsi34gb.novatours;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BankDetails extends Activity {
EditText editText,editText1,editText2,editText3,editText4;
SharedPreferences sharedPreferences;
ProgressDialog progressDialog;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        editText = (EditText)findViewById(R.id.editText5);
        editText1 = (EditText)findViewById(R.id.editText4);
        editText2 = (EditText)findViewById(R.id.editText3);
        editText3 = (EditText)findViewById(R.id.editText2);
        editText4 = (EditText)findViewById(R.id.editText);
        sharedPreferences = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(BankDetails.this,R.id.editText5,"^[A-Za-z\\s]{2,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.userid);

        awesomeValidation.addValidation(BankDetails.this,R.id.editText4,".{3,}",R.string.userid);
        awesomeValidation.addValidation(BankDetails.this,R.id.editText3,".{1,}",R.string.userid);
        awesomeValidation.addValidation(BankDetails.this,R.id.editText2,".{1,}",R.string.userid);
        awesomeValidation.addValidation(BankDetails.this,R.id.editText,".{2,}",R.string.userid);


        Button button = (Button)findViewById(R.id.button13);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate()) {
                progressDialog = new ProgressDialog(BankDetails.this);
                progressDialog.setMessage("please Wait while loading");
                progressDialog.show();

                    final String name = editText.getText().toString();
                    final String bankname = editText4.getText().toString();
                    final String accountno = editText2.getText().toString();

                    final String ifsccode = editText1.getText().toString();
                    final String branch = editText3.getText().toString();
                    databased(name, bankname, accountno, ifsccode, branch);

                }
            }
        });
    }
public void databased(final String name, final String bankname, final String accountno, final String ifsccode , final String branch){

    FirebaseDatabase database = FirebaseDatabase.getInstance();
     final String uid = sharedPreferences.getString("userid","");
    final DatabaseReference myRef = database.getReference("bank" );
   /* myRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
*/
          /*  if (dataSnapshot.hasChild(uid))
            {
                Toast.makeText(Join.this,"Userid already exists",Toast.LENGTH_LONG).show();
                return ;

            }*/


                Bankclass user = new Bankclass(accountno,bankname,branch ,ifsccode,name);
                myRef.child(uid).setValue(user);
                progressDialog.dismiss();
                Toast.makeText(BankDetails.this,"Bank Details Added ",Toast.LENGTH_LONG).show();


       /* }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });*/


}
}
