package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Weather7ViewHolder extends RecyclerView.ViewHolder{
    TextView day_date;
    TextView desc;
    TextView temp;
    TextView precip;
    TextView uv;
    TextView morn;
    TextView day;
    TextView eve;
    TextView night;
    ImageView icon;


    public Weather7ViewHolder(@NonNull View itemView) {
        super(itemView);
        day_date = itemView.findViewById(R.id.day_date);
        desc = itemView.findViewById(R.id.desc);
        temp = itemView.findViewById(R.id.temp);
        precip = itemView.findViewById(R.id.precip);
        uv = itemView.findViewById(R.id.uv);
        morn = itemView.findViewById(R.id.morn);
        day = itemView.findViewById(R.id.day);
        eve = itemView.findViewById(R.id.eve);
        night = itemView.findViewById(R.id.night);
        icon = itemView.findViewById(R.id.icon);

    }
}
