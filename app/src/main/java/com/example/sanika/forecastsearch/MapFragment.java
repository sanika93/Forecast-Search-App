package com.example.sanika.forecastsearch;

import android.app.Fragment;

import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.tiles.AerisTile;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;
/**
 * Created by sanika on 07-12-2015.
 */
public class MapFragment extends MapViewFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);
        mapView.addLayer(AerisTile.RADSAT);
        initMap();
        return view;
    }

    private void initMap() {

        String newData=((MapActivity)getActivity()).getJSONData();
        newData=newData.replace("\\","");
        JSONObject jData=null;
        Float latitude=null;
        Float longitude=null;
        try{
        jData=new JSONObject(newData.substring(1,newData.length()-1));
            latitude=Float.parseFloat(jData.getString("latitude"));
            longitude=Float.parseFloat(jData.getString("longitude"));
        }catch (JSONException e){

            throw new RuntimeException(e);
        }

        mapView.moveToLocation(new LatLng(latitude,longitude),
                6);




    }



}
