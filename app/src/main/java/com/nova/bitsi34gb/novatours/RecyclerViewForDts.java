package com.nova.bitsi34gb.novatours;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerViewForDts extends RecyclerView.Adapter<RecyclerViewForDts.ViewHolder>{
    SharedPreferences preferences;
    private ProgressDialog progressDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String phone,names,emails;
    DatabaseReference myRef = database.getReference("imagesfordts");
EditText editText,editText2,editText3;
Button button;
    private Context context;
    List<String> list;
    List<String> list1;
    HashMap<String,String> hashMap;

    static List<String> list2 = new ArrayList<>();
     static List<String> list3 = new ArrayList<>();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    // Reference to an image file in Cloud Storage
    public RecyclerViewForDts(Context context,List<String> list,List<String> list1,HashMap<String,String> hashMap) {
        this.context = context;
        preferences = context.getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        this.list = list;
        this.list1 = list1;
        this.hashMap = hashMap;
      // mydatacreation();
        //progressDialog.dismiss();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view2);

        }
    }

    @Override
    public RecyclerViewForDts.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.imagefordts,parent,false);
        return new RecyclerViewForDts.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewForDts.ViewHolder holder, final int position) {
        final int i;
        final CardView cardView = holder.cardView;
        final Context context = cardView.getContext();
        final ImageView imageView = (ImageView)cardView.findViewById(R.id.button12);
        final TextView textView = (TextView)cardView.findViewById(R.id.textView14);

        try{

            Glide.with(context).load(list.get(position)).into(imageView);
            textView.setText(list1.get(position));

           // progressDialog.dismiss();

        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(context,"its error",Toast.LENGTH_LONG);
            toast.show();
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mView = View.inflate(context,R.layout.formfordts,null);
               editText = (EditText)mView.findViewById(R.id.editText36);
               editText2 = (EditText) mView.findViewById(R.id.editText38);
               editText3 = (EditText)mView.findViewById(R.id.editText39);
               button =(Button)mView.findViewById(R.id.button15);
               button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                      names = editText.getText().toString();
                      emails = editText3.getText().toString();
                      phone = editText2.getText().toString();

                       if(!names.isEmpty() && !emails.isEmpty() && !phone.isEmpty()) {
                           String emailser = hashMap.get("abouttripemail");
                           Intent email = new Intent(Intent.ACTION_SEND);
                           email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailser});
                           email.putExtra(Intent.EXTRA_SUBJECT, list1.get(position).toString());
                           email.putExtra(Intent.EXTRA_TEXT, names + "\n" + phone);
                           email.setType("message/rfc822");
                           context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
                       }
                       else{
                           Toast.makeText(context,"fields should not be empty",Toast.LENGTH_LONG).show();
                       }
                   }
               });

              builder.setView(mView);
              AlertDialog alertDialog = builder.create();
              alertDialog.show();

                /*
                String emails = hashMap.get("abouttripemail");
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
                email.putExtra(Intent.EXTRA_SUBJECT, list1.get(position).toString());
                email.putExtra(Intent.EXTRA_TEXT,  preferences.getString("phoneno","defa") );
                email.setType("message/rfc822");
                context.startActivity(Intent.createChooser(email, "Choose an Email client :"));*/

            }
        });


    }

    public void mydatacreation(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    list2.add(dataSnapshot1.getValue().toString());
                    list3.add(dataSnapshot1.getKey().toString());

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
    public int getItemCount() {

        int length = list1.size();
        return length;
    }
}
