package com.hikerr.mapbox;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.jsasek.mapbox.R;
import com.hikerr.mapbox.components.DrawGeoJsonFromList;
import com.hikerr.trails.TrailManager;
import com.hikerr.trails.TrailManagerFactory;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private MapView mapView;
    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoianNhc2VrIiwiYSI6ImNqbnY0NWR4aDAzZnozeG50aXgzbjFoNGYifQ.p3xESS-zIpp6a2_feGdxNQ");
        setContentView(R.layout.activity_main);

        GPSTracker.getInstance(getBaseContext()).bindForUpdates(this);

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


    public void uiEventDevMenuView(View v){

        Intent i = new Intent(this, DevMenuActivity.class);
        startActivityForResult(i, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == MapActivityConstants.MOVE_MAP && data != null) {
            Double latitude = data.getDoubleExtra(MapActivityConstants.LATITUDE, 0);
            Double longitude = data.getDoubleExtra(MapActivityConstants.LONGITUDE, 0);

            moveToPosition(latitude, longitude);

        }
    }


    private void moveToPosition(double latitude, double longitude){

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(12.038777)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 1000);

            }
        });


    }


    @Override
    public void onLocationChanged(Location location) {

        TrailManager tm = TrailManagerFactory.getInstance();

        tm.addLocation(location);

        MainActivity main = this;

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                DrawGeoJsonFromList drawComponent = new DrawGeoJsonFromList();
                drawComponent.setComponents(main, mapboxMap, tm.getTrail());
                drawComponent.execute();

            }
        });

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}
