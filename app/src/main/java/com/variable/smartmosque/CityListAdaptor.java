package com.variable.smartmosque;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

class CityListAdaptor extends RecyclerView.Adapter<CityListAdaptor.ViewHolder> {

    Context context;
    List<MosqueProfileModel> list;

    public CityListAdaptor(Context context, List<MosqueProfileModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(context).inflate(R.layout.city_list_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
          final MosqueProfileModel mosqueProfileModel=list.get(position);
          holder.citynametxt.setText(mosqueProfileModel.mosque_city);

          holder.cardBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  MosqueProfileModel data=list.get(position);
                  Bundle bundle=new Bundle();
                  bundle.putSerializable("data", (Serializable) data);
                  Intent intent=new Intent(context,MosqueListActivity.class);
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

        CardView cardBtn;
        TextView citynametxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardBtn=itemView.findViewById(R.id.cardView);
            citynametxt=itemView.findViewById(R.id.cityname);

        }
    }
}
