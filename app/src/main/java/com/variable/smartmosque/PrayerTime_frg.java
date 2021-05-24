package com.variable.smartmosque;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PrayerTime_frg extends Fragment {

    RecyclerView recyclerView;
    ArrayList<PraytimeModel> pratim_list;
    Prayertime_Adaptor prayertime_adaptor;
   LinearLayoutManager manager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                View view=inflater.inflate(R.layout.fragment_prayer_time_frg, container, false);

                FloatingActionButton fab = view.findViewById(R.id.floatingActionBtn);
                   fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getActivity(),AddPrayerTimeActivity.class);
                        startActivity(intent);

                    }
                });

        recyclerView=view.findViewById(R.id.recyclerprayertime);
        manager=new LinearLayoutManager(getActivity());
        pratim_list=new ArrayList<>();
        recyclerView.setLayoutManager(manager);

        fetchdata();


        return view;
    }

    private void fetchdata() {


        DatabaseReference deRef= FirebaseDatabase.getInstance().getReference();
        deRef.child("MosqueAdmin").child("PrayerTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pratim_list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    if (dataSnapshot1.child("mosq_key").getValue(String.class).equals(Appdata.uid)) {


                        String prayerName = dataSnapshot1.child("prayerName").getValue().toString();
                        String prayerTime = dataSnapshot1.child("prayerTime").getValue().toString();
                        String aboutPrayer = dataSnapshot1.child("aboutPrayer").getValue().toString();
                        String currentDate = dataSnapshot1.child("currentDate").getValue().toString();

                        PraytimeModel praytimeModel = new PraytimeModel(dataSnapshot1.getKey(),Appdata.uid,prayerName, prayerTime, aboutPrayer, currentDate);
                        pratim_list.add(praytimeModel);
                    }
                }

                prayertime_adaptor=new Prayertime_Adaptor(getActivity(),pratim_list);
                recyclerView.setAdapter(prayertime_adaptor);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
            }
        });


    }
}