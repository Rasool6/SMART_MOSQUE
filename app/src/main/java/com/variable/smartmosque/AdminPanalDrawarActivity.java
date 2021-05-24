package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminPanalDrawarActivity extends AppCompatActivity {

      DrawerLayout drawerLayout;
      NavigationView navigationView;
      Toolbar toolbar;
      ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panal_drawar);

        drawerLayout=findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigation);
        toolbar=findViewById(R.id.toolbar);

          setSupportActionBar(toolbar);
          toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
          drawerLayout.addDrawerListener(toggle);
          toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new MosqueProfile_frg()).commit();
        navigationView.setCheckedItem(R.id.item_profile);
        navigationView.setItemIconTintList(null);

          navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
              Fragment temp;
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                       drawerLayout.closeDrawer(GravityCompat.START);

                  switch (menuItem.getItemId()){

                      case R.id.item_profile:
                                         temp=new MosqueProfile_frg();
                                 break;
                      case R.id.item_prayer:
                          temp=new PrayerTime_frg();
                          break;
                      case R.id.item_IslamicInfo:
                          temp=new IslamicInfo_frg();
                          break;
                  }
                  getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                  return false;

              }
          });


        FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    if (snapshot1.getKey().equals(Appdata.uid)){
                        View view = navigationView.getHeaderView(0);
                        TextView textView=view.findViewById(R.id.drawrProfilename);
                        textView.setText(snapshot1.child("email").getValue(String.class));
                        CircleImageView imageView=view.findViewById(R.id.profileimage);
                        imageView.setImageResource(R.drawable.avatar04);
                        Glide.with(AdminPanalDrawarActivity.this).load(snapshot1.child("mosquImage").getValue(String.class)).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminPanalDrawarActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    @Override
    public void onBackPressed() {
       if (drawerLayout.isDrawerOpen(GravityCompat.START)){
           drawerLayout.closeDrawer(GravityCompat.START);

       }else {
           super.onBackPressed();
       }
    }
}