package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class Weather48ViewHolder extends RecyclerView.ViewHolder {
    TextView entryDay;
    TextView entryTime;
    ImageView iconHolder;
    TextView entryTemp;
    TextView entryDesc;
    ConstraintLayout entry;

    public Weather48ViewHolder(@NonNull View itemView) {
        super(itemView);
        entryDay = itemView.findViewById(R.id.entryDay);
        entryTime = itemView.findViewById(R.id.entryTime);
        iconHolder = itemView.findViewById(R.id.iconHolder);
        entryTemp = itemView.findViewById(R.id.entryTemp);
        entryDesc = itemView.findViewById(R.id.entryDesc);
        entry = itemView.findViewById(R.id.entry);


    }


}
