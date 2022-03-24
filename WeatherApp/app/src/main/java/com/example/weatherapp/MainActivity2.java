package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private RequestQueue mQueue;
    private ImageView icon;
    private RecyclerView rec2;
    private ArrayList<Weather7> w7List = new ArrayList<>();
    private TextView day_date,temp,desc,precip,uv,morn,day,eve,night;
    private String[] passIsImperial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        day_date =findViewById(R.id.day_date);
        temp =findViewById(R.id.temp);
        desc =findViewById(R.id.desc);
        precip =findViewById(R.id.precip);
        uv =findViewById(R.id.uv);
        morn =findViewById(R.id.morn);
        day =findViewById(R.id.day);
        eve =findViewById(R.id.eve);
        night =findViewById(R.id.night);

        rec2 = findViewById(R.id.rec2);
        mQueue = Volley.newRequestQueue(this);


        Weather7Adapter wAdapter = new Weather7Adapter(MainActivity2.this, w7List);
        rec2.setAdapter(wAdapter);
        rec2.setLayoutManager(new LinearLayoutManager(MainActivity2.this,RecyclerView.VERTICAL,false));



        Intent intent = getIntent();
        if(intent != null){
            String location1 = intent.getStringExtra("locationParLong");
            String location2 = intent.getStringExtra("locationParLat");
            String loc = intent.getStringExtra("locName");
            passIsImperial = intent.getStringArrayExtra("isImperial");
            getSupportActionBar().setTitle(loc);
            if(location1 == null){
                SharedPreferences sp = getApplication().getSharedPreferences("myUserPrefs",Context.MODE_PRIVATE);
                String saveLoc = sp.getString("loc","Chicago,Illinois");
                String lo = sp.getString("lo","41.8675766");
                String la = sp.getString("la","-87.616232");
                passIsImperial = intent.getStringArrayExtra("isImperial");
                getSupportActionBar().setTitle(saveLoc);
                currentWeather2(lo,la);
            }
            currentWeather2(location1,location2);
            Log.d(TAG, "onCreate: "+location1+location2);
        }
        else{
            currentWeather2( "41.8675766","-87.616232");
            Log.d(TAG, "Intent is null");

        }




    }

    private void currentWeather2(String longitude, String lat){
        String key = "a970acb320885c426865c6a553425cf9";
        String url ="https://api.openweathermap.org/data/2.5/onecall?lat="+longitude+"&lon="+lat+"&appid=a970acb320885c426865c6a553425cf9&units="+passIsImperial[0]+"&lang=en&exclude=minutely";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray seven =response.getJSONArray("daily");
                            for(int i =0;i<seven.length();i++){

                                JSONObject numobj = seven.getJSONObject(i);
                                long recDt = numobj.getLong("dt");
                                String recDate = new SimpleDateFormat("E M/d", Locale.getDefault()).format(new Date(recDt * 1000));
                                String uvi = numobj.getString("uvi");
                                String pop = numobj.getString("pop");
                                JSONObject _temp = numobj.getJSONObject("temp");
                                double morn = _temp.getDouble("morn");
                                double day = _temp.getDouble("day");
                                double eve = _temp.getDouble("eve");
                                double night = _temp.getDouble("night");
                                double high = _temp.getDouble("max");
                                double low = _temp.getDouble("min");
                                JSONArray recInner =numobj.getJSONArray("weather");
                                JSONObject weatherObj = (JSONObject) recInner.get(0);
                                String recDesc = weatherObj.getString("description");
                                String recI = weatherObj.getString("icon");
                                Weather7 entry = new Weather7(recDate,Math.round(high)+passIsImperial[1]+ "/"+Math.round(low)+passIsImperial[1],
                                        recDesc,"("+pop +"% precip."+")","UV index : "+uvi,Math.round(morn)+passIsImperial[1],Math.round(day) + passIsImperial[1],
                                        Math.round(eve)+passIsImperial[1],Math.round(night)+passIsImperial[1],recI);
                                w7List.add(entry);
//

                            }
                            Weather7Adapter wAdapter = new Weather7Adapter(MainActivity2.this, w7List);
                            rec2.setAdapter(wAdapter);
                            rec2.setLayoutManager(new LinearLayoutManager(MainActivity2.this,RecyclerView.VERTICAL,false));










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


}