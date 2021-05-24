package com.variable.smartmosque;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.List;

class IslamicInfoUser_adapter extends RecyclerView.Adapter<IslamicInfoUser_adapter.ViewHolder> {
  Context context;
  List<IslamicInfo_Model> list;

    public IslamicInfoUser_adapter(Context context, List<IslamicInfo_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.islamic_info_user_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        IslamicInfo_Model islamicInfo_model=list.get(position);
        holder.title.setText(islamicInfo_model.title);
        Glide.with(context).load(islamicInfo_model.infoImage).into(holder.infoImage);
        holder.detail.setText(islamicInfo_model.detail);
        holder.current_date.setText(islamicInfo_model.current_date);
        holder.category.setText(islamicInfo_model.infoSpinner);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        ImageView infoImage,popUpbtn;
        TextView title,detail,current_date,category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            infoImage=itemView.findViewById(R.id.infoShowImageId);
            title=itemView.findViewById(R.id.titlshow);
            detail=itemView.findViewById(R.id.detailshow);
            current_date=itemView.findViewById(R.id.infoDate);
            category=itemView.findViewById(R.id.inforspinnerId);

            popUpbtn=itemView.findViewById(R.id.popUp);


            popUpbtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            popUpSHow(v);

        }

        private void popUpSHow(View v) {

            PopupMenu popupMenu= new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.po_up_menu2);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();


        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            IslamicInfo_Model islamicInfo_model=list.get(getAdapterPosition());
            switch (item.getItemId()){

                case R.id.item_detail:


                    Bundle bundle=new Bundle();
                    bundle.putSerializable("MYDATA", (Serializable) islamicInfo_model);
                    Intent intent=new Intent(context,IslaminInfoDetailActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                    return true;
                default:
                    return false;
            }

        }
    }
}
