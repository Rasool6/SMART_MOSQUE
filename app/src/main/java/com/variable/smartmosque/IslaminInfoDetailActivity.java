package com.variable.smartmosque;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class IslaminInfoDetailActivity extends AppCompatActivity {

    TextView title,info,currentdate;
    ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_islamin_info_detail);

        title=findViewById(R.id.detailTitle);
        info=findViewById(R.id.detailinfo);
        currentdate=findViewById(R.id.currentDatedetail);

        imageView=findViewById(R.id.detailImage);


        Bundle bundle=getIntent().getExtras();
        IslamicInfo_Model islamicInfo_model= (IslamicInfo_Model) bundle.getSerializable("MYDATA");

        title.setText(islamicInfo_model.title);
        Glide.with(IslaminInfoDetailActivity.this).load(islamicInfo_model.infoImage).into(imageView);
        info.setText(islamicInfo_model.detail);
        currentdate.setText(islamicInfo_model.current_date);
    }
}