package com.hikerr.mapbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jsasek.mapbox.R;

public class DevMenuActivity extends AppCompatActivity {

    private GPSTracker gpsTracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_menu);

    }

    public void uiEventGetLocation(View v){


        System.out.println(getCurrentLocation());

    }

    public void uiEventReCenter(View v){

        Location loc = getCurrentLocation();

        Intent output = new Intent();
        output.putExtra(MapActivityConstants.LATITUDE, loc.getLatitude());
        output.putExtra(MapActivityConstants.LONGITUDE, loc.getLongitude());
        setResult(MapActivityConstants.MOVE_MAP, output);
        finish();

    }

    public void uiEventMapView(View v){

        finish();

    }

    @SuppressLint("NewApi")
    private Location getCurrentLocation(){

        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if(this.gpsTracker == null){
            this.gpsTracker = new GPSTracker(getBaseContext());
        }

        return this.gpsTracker.getLocation();


    }

}
