package com.variable.smartmosque;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MosqueProfile_frg extends Fragment {

    TextView mosaue_name, mosque_imam, mosque_location, mosque_city, updateMosquePro;
    ImageView imageView;

    MosqueProfileAdapter mosqueProfileAdapter;
    List<AdminModel> list;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmented

        View view = inflater.inflate(R.layout.fragment_mosque_profile_frg, container, false);

        mosaue_name = view.findViewById(R.id.mosquenameId);
        mosque_imam = view.findViewById(R.id.imamnameid);
        mosque_location = view.findViewById(R.id.locationId);
        mosque_city = view.findViewById(R.id.mosquecityid);
        imageView = view.findViewById(R.id.imgProMosq);
        recyclerView = view.findViewById(R.id.mosqRecy);
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        updateMosquePro = view.findViewById(R.id.updateMosquePro);


        fetchMosque();


        return view;
    }

    private void fetchMosque() {

        FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(Appdata.uid)){
                         String str_key=dataSnapshot.getKey();
                        String str_mosqueName=dataSnapshot.child("mosqueName").getValue(String.class);
                        String str_mosqueImam=dataSnapshot.child("imamName").getValue(String.class);
                        String str_mosqueEmail=dataSnapshot.child("email").getValue(String.class);
                        String str_mosqueSect=dataSnapshot.child("sect").getValue(String.class);
                        String str_mosquePassword=dataSnapshot.child("password").getValue(String.class);
                        String str_mosqueLatitude=dataSnapshot.child("latitude").getValue(String.class);
                        String str_mosqueLongitude=dataSnapshot.child("longitude").getValue(String.class);
                        String str_mosqueLocation=dataSnapshot.child("mosqueLocation").getValue(String.class);
                        String str_mosque_image=dataSnapshot.child("mosquImage").getValue(String.class);
                        String str_mosque_city=dataSnapshot.child("mosque_city").getValue(String.class);

                        AdminModel adminModel=new AdminModel(str_key,str_mosqueName,str_mosqueImam,str_mosqueEmail,str_mosqueSect,str_mosquePassword,str_mosqueLocation,str_mosqueLatitude
                        ,str_mosqueLongitude,
                        str_mosque_image,
                                str_mosque_city
                        );

                        list.add(adminModel);

                    }

                }

                   mosqueProfileAdapter=new MosqueProfileAdapter(getActivity(),list);
                   recyclerView.setAdapter(mosqueProfileAdapter);
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

