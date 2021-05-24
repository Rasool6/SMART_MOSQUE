package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Islamic_UniqueInfoActivity extends AppCompatActivity {
        RecyclerView recyclerView;
    IslamicInfoUser_adapter info_adapter;
    List<IslamicInfo_Model> info_list;
    LinearLayoutManager manager;
    ProgressBar progressBar;

    String activity_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamic__unique_info);
        recyclerView=findViewById(R.id.recyclerInfo);
        progressBar=findViewById(R.id.progressbarr);

        manager=new LinearLayoutManager(this);
        info_list=new ArrayList<>();
        recyclerView.setLayoutManager(manager);

         activity_status = getIntent().getStringExtra("activity_status");
        fetchInfoData();
    }

    private void fetchInfoData() {
        progressBar.setVisibility(View.VISIBLE);

        DatabaseReference deRef= FirebaseDatabase.getInstance().getReference();
        deRef.child("MosqueAdmin").child("Islamic_info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                info_list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);
                    if (dataSnapshot1.child("infoSpinner").getValue().toString().equals(activity_status)) {

                        Appdata.INFO_KEY = dataSnapshot1.getKey();
                        String title = dataSnapshot1.child("title").getValue().toString();
                        String infoImage = dataSnapshot1.child("infoImage").getValue().toString();
                        String detail = dataSnapshot1.child("detail").getValue().toString();
                        String currentDate = dataSnapshot1.child("current_date").getValue().toString();
                        String infoSpinner = dataSnapshot1.child("infoSpinner").getValue().toString();
                        IslamicInfo_Model islamicInfo_model = new IslamicInfo_Model(Appdata.INFO_KEY, title, detail, infoSpinner, infoImage, currentDate);
                        info_list.add(islamicInfo_model);

                    }
                }

                info_adapter=new IslamicInfoUser_adapter(Islamic_UniqueInfoActivity.this,info_list);
                recyclerView.setAdapter(info_adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Islamic_UniqueInfoActivity.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });



    }
}