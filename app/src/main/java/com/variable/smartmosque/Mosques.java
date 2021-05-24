package com.variable.smartmosque;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Mosques extends Fragment {
 // Spinner spinner;
    RecyclerView recyclerView;
    List<MosqueProfileModel> cityList = new ArrayList<>();
      CityListAdaptor cityListAdaptor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mosques, container, false);
      //  spinner=view.findViewById(R.id.spinnerForcity);
         recyclerView=view.findViewById(R.id.cityRecycler);
        cityList=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        fetchcityData();
       // fetchSpinnerData();
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                if (position == 0){
//
//
//                }else
//                {
//
//                    String str_ctySpinner =   cityList.get(position);
//
//                    Intent intent = new Intent(getActivity(), MosqueListActivity.class).putExtra("city",str_ctySpinner);
//                    startActivity(intent);
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // To do ...
//            }
//
//        });




        return view;
    }

    private void fetchcityData() {

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("MosqueAdmin").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {



                     String usersKey=dataSnapshot.getKey();
               //      Log.d("key",usersKey);
                    String str_catgery = dataSnapshot.child("mosque_city").getValue(String.class);

                    MosqueProfileModel mosqueProfileModel=new MosqueProfileModel(usersKey,str_catgery);
                      cityList.add(mosqueProfileModel);
                }

                  cityListAdaptor=new CityListAdaptor(getActivity(),cityList);
                  recyclerView.setAdapter(cityListAdaptor);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
            }
        });

    }

//    private void fetchSpinnerData() {
//
//        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
//        dbRef.child("MosqueAdmin").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                cityList.clear();
//                cityList.add("Select Category");
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//
//
//
//                     String usersKey=dataSnapshot.getKey();
//                    String str_catgery = dataSnapshot.child("mosque_city").getValue(String.class);
//                 //  String str_catgery = dataSnapshot.child("mosque_city").getValue().toString();
//
//                   cityList.add(str_catgery);
//             //                    Log.d("city",str_catgery);
//                }
//
//
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item1, cityList);
//                adapter.setDropDownViewResource(R.layout.spinner_item1);
//                spinner.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    return;
//            }
//        });
//
//    }
}