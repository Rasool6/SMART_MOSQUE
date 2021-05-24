package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class
UpdateIskamicInfoActivity extends AppCompatActivity {

    ImageView imageView;
    EditText updatetitle,updatedetail;
    Button updateBtn;
    ProgressDialog progressDialog;
     Uri filePath;
     Bitmap bitmap;
     Calendar calendar;
     String currentDate;
    IslamicInfo_Model islamicInfo_model;
     String infoKey;
    Spinner infoSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_iskamic_info);
        imageView=findViewById(R.id.infoImageupdateID);
        updatetitle=findViewById(R.id.edtInfoupdateTitlID);
        updatedetail=findViewById(R.id.edtInfoupdateDetaillID);
        updateBtn=findViewById(R.id.updateInfoBtnId);
        infoSpinner=findViewById(R.id.infoSpinner);


        progressDialog=new ProgressDialog(this);

        Bundle bundle=getIntent().getExtras();
        islamicInfo_model= (IslamicInfo_Model) bundle.getSerializable("MYDATA");
        infoKey=islamicInfo_model.islamic_Info_key;
        updatetitle.setText(islamicInfo_model.title);
        Glide.with(UpdateIskamicInfoActivity.this).load(islamicInfo_model.infoImage).into(imageView);
        updatedetail.setText(islamicInfo_model.detail);
        calendar= Calendar.getInstance();
        currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(UpdateIskamicInfoActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);


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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_titil=  updatetitle.getText().toString();
                String str_detail=   updatedetail.getText().toString();
                String str_infoSpinner=   infoSpinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(str_titil)){
                    updatetitle.setError("Empty Field");
                    return;
                }else if (TextUtils.isEmpty(str_detail)){
                    updatedetail.setError("Empty Field");
                    return;
                }else
                if (TextUtils.isEmpty(str_infoSpinner)){
                    Toast.makeText(UpdateIskamicInfoActivity.this, "Please Select Post Type", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    uploadData(str_titil,str_detail,str_infoSpinner);
                }

            }
        });



    }

    private void uploadData(final String str_titil, final String str_detail, final String str_infoSpinner) {
        progressDialog.setTitle("File Uploading");
        progressDialog.show();


        if (filePath!=null) {
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
                            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            //       String password  = FirebaseAuth.getInstance();


                            IslamicInfo_Model islamicInfo_model = new IslamicInfo_Model(
                                    Appdata.uid,
                                    str_titil,
                                    str_detail,
                                    str_infoSpinner,
                                    uri.toString(),
                                    currentDate

                            );

                            addData(islamicInfo_model);


                        }
                    });


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percent = 100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded\t" + (int) percent + "%");

                }
            });
        }else
        {
            IslamicInfo_Model islamicInfo_model1 = new IslamicInfo_Model(Appdata.uid,
                    str_titil,
                    str_detail,
                    str_infoSpinner,
                    islamicInfo_model.infoImage,
                    currentDate
            );

            addData(islamicInfo_model1);
        }
    }

    private void addData(IslamicInfo_Model islamicInfo_model) {

        FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("Islamic_info").child(infoKey)
                .setValue(islamicInfo_model, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (ref != null) {

                            Log.e("TAG", "Data Not saved");
                            Toast.makeText(UpdateIskamicInfoActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        } else {
                            Toast.makeText(UpdateIskamicInfoActivity.this, "Data not updated successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK){
            filePath=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filePath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}