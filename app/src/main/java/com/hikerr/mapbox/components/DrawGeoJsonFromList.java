package com.hikerr.mapbox.components;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

public class DrawGeoJsonFromList extends AsyncTask<Void, Void, List<LatLng>> {

    private Activity activity = null;
    private MapboxMap mapboxMap = null;
    private List<Location> locationList = null;

    public void setComponents(Activity activity, MapboxMap mapboxMap, List<Location> locationList){
        
        this.activity = activity;
        this.locationList = locationList;
        this.mapboxMap = mapboxMap;
        
    }


    @Override
    protected List<LatLng> doInBackground(Void... voids) {

        ArrayList<LatLng> points = new ArrayList<>();

        for(Location loc : this.locationList){

            LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            points.add(latLng);

        }

        return points;
    }

    @Override
    protected void onPostExecute(List<LatLng> points) {
        super.onPostExecute(points);

        Log.d("DrawGeoJsonList", "onPostExecute: ");

        if (points.size() > 0) {

            // Draw polyline on map
            mapboxMap.addPolyline(new PolylineOptions()
                    .addAll(points)
                    .color(Color.parseColor("#3bb2d0"))
                    .width(2));
        }
    }

}
