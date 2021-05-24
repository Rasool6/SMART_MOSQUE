package com.variable.smartmosque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class UserShowPrayerTimeAdapter  extends RecyclerView.Adapter<UserShowPrayerTimeAdapter.ViewHolder> {

    Context context;
    List<PraytimeModel> list;

    public UserShowPrayerTimeAdapter(Context context, List<PraytimeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_showprayer_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PraytimeModel praytimeModel=list.get(position);
        holder.prayerName.setText(praytimeModel.prayerName);
        holder.prayerTime.setText(praytimeModel.prayerTime);
        holder.aboutPrayer.setText(praytimeModel.aboutPrayer);
        holder.currentDate.setText(praytimeModel.currentDate);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView prayerName,prayerTime,aboutPrayer,currentDate,detailBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            prayerName=itemView.findViewById(R.id.prayerNameshow);
            prayerTime=itemView.findViewById(R.id.prayerTimeshow);
            aboutPrayer=itemView.findViewById(R.id.aboutPrayershow);
            currentDate=itemView.findViewById(R.id.currentdateshow);
            detailBtn=itemView.findViewById(R.id.detailPrytimBtn);
        }
    }
}
