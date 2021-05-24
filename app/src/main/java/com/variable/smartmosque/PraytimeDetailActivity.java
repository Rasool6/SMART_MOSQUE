package com.variable.smartmosque;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PraytimeDetailActivity extends AppCompatActivity {

    TextView prayerName,prayerTime,detail,currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praytime_detail);
        prayerName=findViewById(R.id.prayNameShowdetail);
        prayerTime=findViewById(R.id.prayerTime);
        detail=findViewById(R.id.detailPayeryerTimeHadees);
        currentDate=findViewById(R.id.currentDatedetail);


        Bundle bundle=getIntent().getExtras();
        PraytimeModel praytimeModel= (PraytimeModel) bundle.getSerializable("MYDATA");

        prayerName.setText(praytimeModel.prayerName);
        prayerTime.setText(praytimeModel.prayerTime);
        detail.setText(praytimeModel.aboutPrayer);
        currentDate.setText(praytimeModel.currentDate);


    }
}