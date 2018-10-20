package com.example.sharansingh.solarifyenergy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<LeaderBoardDataType> mArrayList;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView=(RecyclerView)findViewById(R.id.leaderboardRecyclerView);
        mArrayList=new ArrayList<LeaderBoardDataType>();
        final LeaderboardRecyclerAdapter leaderboardRecyclerAdapter=new LeaderboardRecyclerAdapter(this,mArrayList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(leaderboardRecyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseApp.initializeApp(this);




  /*      listView=(ListView)findViewById(R.id.leaderboardListView);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
*/
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                Log.i("datasnapshotValues",""+dataSnapshot.getKey());

          DatabaseReference db= FirebaseDatabase.getInstance().getReferenceFromUrl("https://solarifyenergy.firebaseio.com/users").child(dataSnapshot.getKey());
          db.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
           //       arrayList.add(dataSnapshot2.child("total").getValue().toString());
           //       arrayAdapter.notifyDataSetChanged();
                  mArrayList.add(new LeaderBoardDataType(dataSnapshot2.child("name").getValue().toString(),dataSnapshot2.child("total").getValue().toString()));
                  Collections.sort(mArrayList);
                  leaderboardRecyclerAdapter.notifyDataSetChanged();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                leaderboardRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
