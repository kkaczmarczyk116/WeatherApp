package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SelectListener{
    private final String TAG = getClass().getSimpleName();
    private TextView currentDtTextV,tempTextV,feelsLikeTextV,weatherDescTextV,windsTextV,humidityTextV,uvIndexTextV,visibilityTextV,morningTempTextV,daytimeTempTextV,eveningTextV,nightTextV,sunriseTextV,sunsetTextV;
    private RequestQueue mQueue;
    private ImageView weatherIcon;
    private SwipeRefreshLayout swiper;
    private RecyclerView rec;
    private ArrayList<Weather48> wList = new ArrayList<>();
    private TextView locationTextV;
    private ConstraintLayout layer;
    private boolean isImperial;
    private SharedPreferences sp;
    private Weather48Adapter w48Adapter = new Weather48Adapter(this,wList,this);



    //TODO: Add click Intent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        currentDtTextV = findViewById(R.id.currentDtTextV);
        tempTextV = findViewById(R.id.tempTextV);
        feelsLikeTextV = findViewById(R.id.feelsLikeTextV);
        windsTextV = findViewById(R.id.windsTextV);
        humidityTextV = findViewById(R.id.humidityTextV);
        uvIndexTextV = findViewById(R.id.uvIndexTextV);
        visibilityTextV = findViewById(R.id.visibilityTextV);
        sunriseTextV = findViewById(R.id.sunriseTextV);
        sunsetTextV = findViewById(R.id.sunsetTextV);
        morningTempTextV = findViewById(R.id.morningTempTextV);
        daytimeTempTextV = findViewById(R.id.daytimeTempTextV);
        eveningTextV = findViewById(R.id.eveningTextV);
        nightTextV = findViewById(R.id.nightTextV);

        weatherDescTextV = findViewById(R.id.weatherDescTextV);
        weatherIcon = findViewById(R.id.weatherIcon);

        mQueue = Volley.newRequestQueue(this);

        swiper = findViewById(R.id.swiper);

        rec = findViewById(R.id._48hourRec);

        locationTextV=findViewById(R.id.locationTextV);

        layer = findViewById(R.id.layer);

        findLoc();

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!hasNetworkConnection()){
                    Toast.makeText(MainActivity.this, "No-Network Connection", Toast.LENGTH_SHORT).show();
                    swiper.setRefreshing(false);
                }else{
                    findLoc();
                    swiper.setRefreshing(false);
                }

            }
        });


    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SharedPreferences sp = getApplication().getSharedPreferences("myUserPrefs",Context.MODE_PRIVATE);
        boolean isImperialCheck = sp.getBoolean("isImperial",true);
        MenuItem item = menu.findItem(R.id.temp_menu);
        if (isImperialCheck == true){
            item.setIcon(R.drawable.units_f);
        } else {
            item.setIcon(R.drawable.units_c);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void findLoc(){

        sp =getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        String s1 = sp.getString("loc","Chicago,Illinois");
        locationTextV.setText(s1);

        String findloctext = locationTextV.getText().toString();
        Log.d(TAG, "text is in findloc "+findloctext);
        String[] loc = getLatLon(findloctext);
        currentWeather(loc[0],loc[1],isImperial);


    }
    String[] passIsImperial;
    private void currentWeather(String longitude, String lat,boolean isImperial){
        passIsImperial = passInfo(isImperial);
        String key = "a970acb320885c426865c6a553425cf9";
        String url ="https://api.openweathermap.org/data/2.5/onecall?lat=" +longitude+"&lon=" +lat+ "&appid=a970acb320885c426865c6a553425cf9&units="+passIsImperial[0] +"&lang=en&exclude=minutely";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject cur = response.getJSONObject("current");
                            long dt = cur.getLong("dt");
                            String date = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault()).format(new Date(dt * 1000));
                            currentDtTextV.setText(date);
                            long sunr = cur.getLong("sunrise");
                            String sunrise = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(sunr * 1000));
                            sunriseTextV.setText("Sunrise: "+sunrise);
                            long suns = cur.getLong("sunset");
                            String sunset = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date(suns * 1000));
                            sunsetTextV.setText("Sunset: " +sunset);
                            double temp = cur.getDouble("temp");
                            tempTextV.setText(Math.round(temp)+ passIsImperial[1]);
                            double feels_like = cur.getDouble("feels_like");
                            feelsLikeTextV.setText("Feels Like " + Math.round(feels_like) + passIsImperial[1]);
                            String humidity = cur.getString("humidity");
                            humidityTextV.setText("Humidity: " +humidity+ " %");
                            String uv = cur.getString("uvi");
                            uvIndexTextV.setText("UV Index : "+uv);
                            String vis = cur.getString("visibility");
                            visibilityTextV.setText("Visibility: " + vis + passIsImperial[2]);
                            double windSpeed = cur.getDouble("wind_speed");
                            double windDegree = cur.getDouble("wind_deg");
                            String wind = getDirection(windDegree) + " at "+windSpeed + passIsImperial[2];
                            windsTextV.setText("Winds: "+ wind);


                            JSONArray inner =cur.getJSONArray("weather");
                            JSONObject weatherArr = (JSONObject) inner.get(0);
                            String desc = weatherArr.getString("description");
                            weatherDescTextV.setText(desc);
                            String icon = weatherArr.getString("icon");
                            String iconCode = "_" +icon;
                            int code = getResources().getIdentifier(iconCode,"drawable",getPackageName());
                            weatherIcon.setImageResource(code);



                            JSONArray weather =response.getJSONArray("daily");
                            JSONObject jw = weather.getJSONObject(0);
                            JSONObject innerObj = jw.getJSONObject("temp");
                            double morning = innerObj.getDouble("morn");
                            morningTempTextV.setText(Math.round(morning) + passIsImperial[1]);
                            double day = innerObj.getDouble("day");
                            daytimeTempTextV.setText(Math.round(day)+  passIsImperial[1]);
                            double eve = innerObj.getDouble("eve");
                            eveningTextV.setText(Math.round(eve)+ passIsImperial[1]);
                            double night = innerObj.getDouble("night");
                            nightTextV.setText(Math.round(night)+  passIsImperial[1]);


                            JSONArray forty8 =response.getJSONArray("hourly");
                            for(int i =0;i<forty8.length();i++){

                                JSONObject numobj = forty8.getJSONObject(i);
                                double recTemp = numobj.getDouble("temp");
                                long recDt = numobj.getLong("dt");
                                String recDate = new SimpleDateFormat("E", Locale.getDefault()).format(new Date(recDt * 1000));
                                String comp = new SimpleDateFormat("E", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
                                JSONArray recInner =numobj.getJSONArray("weather");
                                JSONObject weatherObj = (JSONObject) recInner.get(0);
                                String recDesc = weatherObj.getString("description");
                                String recI = weatherObj.getString("icon");

                                if(recDate.equals(comp)){
                                    Weather48 entry = new Weather48("Today",recDt,recI,Math.round(recTemp) +  passIsImperial[1],recDesc);
                                    wList.add(entry);
                                }else{
                                    Weather48 entry = new Weather48(recDate,recDt,recI,Math.round(recTemp) +  passIsImperial[1],recDesc);
                                    wList.add(entry);
                                }
                            }

                            rec.setAdapter(w48Adapter);
                            rec.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false));




                            swiper.setRefreshing(false);





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        mQueue.add(request);

    }
    private String getDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "no info"; // We'll use 'X' as the default if we get a bad value
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.location_menu){
            enterLoc(MainActivity.this);


        }else if(item.getItemId() == R.id.daily_menu){
            //Intent info = new Intent(MainActivity.this,MainActivity2.class);
            //startActivity(info);
            Intent i = new Intent(MainActivity.this,MainActivity2.class);
            i.putExtra("locationParLong",longi);
            i.putExtra("locationParLat",lati);
            i.putExtra("locName",titleName);
            i.putExtra("isImperial",passIsImperial);
            startActivity(i);
            return false;
        }else if(item.getItemId() == R.id.temp_menu){
            isImperial = !isImperial;
            wList.clear();
            findLoc();
            if (isImperial == true){
                item.setIcon(R.drawable.units_f);
                passIsImperial = passInfo(isImperial);

            }else{
                item.setIcon(R.drawable.units_c);
                passIsImperial = passInfo(isImperial);

            }

        }else{
            Log.d(TAG, "onOptionSel  error");
        }
        return super.onOptionsItemSelected(item);
    }
    String longi;
    String lati;
    String titleName;
    private void enterLoc(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Enter a Location")
                .setMessage("For US locations, enter as 'City', or 'City,State"
                        +"\n\n"+ "For International locations enter as 'City', Country'")
                .setView(taskEditText)
                .setPositiveButton("Okay ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        //locationTextV.setText(task);
                        String[] longlat = getLatLon(task);
                        longi = (longlat[0]);
                        lati = (longlat[1]);
                        titleName = longlat[2];
                        locationTextV.setText(titleName);
                        Log.d(TAG, ""+longlat[0]+ ""+ longlat[1]);
                        //locationTextV.setText(task);
                        wList.clear();
                        sp =getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("loc",titleName);
                        editor.commit();
                        findLoc();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    private String[] getLatLon(String userProvidedLocation) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(userProvidedLocation,1);

            if (addresses == null || addresses.isEmpty()) {
                Log.d(TAG, "address is null or address is empty");
                // Nothing returned!
                return null;
            }

            double lat = addresses.get(0).getLatitude();
            double lon = addresses.get(0).getLongitude();
            String loc = addresses.get(0).getAddressLine(0);
            Log.d(TAG, lat+lon + loc);

            return new String[] {Double.toString(lat), Double.toString(lon),loc};

        } catch (IOException e) {
            Log.d(TAG, "in catch of getLatlong");
            System.out.println("in catch of getLatlong");
            // Failure to get an Address object
            return null;
        }
    }

