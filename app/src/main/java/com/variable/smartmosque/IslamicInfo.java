package com.variable.smartmosque;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IslamicInfo extends Fragment {




    CardView cardView1,cardView2,cardView3,cardView4,cardView5,cardView6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_islamic_info, container, false);
       cardView1=view.findViewById(R.id.cardView1);
       cardView2=view.findViewById(R.id.cardView2);
       cardView3=view.findViewById(R.id.cardView3);
       cardView4=view.findViewById(R.id.cardView4);
       cardView5=view.findViewById(R.id.cardView5);
       cardView6=view.findViewById(R.id.cardView6);

     listners();

       return view;
    }

    private void listners() {

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Islamic_UniqueInfoActivity.class).putExtra("activity_status","Dua"));
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Islamic_UniqueInfoActivity.class).putExtra("activity_status","Quran Verses"));
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Islamic_UniqueInfoActivity.class).putExtra("activity_status","Quran Surah"));
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Islamic_UniqueInfoActivity.class).putExtra("activity_status","Worship"));
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Islamic_UniqueInfoActivity.class).putExtra("activity_status","Hadith"));
            }
        });
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Islamic_UniqueInfoActivity.class).putExtra("activity_status","Allah Names"));
            }
        });

    }

}