package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserIslamicPosts extends AppCompatActivity {

    RecyclerView recyclerView;
    List<IslamicInfo_Model> list;
    IslamicInfoUser_adapter islamicInfoUser_adapter;
    String mosqueKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_islamic_posts);

        recyclerView=findViewById(R.id.recy);


        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mosqueKey=getIntent().getStringExtra("mosquKey1");
      //  Toast.makeText(this, mosqueKey, Toast.LENGTH_SHORT).show();
     //   Log.d("key",mosqueKey);
        fetchdata();

    }

    private void fetchdata() {


        DatabaseReference deRef= FirebaseDatabase.getInstance().getReference();
        deRef.child("MosqueAdmin").child("Islamic_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {

                    if (dataSnapshot1.child("mosq_key").getValue().equals(mosqueKey)) {


                    String title = dataSnapshot1.child("title").getValue().toString();
                    String infoImage = dataSnapshot1.child("infoImage").getValue().toString();
                    String detail = dataSnapshot1.child("detail").getValue().toString();
                    String currentDate = dataSnapshot1.child("current_date").getValue().toString();
                    String infoSpinner = dataSnapshot1.child("infoSpinner").getValue().toString();

                    IslamicInfo_Model islamicInfo_model = new IslamicInfo_Model(mosqueKey,title, detail,infoSpinner, infoImage, currentDate);
                    list.add(islamicInfo_model);
                }

                }
                if (list.size()==0){
                    Toast.makeText(UserIslamicPosts.this, "No data found", Toast.LENGTH_SHORT).show();
                    return;

                }else {
                    islamicInfoUser_adapter = new IslamicInfoUser_adapter(UserIslamicPosts.this, list);
                    recyclerView.setAdapter(islamicInfoUser_adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserIslamicPosts.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}