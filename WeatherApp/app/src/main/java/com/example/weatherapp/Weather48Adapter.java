package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Weather48Adapter extends RecyclerView.Adapter<Weather48ViewHolder> {
    private final MainActivity mainAct;
    private ArrayList<Weather48> weather48List = new ArrayList<>();
    private SelectListener listener;

    public Weather48Adapter(MainActivity mainAct, ArrayList<Weather48> weather48List, SelectListener listener) {
        this.mainAct = mainAct;
        this.weather48List = weather48List;
        this.listener = listener;
    }


    @NonNull
    @Override
    public Weather48ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_entry,parent,false);
        return new Weather48ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Weather48ViewHolder holder, int position) {
        Weather48 w48 = weather48List.get(position);
        holder.entryDay.setText(w48.getDay());
        String iconCode = "_" +w48.getIcon();
        int code = mainAct.getResources().getIdentifier(iconCode,"drawable",mainAct.getPackageName());
        holder.iconHolder.setImageResource(code);
        holder.entryDesc.setText(w48.getDesc());
        holder.entryTemp.setText(w48.getTemp());
        String temptime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date((long) (w48.getDt() * 1000)));
        holder.entryTime.setText(temptime);
        holder.entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(weather48List.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return weather48List.size();
    }



}
