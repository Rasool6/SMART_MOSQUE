package com.variable.smartmosque;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class MosqueProfileAdapter extends RecyclerView.Adapter<MosqueProfileAdapter.ViewHolder> {
    Context context;
    List<AdminModel> list;

    public MosqueProfileAdapter(Context context, List<AdminModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mosque_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AdminModel adminModel=list.get(position);
        Glide.with(context).load(adminModel.mosquImage).into(holder.imageView);
        holder.mosaue_name.setText(adminModel.mosqueName);
        holder.mosque_imam.setText(adminModel.imamName);
        holder.sectId.setText(adminModel.sect);
        holder.city.setText(adminModel.mosque_city);
        holder.mosque_location.setText(adminModel.mosqueLocation);



        holder.updateMosquePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminModel adminModel1=list.get(position);
                Bundle bundle =new Bundle();
                bundle.putSerializable("Data", (Serializable) adminModel1 );
                Intent intent =new Intent(context,UpdateMosqueProfile.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mosaue_name, mosque_imam, mosque_location, city,sectId;
        Button updateMosquePro;
        CircleImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mosaue_name = itemView.findViewById(R.id.mosquenameId);
            mosque_imam = itemView.findViewById(R.id.imamnameid);
            mosque_location = itemView.findViewById(R.id.locationId);
            city = itemView.findViewById(R.id.city);
            sectId = itemView.findViewById(R.id.sect);
            imageView = itemView.findViewById(R.id.imgProMosq);

            updateMosquePro = itemView.findViewById(R.id.updateMosquePro);
        }
    }
}
