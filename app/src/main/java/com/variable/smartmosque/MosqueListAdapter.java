package com.variable.smartmosque;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class MosqueListAdapter extends RecyclerView.Adapter<MosqueListAdapter.ViewHolder> {

     Context context;
      List<MosqueProfileModel> list=new ArrayList<>();

    public MosqueListAdapter(Context context, List<MosqueProfileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.mosqulist_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MosqueProfileModel mosqueProfileModel=list.get(position);

        holder.mosaue_name.setText(mosqueProfileModel.mosque_name);
        holder.mosque_imam.setText(mosqueProfileModel.mosque_imam);
        holder.mosque_location.setText(mosqueProfileModel.mosque_location);
        holder.mosque_city.setText(mosqueProfileModel.mosque_city);
        Glide.with(context).load(mosqueProfileModel.mosque_image).into(holder.imageView);

       holder.getDtailBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                  context.startActivity(new Intent(context,MosqueSpecificDetailActicity.class).putExtra("mosquKey",mosqueProfileModel.mosqueProfile_key));



           }
       });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mosaue_name, mosque_imam, mosque_location, mosque_city, getDtailBtn;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mosaue_name = itemView.findViewById(R.id.mosquenameId);
            mosque_imam = itemView.findViewById(R.id.imamnameid);
            mosque_location = itemView.findViewById(R.id.locationId);
            mosque_city = itemView.findViewById(R.id.mosquecityid);
            imageView = itemView.findViewById(R.id.imgProMosq);
            getDtailBtn = itemView.findViewById(R.id.textView);



        }
    }
}
