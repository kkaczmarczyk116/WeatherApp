package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Weather7Adapter extends RecyclerView.Adapter<Weather7ViewHolder>{
    private final MainActivity2 mainAct2;
    private ArrayList<Weather7> weather7List = new ArrayList<>();


    public Weather7Adapter(MainActivity2 mainAct2, ArrayList<Weather7> weather7List) {
        this.mainAct2 = mainAct2;
        this.weather7List = weather7List;
    }

    @NonNull
    @Override
    public Weather7ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather7_entry,parent,false);
        return new Weather7ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Weather7ViewHolder holder, int position) {
        Weather7 w7 = weather7List.get(position);
        holder.day_date.setText(w7.getTitle());
        holder.temp.setText(w7.getTemp());
        holder.desc.setText(w7.getDesc());
        holder.precip.setText(w7.getPrec());
        holder.uv.setText(w7.getUv());
        holder.morn.setText(w7.getMorn());
        holder.day.setText(w7.getMorn());
        holder.eve.setText(w7.getEve());
        holder.night.setText(w7.getNight());
        String iconCode = "_" +w7.getIcon();
        int code = mainAct2.getResources().getIdentifier(iconCode,"drawable",mainAct2.getPackageName());
        holder.icon.setImageResource(code);

    }

    @Override
    public int getItemCount() {
        return weather7List.size();
    }
}
