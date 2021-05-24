package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserShowPrayerTime extends AppCompatActivity {

     RecyclerView recyclerView;
     List<PraytimeModel> list;
     UserShowPrayerTimeAdapter userShowPrayerTimeAdapter;
     String mosqueKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show_prayer_time);

        recyclerView=findViewById(R.id.userSHowRecPTime);


        list=new ArrayList<>();
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mosqueKey=getIntent().getStringExtra("mosquKey1");
      //  Toast.makeText(this, mosqueKey, Toast.LENGTH_SHORT).show();
      //  Log.d("key",mosqueKey);

        fetchdata();

    }

    private void fetchdata() {


        DatabaseReference deRef= FirebaseDatabase.getInstance().getReference();
        deRef.child("MosqueAdmin").child("PrayerTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                   if (dataSnapshot1.child("mosq_key").getValue(String.class).equals(mosqueKey)) {


                        String prayerName = dataSnapshot1.child("prayerName").getValue().toString();
                        String prayerTime = dataSnapshot1.child("prayerTime").getValue().toString();
                        String aboutPrayer = dataSnapshot1.child("aboutPrayer").getValue().toString();
                        String currentDate = dataSnapshot1.child("currentDate").getValue().toString();

                        PraytimeModel praytimeModel = new PraytimeModel(prayerName, prayerTime, aboutPrayer, currentDate);
                        list.add(praytimeModel);
                   }

                }

                 userShowPrayerTimeAdapter=new UserShowPrayerTimeAdapter(UserShowPrayerTime.this,list);
                recyclerView.setAdapter(userShowPrayerTimeAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserShowPrayerTime.this, "No data found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}