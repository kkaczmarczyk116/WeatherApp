package com.example.weatherapp;

public class CurrentWeather {

    private final String dt;
    private final String temp;
    private final String feels_like;
    private final String humidity;
    private final String uv;
    private final String sunrise;
    private final String sunset;
    private final String icon;
    private final String desc;
    private final String wind;
    private final String vis;
    private final String morn;
    private final String day;
    private final String eve;
    private final String night;

    public CurrentWeather(String dt, String temp, String feels_like, String humidity, String uv, String sunrise, String sunset, String icon, String desc, String wind, String vis, String morn, String day, String eve, String night) {
        this.dt = dt;
        this.temp = temp;
        this.feels_like = feels_like;
        this.humidity = humidity;
        this.uv = uv;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.icon = icon;
        this.desc = desc;
        this.wind = wind;
        this.vis = vis;
        this.morn = morn;
        this.day = day;
        this.eve = eve;
        this.night = night;
    }

    public String getDt() {
        return dt;
    }

    public String getTemp() {
        return temp;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getUv() {
        return uv;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getIcon() {
        return icon;
    }

    public String getDesc() {
        return desc;
    }

    public String getWind() {
        return wind;
    }

    public String getVis() {
        return vis;
    }

    public String getMorn() {
        return morn;
    }

    public String getDay() {
        return day;
    }

    public String getEve() {
        return eve;
    }

    public String getNight() {
        return night;
    }
}
