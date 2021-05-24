package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MosqueListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String data;
    List<MosqueProfileModel> mosque_list;
    MosqueListAdapter mosqueListAdapter;
    String mosqKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_list);
      recyclerView=findViewById(R.id.mosqulistRec);

        mosque_list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

          Bundle bundle=getIntent().getExtras();
          MosqueProfileModel mosqueProfileModel= (MosqueProfileModel) bundle.getSerializable("data");
          mosqKey=mosqueProfileModel.mosqueProfile_key;
          data=mosqueProfileModel.mosque_city;





           fetch_List_Of_Mosque();
    }

    private void fetch_List_Of_Mosque() {

        FirebaseDatabase.getInstance().getReference("MosqueAdmin").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mosque_list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                     if (dataSnapshot.child("mosque_city").getValue(String.class).equals(data))
                     {


                         String str_mosqueKey=dataSnapshot.getKey();
                         String str_mosqueName=dataSnapshot.child("mosque_name").getValue(String.class);
                         String str_mosque_imam=dataSnapshot.child("mosque_imam").getValue(String.class);
                         String str_mosque_city=dataSnapshot.child("mosque_city").getValue(String.class);
                         String str_mosque_location=dataSnapshot.child("mosque_location").getValue(String.class);
                         String str_mosque_image=dataSnapshot.child("mosque_image").getValue(String.class);

                         MosqueProfileModel mosqueProfileModel=new MosqueProfileModel(str_mosqueKey,str_mosqueName,str_mosque_imam,str_mosque_city,str_mosque_location,str_mosque_image);
                         mosque_list.add(mosqueProfileModel);

                     }
                }
                mosqueListAdapter=new MosqueListAdapter(MosqueListActivity.this,mosque_list);
                recyclerView.setAdapter(mosqueListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}