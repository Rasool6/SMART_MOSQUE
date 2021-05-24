package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.renderscript.Script;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminSignUpActivity extends AppCompatActivity {

    EditText mosqueName,imamName,email,password,conPassword,edtLocation;
    Button submit,getLocationBtn;
   FirebaseAuth mAuth;
   ProgressBar progressBar;
   Spinner sectSpinner;
   String str_sect;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);
        mAuth = FirebaseAuth.getInstance();

        mosqueName=findViewById(R.id.txtMosqueName);
        sectSpinner=findViewById(R.id.sectSpinner);
         imamName=findViewById(R.id.txtimamName);
         email=findViewById(R.id.txtemail);
         password=findViewById(R.id.txtpassword1);
         conPassword=findViewById(R.id.txtpassword2);
         submit=findViewById(R.id.btnAdmin);
         progressBar=findViewById(R.id.progressId);
         edtLocation=findViewById(R.id.txtgetLocation);
         getLocationBtn=findViewById(R.id.getLocation);


        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GpsTracker gpsTracker = new GpsTracker(AdminSignUpActivity.this);
                if (gpsTracker.canGetLocation()) {
                     latitude = gpsTracker.getLatitude();
                    longitude = gpsTracker.getLongitude();
                    try {
                        String address=getAddress(latitude,longitude);
                        edtLocation.setText(address);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                    final String str_mosquName=mosqueName.getText().toString().trim();
                    final String str_imamName=imamName.getText().toString().trim();
                    final String str_email=email.getText().toString().trim();
                    final String str_password=password.getText().toString().trim();
                    String str_confirmpasswor=conPassword.getText().toString().trim();
                 String  str_location=edtLocation.getText().toString().trim();
                 String str_sect=sectSpinner.getSelectedItem().toString().trim();


                 if (TextUtils.isEmpty(str_mosquName)){
                        mosqueName.setError("Empty Field..!");
                        return;
                    }
                 if (TextUtils.isEmpty(str_imamName)){
                     imamName.setError("Empty Field..!");
                     return;
                 }
                 if (TextUtils.isEmpty(str_email)){
                     email.setError("Empty Field..!");
                     return;
                 }
                 if (sectSpinner.getSelectedItem().equals("Select Your Sect")){
                     Toast.makeText(AdminSignUpActivity.this, "Select Your Sect please..!", Toast.LENGTH_SHORT).show();
                     return;

                 }
                 if (TextUtils.isEmpty(str_password) ){
                     password.setError("Empty Field..!");
                     return;
                 }
                 if (str_password.length()<=8){
                     password.setError("password must be greator than 8");
                     return;
                 }

                 if (!str_confirmpasswor.equals(str_password)){
                     conPassword.setError("password Not Macthed");
                     return;
                 }if (TextUtils.isEmpty(str_location)){

                     edtLocation.setError("Get Your Mosque  Current Location");
                     return;
                 }

                 else {
                     progressBar.setVisibility(View.VISIBLE);

                     saveData(str_mosquName,str_imamName,str_email,str_sect,str_password,str_location);

                 }
             }
         });



    }

    private void saveData(final String str_mosquName, final String str_imamName, final String str_email, final String str_sect, final String str_password,final String str_location) {



        mAuth.createUserWithEmailAndPassword(str_email,str_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String TAG="";
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // email sent
                                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                                senddata(currentUser.getUid(),
                                                        str_mosquName,
                                                        str_imamName,
                                                        str_email,
                                                        str_sect,
                                                        str_password,
                                                        str_location

                                                );
                                                // after email is sent just logout the user and finish this activity
//                                                                       FirebaseAuth.getInstance().signOut();

                                            } else {
                                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                                //restart this activity
                                                overridePendingTransition(0, 0);
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());

                                            }

                                        }
                                    });


//                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.setVisibility(View.GONE);
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(AdminSignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void senddata(String uid, String str_mosquName, String str_imamName, String str_email, String str_sect, String str_password,String str_location) {
        AdminModel adminModel = new AdminModel(
                str_mosquName,
                str_imamName,
                str_email,
                str_sect,
                str_password,
                str_location,
                String.valueOf(latitude),
                String.valueOf(longitude),
                "Pre Test value of mosque_image",
                "test"

        );
        FirebaseDatabase.getInstance().getReference("MosqueAdmin").child("Users").child(uid).setValue(adminModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG", "Data Not saved");
                   progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(AdminSignUpActivity.this, "Profile  created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminSignUpActivity.this, AdminLoginActivity.class).putExtra("status","user"));
                    finish();
                    Log.e("ss", "Data saved successfully");
//                    progressDialog.dismiss();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }



    String getAddress(double latitude, double longitude) throws IOException {
        if (Geocoder.isPresent()) {
            Geocoder myLocation = new Geocoder(AdminSignUpActivity.this, Locale.getDefault());
            List<Address> myList = myLocation.getFromLocation(latitude, longitude, 1);
            Address address = (Address) myList.get(0);
            String addressStr = "";
            addressStr += address.getAddressLine(0) + ", ";
            addressStr += address.getAddressLine(1) + ", ";
            addressStr += address.getAddressLine(2);
            return address.getAddressLine(0);
        } else {
            return "geoCoder not present";
        }
    }


    }

