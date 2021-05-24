package com.variable.smartmosque;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class UserType extends AppCompatActivity {

    Button admin,user;
    Spinner uesrType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertype);

//          admin=findViewById(R.id.btnAdmin);
//          user=findViewById(R.id.btnUser);
          uesrType=findViewById(R.id.userType);

        uesrType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    if (position==1){
                        Appdata.uesrType="Mosque Admin";
                        startActivity(new Intent(UserType.this, AdminLoginActivity.class));
                        finish();

                    }else if(position==2){
                        Appdata.uesrType="User";
                        startActivity(new Intent(UserType.this, UserLandingActivity.class));
                        finish();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }
}