package com.example.weatherapp;

import java.io.Serializable;

public class Weather7 implements Serializable {

    private final String title;
    private final String temp;
    private final String desc;
    private final String prec;
    private final String uv;
    private final String morn;
    private final String day;
    private final String eve;
    private final String night;
    private final String icon;

    public Weather7(String title, String temp, String desc, String prec, String uv, String morn,String day, String eve, String night, String icon) {
        this.title = title;
        this.temp = temp;
        this.desc = desc;
        this.prec = prec;
        this.uv = uv;
        this.morn = morn;
        this.day = day;
        this.eve = eve;
        this.night = night;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getEve() {
        return eve;
    }

    public String getNight() {
        return night;
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

    public String getPrec() {
        return prec;
    }

    public String getUv() {
        return uv;
    }

    public String getMorn() {
        return morn;
    }
}
