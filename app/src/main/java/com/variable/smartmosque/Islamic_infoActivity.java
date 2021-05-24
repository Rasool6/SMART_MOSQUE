package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Islamic_infoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    IslamicInfo_Adapter info_adapter;
    List<IslamicInfo_Model> info_list;
    LinearLayoutManager manager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamic_info);
        recyclerView=findViewById(R.id.recyclerViewinfo);
        progressBar=findViewById(R.id.progressbarr);
        manager=new LinearLayoutManager(Islamic_infoActivity.this);
        info_list=new ArrayList<>();
        recyclerView.setLayoutManager(manager);

        fetchInfoData();

        FloatingActionButton floatingActionButton=findViewById(R.id.floatingActionBtn_info);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Islamic_infoActivity.this,AddIslamic_Info_Activity.class));
            }
        });


    }

    private void fetchInfoData() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference deRef= FirebaseDatabase.getInstance().getReference();
        deRef.child("MosqueAdmin").child("Islamic_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                info_list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if (dataSnapshot1.child("mosq_key").getValue(String.class).equals(Appdata.uid)
                            //&&
                  //  dataSnapshot1.child("infoSpinner").getValue(String.class).equals(str_spinnerdata)
                    ) {
                        progressBar.setVisibility(View.GONE);
                        Appdata.INFO_KEY = dataSnapshot1.getKey();
                        String title = dataSnapshot1.child("title").getValue().toString();
                        String infoImage = dataSnapshot1.child("infoImage").getValue().toString();
                        String detail = dataSnapshot1.child("detail").getValue().toString();
                        String currentDate = dataSnapshot1.child("current_date").getValue().toString();
                        String infoSpinner = dataSnapshot1.child("infoSpinner").getValue().toString();

                        IslamicInfo_Model islamicInfo_model = new IslamicInfo_Model(Appdata.INFO_KEY,Appdata.uid,title,detail,infoSpinner, infoImage,currentDate);
                        info_list.add(islamicInfo_model);
                    }
                }

                info_adapter=new IslamicInfo_Adapter(Islamic_infoActivity.this,info_list);
                recyclerView.setAdapter(info_adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Islamic_infoActivity.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });



    }
}