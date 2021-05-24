package com.variable.smartmosque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MosqueSpecificDetailActicity extends AppCompatActivity {


    TextView prayerTimeBtn,postsBtn;
    String mosqukey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_specific_detail_acticity);

        prayerTimeBtn=findViewById(R.id.textView2);
        postsBtn=findViewById(R.id.textView3);


        //Toast.makeText(this, mosqukey, Toast.LENGTH_SHORT).show();
        //Log.d("key",mosqukey);

        prayerTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MosqueSpecificDetailActicity.this,UserShowPrayerTime.class).putExtra("mosquKey1",getIntent().getStringExtra("mosquKey")));
                finish();
            }
        });
        postsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MosqueSpecificDetailActicity.this,UserIslamicPosts.class).putExtra("mosquKey1",getIntent().getStringExtra("mosquKey")));

            }
        });


    }
}