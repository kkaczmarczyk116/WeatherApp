package com.example.weatherapp;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Weather48 implements Serializable {
    private final String day;
    private final long dt;
    private final String icon;
    private final String temp;
    private final String desc;
    //private Bitmap bitmap;


    public Weather48(String day, long dt, String icon, String temp, String desc) {
        this.day = day;
        this.dt = dt;
        this.icon = icon;
        this.temp = temp;
        this.desc = desc;
        //this.bitmap = bitmap;
    }

    public String getDay() {
        return day;
    }

    public double getDt() {
        return dt;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }

    public String getDesc() {
        return desc;
    }

//    public Bitmap getBitmap() {
//        return bitmap;
//    }
//
//    public void setBitmap(Bitmap bitmap) {
//        this.bitmap = bitmap;
//    }
}
