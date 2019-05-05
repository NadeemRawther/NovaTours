package com.nova.bitsi34gb.novatours;

import android.app.Activity;
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

import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Stack;

import de.blox.graphview.BaseGraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.ViewHolder;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class Binary extends Activity {
    private int nodeCount = 1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences sharedPref;
    GraphView graphView;
    String left, right,rank,name;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<NameAndEarnings> arrayList = new ArrayList<>();
   Stack<String> stack = new Stack<>();
    private static final String TAG1 = "NadeemActivity2";
    String useridog;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary);
        sharedPref = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        graphView = findViewById(R.id.graph);
        useridog = sharedPref.getString("userid","d");
        progressDialog = new ProgressDialog(Binary.this);
        progressDialog.setMessage("please Wait while loading");
        progressDialog.show();
          makeTree(useridog);
    }

  public void methodinBinary(final ArrayList<NameAndEarnings> list2){
       Graph graph = new Graph();
      graphView = findViewById(R.id.graph);
      for (NameAndEarnings nameAndEarnings : list2) {
          String userid = nameAndEarnings.getUserid();
          String left = nameAndEarnings.getLeft();
          String right = nameAndEarnings.getRight();
          //String rank = nameAndEarnings.getRank();
          if (!left.equals("0")) {
              graph.addEdge(new Node(userid), new Node(left));
          }
          if (!right.equals("0")) {
              graph.addEdge(new Node(userid), new Node(right));
          }
      }

      final BaseGraphAdapter<ViewHolder> adapter = new BaseGraphAdapter<ViewHolder>(graph) {



          @Override
          public ViewHolder onCreateViewHolder(ViewGroup parent,int ViewType) {
              final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userchain, parent, false);
              return new SimpleViewHolder(view);
          }

          @Override
          public void onBindViewHolder(ViewHolder viewHolder, Object data, int position) {
              ((SimpleViewHolder)viewHolder).mTextView.setText(data.toString());
             // viewHolder.mTextView1.setText(list2.get(position).getName());
              ((SimpleViewHolder)viewHolder).cardView.setCardBackgroundColor(Color.TRANSPARENT);
              if(list2.get(position).getRank().equals("gold")){
                  ((SimpleViewHolder)viewHolder).imageView.setImageResource(R.mipmap.gold);

              }
              else if (list2.get(position).getRank().equals("silver")){
                  ((SimpleViewHolder)viewHolder).imageView.setImageResource(R.mipmap.silver);
              }
              else if (list2.get(position).getRank().equals("platinum")){
                  ((SimpleViewHolder)viewHolder).imageView.setImageResource(R.mipmap.platinum);
              }

          }
      };
      graphView.setAdapter(adapter);
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
        TextView mTextView,mTextView1;
        CardView cardView;
        ImageView imageView;


       public SimpleViewHolder(View view) {
           super(view);
            cardView = view.findViewById(R.id.card_view);
          imageView = view.findViewById(R.id.imgforuser);
            mTextView = view.findViewById(R.id.textView);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Binary.this, Users.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        list.clear();
        finish();
        startActivity(intent);

    }

    public void makeTree(final String userid){

        final DatabaseReference myRef = database.getReference("users");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                String conc = dataSnapshot.child(userid).getKey().toString();

                while (!conc.equals("0")|| !stack.empty()) {

                    while (!conc.equals("0")) {
                        stack.push(conc);
                        list.add(conc);
                        left = dataSnapshot.child(conc).child("left").getValue().toString();
                        right = dataSnapshot.child(conc).child("right").getValue().toString();
                        rank = dataSnapshot.child(conc).child("rank").getValue().toString();
                        name = dataSnapshot.child(conc).child("name").getValue().toString();
                        NameAndEarnings nameAndEarnings = new NameAndEarnings(name,conc,left,right,rank);
                        arrayList.add(nameAndEarnings);
                        conc = dataSnapshot.child(conc).getKey().toString();

                        conc = left;
                    }
                    if(!stack.peek().equals("0")){

                    }
                    conc = stack.pop();

                    conc = dataSnapshot.child(conc).child("right").getValue().toString();


                }
          for(int j = 0; j < list.size();j++){
              Log.e(TAG1, arrayList.get(j).getUserid().toString());
                }

                methodinBinary(arrayList);
                //BinaryForUser binary = new BinaryForUser(Binary.this);
                //binary.methodForBinary(userid,list.size());

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });

    }
  }