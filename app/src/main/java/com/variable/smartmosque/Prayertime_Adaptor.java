package com.variable.smartmosque;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Prayertime_Adaptor extends RecyclerView.Adapter<Prayertime_Adaptor.ViewHold> {

    Context context;
    List<PraytimeModel> list;
     String p_key;

    public Prayertime_Adaptor(Context context, List<PraytimeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.praytime_row,parent,false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHold holder, final int position) {

        final PraytimeModel praytimeModel=list.get(position);

        holder.prayerName.setText(praytimeModel.prayerName);
        holder.prayerTime.setText(praytimeModel.prayerTime);
        holder.aboutPrayer.setText(praytimeModel.aboutPrayer);
        holder.currentDate.setText(praytimeModel.currentDate);
        //   Editing paryer time
           holder.editBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final DialogPlus dialogPlus=DialogPlus.newDialog(holder.aboutPrayer.getContext())
                           .setContentHolder(new ViewHolder(R.layout.prayer_tim_content))
                           .setExpanded(true,800)
                           .create();
                   View view=dialogPlus.getHolderView();
                   final EditText prayeName=view.findViewById(R.id.editPrayerName);
                   final EditText prayerTime=view.findViewById(R.id.editPrayerTime);
                   final EditText aboutprayer=view.findViewById(R.id.editAboutPrayer);

                   Button editBtn=view.findViewById(R.id.updatePrayerBtnId);
                     p_key=praytimeModel.praytime_key;
                   prayeName.setText(praytimeModel.prayerName);
                   prayerTime.setText(praytimeModel.prayerTime);
                   aboutprayer.setText(praytimeModel.aboutPrayer);
                   Calendar calendar=Calendar.getInstance();
                   final String currentDate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                   dialogPlus.show();


                   editBtn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                          PraytimeModel praytimeModel = new PraytimeModel(Appdata.uid,prayeName.getText().toString(),
                                   prayerTime.getText().toString(),
                                   aboutprayer.getText().toString(),
                                   currentDate

                           );

                           FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("PrayerTime")
                                   .child(p_key)
                                   .setValue(praytimeModel, new DatabaseReference.CompletionListener() {
                                       @Override
                                       public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                           if (ref != null) {
                                               dialogPlus.dismiss();
                                               Log.e("TAG", "Data Not saved");
                                             //  Toast.makeText(context, "Data not updated successfully", Toast.LENGTH_SHORT).show();


                                           } else {
                                               dialogPlus.dismiss();
                                               Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   });

                      }
                   });


               }
           });




//   deleting paryer time
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PraytimeModel praytimeModel1=list.get(position);

                Bundle bundle=new Bundle();
                bundle.putSerializable("MYDATA", (Serializable) praytimeModel1);
                Intent intent=new Intent(context,PraytimeDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.deletpraytimebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete Pray time");
                builder.setMessage("Are you sure..!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("MosqueAdmin").child("PrayerTime").child(praytimeModel.praytime_key).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(context, "Pray Time Deleted...!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();


            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHold extends RecyclerView.ViewHolder {
        TextView prayerName,prayerTime,aboutPrayer,currentDate,detailBtn;
        ImageView deletpraytimebtn,editBtn;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            prayerName=itemView.findViewById(R.id.prayerNameshow);
                    prayerTime=itemView.findViewById(R.id.prayerTimeshow);
            aboutPrayer=itemView.findViewById(R.id.aboutPrayershow);
            currentDate=itemView.findViewById(R.id.currentdateshow);
            detailBtn=itemView.findViewById(R.id.detailPrytimBtn);
            deletpraytimebtn=itemView.findViewById(R.id.deletpraytime);
            editBtn=itemView.findViewById(R.id.editpraytime);

        }
    }
}
