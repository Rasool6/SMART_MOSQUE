package com.variable.smartmosque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class InfoCategory_Adapter extends RecyclerView.Adapter<InfoCategory_Adapter.ViewHolder> {
    Context context;
    List<IslamicInfo_Model> list;

    public InfoCategory_Adapter(Context context, List<IslamicInfo_Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    IslamicInfo_Model islamicInfo_model=list.get(position);
    holder.textView5.setText(islamicInfo_model.infoSpinner);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView5=itemView.findViewById(R.id.textView5);
        }
    }
}
