package com.variable.smartmosque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MosquAdapter extends RecyclerView.Adapter<MosquAdapter.ViewHolder> {

    Context context;
    List<MosqueProfileModel> list;

    public MosquAdapter(Context context, List<MosqueProfileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.mosqprofile_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

             MosqueProfileModel mosqueProfileModel= list.get(position);

             holder.mosaue_name.setText(mosqueProfileModel.mosque_name);
             holder.mosque_imam.setText(mosqueProfileModel.mosque_imam);
             holder.mosque_location.setText(mosqueProfileModel.mosque_location);
             holder.mosque_city.setText(mosqueProfileModel.mosque_city);
          //   holder.mosaue_name.setText(mosqueProfileModel.mosque_name);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mosaue_name,mosque_imam,mosque_location,mosque_city,updateMosq_pro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mosaue_name       =itemView.findViewById(R.id.mosquenameId);
            mosque_imam        =itemView.findViewById(R.id.imamnameid);
             mosque_location    =itemView.findViewById(R.id.locationId);
             mosque_city        =itemView.findViewById(R.id.mosquecityid);
             updateMosq_pro     =itemView.findViewById(R.id.updateMosquePro);

        }
    }
}
