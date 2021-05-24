package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    Button btnLogin,btnSignup;
    EditText email,password;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        btnSignup = findViewById(R.id.Idadmin);
        btnLogin = findViewById(R.id.idLogin);
        email = findViewById(R.id.txtemaila);
        password = findViewById(R.id.txtpasswordA);
        progressBar = findViewById(R.id.progressAlogin);

//         FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//         if(user!=null){
//             startActivity(new Intent(AdminLoginActivity.this,AdminPanalDrawarActivity.class));
//             finish();
//         }

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this,AdminSignUpActivity.class));
            }
        });




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str_email=email.getText().toString().trim();
                final String str_password=password.getText().toString().trim();

                if (TextUtils.isEmpty(str_email)){
                    email.setError("Empty Field..!");
                    return;
                }
                if (TextUtils.isEmpty(str_password) ){
                    password.setError("Empty Field..!");
                    return;
                }
                else {

          progressBar.setVisibility(View.VISIBLE);

                    loginUser(str_email,str_password);
//
//                    Query query= FirebaseDatabase.getInstance().getReference("MosqueAdmin").child("Users").orderByChild("email").equalTo(str_email);
//                    query.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            progressBar.setVisibility(View.GONE);
//
//                            for (DataSnapshot childsnapshot:snapshot.getChildren())
//
//                            {
//                                if (childsnapshot.child("password").getValue(String.class).equals(str_password)) {
//
//                                    Appdata.uid=childsnapshot.getKey();
//                                    Appdata.mosqueName = childsnapshot.child("mosque_name").getValue(String.class);
//                                    Appdata.mosqueImam = childsnapshot.child("mosque_imam").getValue(String.class);
//
//                                    Appdata.ADMIN_EMAIL_KEY = childsnapshot.child("email").getValue(String.class);
//                                    Appdata.mosquelocation = childsnapshot.child("mosque_location").getValue(String.class);
//                                    Appdata.mosquecity = childsnapshot.child("mosque_city").getValue(String.class);
//                                    Appdata.mosqueImage = childsnapshot.child("mosque_image").getValue(String.class);
//
//                                   Appdata.ADMIN_PASWORD_KEY  =  childsnapshot.child("password").getValue(String.class);
//
//                                   startActivity(new Intent(AdminLoginActivity.this,AdminPanalDrawarActivity.class));
//
//                                    Toast.makeText(AdminLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
//
//
//
//                                }
//
//
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(AdminLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//



                }
            }
        });








    }

    private void loginUser(String str_email, String str_password) {

        mAuth.signInWithEmailAndPassword(str_email, str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()) {
                                fetchUserData(user.getUid());

                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AdminLoginActivity.this, "Please Verified your email first", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(AdminLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });


    }

    private void fetchUserData(final String uid) {


        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference("MosqueAdmin").child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot childsnapshot) {

                                    Appdata.uid=childsnapshot.getKey();
                                    Appdata.mosqueName = childsnapshot.child("mosque_name").getValue(String.class);
                                    Appdata.mosqueImam = childsnapshot.child("mosque_imam").getValue(String.class);
                                    Appdata.ADMIN_EMAIL_KEY = childsnapshot.child("email").getValue(String.class);
                                    Appdata.mosquelocation = childsnapshot.child("mosque_location").getValue(String.class);
                                    Appdata.mosquecity = childsnapshot.child("mosque_city").getValue(String.class);
                                    Appdata.mosqueImage = childsnapshot.child("mosque_image").getValue(String.class);
                                    Appdata.ADMIN_PASWORD_KEY  =  childsnapshot.child("password").getValue(String.class);

                                   startActivity(new Intent(AdminLoginActivity.this,AdminPanalDrawarActivity.class));
                                    Toast.makeText(AdminLoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });



    }
}