//  //GEocoder was not responding/working // IO exception
//    private String[] getLatLon(String userProvidedLocation){
//        double x = 41.8675766;
//        double y = -87.616232;
//        String z = "Chicago";
//        return new String[]{Double.toString(x),Double.toString(y),z};
//    }




    private String[] passInfo(boolean test){
        if (test==true) {
            return new String[]{"imperial"," \u2109", " mph"};
        }else if(test == false){
            return new String[]{"metric"," \u00B0C", " km/h"};
            }
        else{
            return null;
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    @Override
    protected void onResume() {
        //checkConn();
        sp =getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        //SharedPreferences sp = getApplication().getSharedPreferences("myUserPrefs",Context.MODE_PRIVATE);
        String saveLoc = sp.getString("loc","");
        String lo = sp.getString("lo","");
        String la = sp.getString("la","");
        boolean isImperialCheck = sp.getBoolean("isImperial",true);
        locationTextV.setText(saveLoc);
        isImperial = isImperialCheck;
        findLoc();
        //currentWeather(lo,la,isImperial);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!hasNetworkConnection()){
                    Toast.makeText(MainActivity.this, "No-Network Connection", Toast.LENGTH_SHORT).show();
                    swiper.setRefreshing(false);
                }else{
                    findLoc();
                    swiper.setRefreshing(false);
                }

            }
        });


        super.onResume();
    }

