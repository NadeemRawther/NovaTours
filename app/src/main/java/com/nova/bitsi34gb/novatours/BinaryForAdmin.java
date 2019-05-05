package com.nova.bitsi34gb.novatours;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.ViewHolder;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class BinaryForAdmin extends AppCompatActivity {
    Graph graph = new Graph();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ProgressDialog progressDialog;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String parentid;
    ArrayList<NameAndEarnings> arrayList = new ArrayList<>();
     List<NameAndEarnings> list = new ArrayList<>();
    List<String> list2= new ArrayList<>();
     GraphView graphView;
    String left, right,rank, name;
    ArrayList<String> list1 = new ArrayList<>();
    Stack<String> stack = new Stack<>();
    private static final String TAG1 = "NadeemActivity2";
    public BinaryForAdmin() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary_for_admin);
        //graphView = findViewById(R.id.graph3);
        progressDialog = new ProgressDialog(BinaryForAdmin.this);
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
        sharedPref = getApplicationContext().getSharedPreferences("Mypref",Context.MODE_PRIVATE);
        parentid = sharedPref.getString("start","0");
        Log.e(TAG1, parentid);

       makeTree(parentid);
    }



    public void methodinBinary(final ArrayList<NameAndEarnings> list2){
        Graph graph = new Graph();
        graphView = findViewById(R.id.graph3);
        for (NameAndEarnings nameAndEarnings : list2) {



            String userid = nameAndEarnings.getUserid();
            String left = nameAndEarnings.getLeft();
            String right = nameAndEarnings.getRight();
            String rank = nameAndEarnings.getRank();
            //Toast.makeText(Binary.this, userid + " " + left + " " + right + " " + rank, Toast.LENGTH_LONG).show();

            if (!left.equals("0")) {
                graph.addEdge(new Node(userid), new Node(left));
            }
            if (!right.equals("0")) {
                graph.addEdge(new Node(userid), new Node(right));
            }

        }


        final BaseGraphAdapter<ViewHolder> adapter = new BaseGraphAdapter<ViewHolder>(graph) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userchain, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, Object data,final int position) {
                ((SimpleViewHolder)viewHolder).cardView.setCardBackgroundColor(Color.TRANSPARENT);
                ((SimpleViewHolder)viewHolder).mTextView.setText(data.toString());

                if(list2.get(position).getRank().equals("gold")){
                    ((SimpleViewHolder)viewHolder).imageView.setImageResource(R.mipmap.gold);
                }
                else if (list2.get(position).getRank().equals("silver")){
                    ((SimpleViewHolder)viewHolder).imageView.setImageResource(R.mipmap.silver);
                }
                else if (list2.get(position).getRank().equals("platinum")){
                    ((SimpleViewHolder)viewHolder).imageView.setImageResource(R.mipmap.platinum);
                }
                ((SimpleViewHolder)viewHolder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BinaryForAdmin.this,"fetching user data",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(BinaryForAdmin.this,UserDetailed.class);
                        intent.putExtra("userid",data.toString()/*list2.get(position).getUserid()*/);
                        startActivity(intent);
                    }
                });

            }
        };
        graphView.setAdapter(adapter);

        // set the algorithm here
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        adapter.setAlgorithm(new BuchheimWalkerAlgorithm(configuration));
        progressDialog.dismiss();
    }


    private static class SimpleViewHolder extends ViewHolder {
        TextView mTextView;
        ImageView imageView;
        CardView cardView;
        public SimpleViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.textView);
            cardView = view.findViewById(R.id.card_view);
           imageView = view.findViewById(R.id.imgforuser);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BinaryForAdmin.this, Admin.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        list.clear();
        startActivity(intent);

    }




    public void makeTree(final String userid){

        final DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String conc = dataSnapshot.child(userid).getKey();

                while (!conc.equals("0")|| !stack.empty()) {

                    while (!conc.equals("0")) {
                        stack.push(conc);
                        list2.add(conc);
                        left = dataSnapshot.child(conc).child("left").getValue().toString();
                        right = dataSnapshot.child(conc).child("right").getValue().toString();
                        rank = dataSnapshot.child(conc).child("rank").getValue().toString();
                        name = dataSnapshot.child(conc).child("name").getValue().toString();
                        NameAndEarnings nameAndEarnings = new NameAndEarnings(name,conc,left,right,rank);
                        arrayList.add(nameAndEarnings);
                        conc = dataSnapshot.child(conc).getKey();

                        conc = left;
                    }
                    if(!stack.peek().equals("0")){

                    }
                    conc = stack.pop();

                    conc = dataSnapshot.child(conc).child("right").getValue().toString();


                }
                for(int j = 0; j < list2.size();j++){
                    Log.e(TAG1, arrayList.get(j).getUserid());
                }

                methodinBinary(arrayList);
                //BinaryForUser binary = new BinaryForUser(Binary.this);
                //binary.methodForBinary(userid,list.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
