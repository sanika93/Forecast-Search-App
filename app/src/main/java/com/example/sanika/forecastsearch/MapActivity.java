package com.example.sanika.forecastsearch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Window;


import com.hamweather.aeris.communication.AerisEngine;
import android.content.Intent;
public class MapActivity extends AppCompatActivity {

    String getData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        Intent intent=getIntent();
        getData=intent.getStringExtra("coords");
        AerisEngine.initWithKeys("J17UryOhDrlBTn67jkI0Y","2gAILkZ8kT4kGWUMW1CHRpNfIGCZ3Y1sgyCCoBHt",this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFragment fragmentMaps = new MapFragment();
        fragmentTransaction.add(R.id.aerisMapFragment, fragmentMaps,"MAPSVIEW");
        fragmentTransaction.commit();

    }

    protected String getJSONData(){

        return getData;
    }

}