//    @Override
//    protected void onStart() {
//        sp =getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
//        String saveLoc = sp.getString("loc","");
//        boolean isImperialCheck = sp.getBoolean("isImperial",true);
//        locationTextV.setText(saveLoc);
//        isImperial = isImperialCheck;
//        findLoc();
//
//        super.onStart();
//    }

    @Override
    protected void onPause() {
        sp =getSharedPreferences("myUserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String findloctext = locationTextV.getText().toString();
        editor.putString("loc",findloctext);
        editor.putBoolean("isImperial",isImperial);
        editor.putString("lo",longi);
        editor.putString("la",lati);
        editor.putString("ti",titleName);
        editor.apply();
        super.onPause();
    }


    @Override
    public void onItemClicked(Weather48 myWeahter48) {

        //double time = myWeahter48.getDt();


//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(CalendarContract.CONTENT_URI);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, time+3600);
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time);
//        intent.putExtra(CalendarContract.Events.ALL_DAY,"false");
//        Intent intent = new Intent(Intent.ACTION_INSERT)
//                .setData(CalendarContract.Events.CONTENT_URI)
////                .putExtra(CalendarContract.Events.TITLE, "test")
////                .putExtra(CalendarContract.Events.EVENT_LOCATION, "test location")
//                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time)
//                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, time+3600)
//                .putExtra(CalendarContract.Events.ALL_DAY,"false");

        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder,  (Calendar.getInstance().getTimeInMillis()));
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());








        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(this, "No App to open intent", Toast.LENGTH_SHORT).show();
        }
    }}