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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddIslamic_Info_Activity extends AppCompatActivity {


    ImageView imageView;
    EditText title,detail;
    Button submitBtn;
    Bitmap bitmap;
    Uri filePath;
    ProgressDialog progressDialog;
    Calendar calendar;
    String currentDate;
     Spinner infoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_islamic__info_);
        progressDialog=new ProgressDialog(this);

        imageView = findViewById(R.id.infoImageID);
        title = findViewById(R.id.edtInfoTitlID);
        detail = findViewById(R.id.edtInfoDetaillID);
        submitBtn=findViewById(R.id.addInfoBtnId);
        infoSpinner=findViewById(R.id.infoSpinner);
        calendar=Calendar.getInstance();
        currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dexter.withActivity(AddIslamic_Info_Activity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

       submitBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             String str_titil=  title.getText().toString();
               String str_detail=   detail.getText().toString();
               String str_infoSpinner=   infoSpinner.getSelectedItem().toString();

               if (TextUtils.isEmpty(str_titil)){
                   title.setError("Empty Field");
                   return;
               }else if (TextUtils.isEmpty(str_detail)){
                   detail.setError("Empty Field");
                   return;
               }else
               if (TextUtils.isEmpty(str_infoSpinner)){
                   Toast.makeText(AddIslamic_Info_Activity.this, "Please Select Post Type", Toast.LENGTH_SHORT).show();
               return;
               }else {

                   UploadData(str_titil,str_detail,str_infoSpinner);
               }
           }
       });


    }

    private void UploadData(final String str_titil, final String str_detail, final String str_infoSpinner) {

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
                            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                            //       String password  = FirebaseAuth.getInstance();


                            addData(
                                    Appdata.uid,
                                    str_titil,
                                    str_detail,
                                    str_infoSpinner,
                                    currentDate,
                                    uri.toString()
                            );


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
        }catch (Exception e){
            Toast.makeText(this, "Image is not selected...!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void addData(String uid, String str_titil, String str_detail, String str_infoSpinner, String currentDate, String uri) {

        IslamicInfo_Model islamicInfo_model = new IslamicInfo_Model(
                uid,
                str_titil,
                str_detail,
                str_infoSpinner,
                uri,
                currentDate
        );
        FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("Islamic_info").push().setValue(islamicInfo_model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        title.setText("");
                        detail.setText("");


                        Toast.makeText(AddIslamic_Info_Activity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddIslamic_Info_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
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