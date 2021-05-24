package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;

public class AddPrayerTimeActivity extends AppCompatActivity {

    EditText paryerName,aboutPrayer;
    TextView prayerTime;
    ImageView pick;
    Button btnAddParyeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prayer_time);

        paryerName=findViewById(R.id.edtPrayerName);
        prayerTime=findViewById(R.id.edtPrayerTime);
        aboutPrayer=findViewById(R.id.edtAboutPrayer);
        btnAddParyeTime=findViewById(R.id.addPrayerBtnId);
        pick=findViewById(R.id.pick);


        pick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddPrayerTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        prayerTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btnAddParyeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_paryerName=paryerName.getText().toString().trim();
                String str_prayerTime=prayerTime.getText().toString().trim();
                String str_aboutPrayer=aboutPrayer.getText().toString().trim();

                Calendar calendar=Calendar.getInstance();
                String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                if (TextUtils.isEmpty(str_paryerName)){
                    paryerName.setError("Empty Field");
                    return;
                }else  if (TextUtils.isEmpty(str_prayerTime)){
                    prayerTime.setError("Empty Field");
                    return;
                }else if (TextUtils.isEmpty(str_aboutPrayer)){
                    aboutPrayer.setError("Empty Field");
                    return;
                }else{
                    PraytimeModel praytimeModel=new PraytimeModel( Appdata.uid,str_paryerName,str_prayerTime,str_aboutPrayer,currentDate);

                    FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("PrayerTime").push().setValue(praytimeModel)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    paryerName.setText("");
                                    prayerTime.setText("");
                                    aboutPrayer.setText("");


                                    Toast.makeText(AddPrayerTimeActivity.this, "Prayer Time Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPrayerTimeActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }


}