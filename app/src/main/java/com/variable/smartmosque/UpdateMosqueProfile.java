package com.variable.smartmosque;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

public class UpdateMosqueProfile extends AppCompatActivity {

    EditText mosque_name, imam_name, mosque_city;
    Button updateMosqueProfilebtn;

    ImageView imageView;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    Uri filePath;
    String id;
    String email;
    Spinner sectSpinner;
    String mosqueKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mosque_profile);
        progressDialog = new ProgressDialog(this);
        imageView = (ImageView) findViewById(R.id.imgProMosq);
        mosque_name = findViewById(R.id.mosquenameUpdateId);
        imam_name = findViewById(R.id.imamnameUpdateid);
        sectSpinner = findViewById(R.id.locationUpdateId);
        mosque_city = findViewById(R.id.mosquecityUpdateid);
        progressBar = findViewById(R.id.mosquUpdateProgressId);
        updateMosqueProfilebtn = findViewById(R.id.updateMosqueProbtn);

        Bundle bundle=getIntent().getExtras();
        AdminModel adminModel= (AdminModel) bundle.getSerializable("Data");
        id = adminModel.admin_key;
        mosque_name.setText(adminModel.mosqueName);
        imam_name.setText(adminModel.imamName);
        mosque_city.setText(adminModel.mosque_city);

        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dexter.withActivity(UpdateMosqueProfile.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

            }
        });


        updateMosqueProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String str_mosquName = mosque_name.getText().toString().trim();
                final String str_imamName = imam_name.getText().toString().trim();
                String str_city = mosque_city.getText().toString().trim();
                String str_sect = sectSpinner.getSelectedItem().toString().trim();


                if (TextUtils.isEmpty(str_mosquName)) {
                    mosque_name.setError("Empty Field..!");
                    return;
                }
                if (TextUtils.isEmpty(str_imamName)) {
                    imam_name.setError("Empty Field..!");
                    return;
                }
                if (TextUtils.isEmpty(str_city)) {
                    mosque_city.setError("Empty Field..!");
                    return;
                }
                if (sectSpinner.getSelectedItem().equals("Select Your Sect")) {
                    Toast.makeText(UpdateMosqueProfile.this, "Select Your Sect please..!", Toast.LENGTH_SHORT).show();
                    return;

                } else {


                    updateMosqueProfile(str_mosquName,str_imamName,str_city,str_sect);

                }
            }
        });

    }

    private void updateMosqueProfile(final String str_mosquName, final String str_imamName, final String str_city, final String str_sect) {

        progressDialog.setTitle("File Uploading");
        progressDialog.show();
        try {
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

            final StorageReference storageReference = firebaseStorage.getReference("Image1" + new Random().nextInt(50));
            storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            DatabaseReference dbRef = firebaseDatabase.getReference();
                            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            //       String password  = FirebaseAuth.getInstance();
                            String str_uri=uri.toString();

                            addMore(str_mosquName,
                                    str_imamName,
                                    str_city,
                                    str_sect,
                                    str_uri
                            );





                        }
                    });


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded\t" + (int) percent + "%");
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "New image is not selected...!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        }

    }

    private void addMore(String str_mosquName, String str_imamName, String str_city, String str_sect, String str_uri) {




        DatabaseReference ref =FirebaseDatabase.getInstance().getReference("MosqueAdmin").child("Users").child(id);
        ref.child("mosqueName").setValue(str_mosquName);
        ref.child("imamName").setValue(str_imamName);
        ref.child("sect").setValue(str_sect);
        ref.child("mosque_city").setValue(str_city);
        ref.child("mosquImage").setValue(str_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                startActivity(new Intent(UpdateMosqueProfile.this, AdminPanalDrawarActivity.class));
                Toast.makeText(UpdateMosqueProfile.this, "Profile data updated successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "Data Not saved");
                Toast.makeText(UpdateMosqueProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}