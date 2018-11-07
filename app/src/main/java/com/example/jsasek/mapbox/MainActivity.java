package com.example.jsasek.mapbox;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoianNhc2VrIiwiYSI6ImNqbnY0NWR4aDAzZnozeG50aXgzbjFoNGYifQ.p3xESS-zIpp6a2_feGdxNQ");
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    public void uiEventReCenter(View v){

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(34.032666, -80.363160))
                        .zoom(12.038777)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 1000);

            }
        });


    }

    public void uiEventDevMenuView(View v){

        setContentView(R.layout.dev_menu);


    }

    public void uiEventMapView(View v){

        setContentView(R.layout.activity_main);

    }

    public void uiEventGetLocation(View v){


        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if(this.gpsTracker == null){
            this.gpsTracker = new GPSTracker(getBaseContext());
        }

        System.out.println(this.gpsTracker.getLocation());

    }

}
