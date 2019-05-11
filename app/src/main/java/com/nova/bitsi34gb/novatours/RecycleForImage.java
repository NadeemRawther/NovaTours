package com.nova.bitsi34gb.novatours;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecycleForImage extends  RecyclerView.Adapter<RecycleForImage.ViewHolder> {
    private ProgressDialog progressDialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("images" );
    private Context context;
    List<String> list;
    DatabaseReference myRef1 = database.getReference("images/images1");
    DatabaseReference myRef2 = database.getReference("images/images2");
    DatabaseReference myRef3 = database.getReference("images/images3");
    DatabaseReference myRef4 = database.getReference("images/images4");
    DatabaseReference myRef5 = database.getReference("images/images5");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    // Reference to an image file in Cloud Storage
    public RecycleForImage(Context context,List<String> list1) {
        this.context = context;
        this.list = list1;

        /*progressDialog = new ProgressDialog(this.context);
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        mydatacreation();
        progressDialog.dismiss();*/
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);

        }
    }

    @Override
    public RecycleForImage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.imageforviewtrips,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int i;
        final CardView cardView = holder.cardView;
        Context context = cardView.getContext();
        final ImageView imageView = (ImageView)cardView.findViewById(R.id.imageView);


        try{

              Glide.with(context).load(list.get(position)).into(imageView);
              //progressDialog.dismiss();

        }catch (Exception e){
            e.printStackTrace();
            Toast toast = Toast.makeText(context,"its error",Toast.LENGTH_LONG);
            toast.show();
        }


    }

    public void mydatacreation(){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                 list.add(dataSnapshot1.getValue().toString());
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

        int length = list.size();
        return length;
    }
}