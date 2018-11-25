package com.hikerr.mapbox.components;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class DrawGeoJsonFromFile extends AsyncTask<Void, Void, List<LatLng>> {

    private AppCompatActivity activity;
    private MapboxMap mapboxMap;

    private static final String TAG = "DrawGeojsonLineActivity";

    public void setComponents(AppCompatActivity activity, MapboxMap mapboxMap){
        
        this.activity = activity;
        this.mapboxMap = mapboxMap;
        
    }


    @Override
    protected List<LatLng> doInBackground(Void... voids) {

        ArrayList<LatLng> points = new ArrayList<>();

        try {
            // Load GeoJSON file
            InputStream inputStream = this.activity.getAssets().open("example.geojson");
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }

            inputStream.close();

            // Parse JSON
            JSONObject json = new JSONObject(sb.toString());
            JSONArray features = json.getJSONArray("features");
            JSONObject feature = features.getJSONObject(0);
            JSONObject geometry = feature.getJSONObject("geometry");
            if (geometry != null) {
                String type = geometry.getString("type");

                // Our GeoJSON only has one feature: a line string
                if (type != null && !type.isEmpty() && type.equalsIgnoreCase("LineString")) {

                    // Get the Coordinates
                    JSONArray coords = geometry.getJSONArray("coordinates");
                    for (int lc = 0; lc < coords.length(); lc++) {
                        JSONArray coord = coords.getJSONArray(lc);
                        LatLng latLng = new LatLng(coord.getDouble(1), coord.getDouble(0));
                        points.add(latLng);
                    }
                }
            }
        } catch (Exception exception) {
            Log.e(TAG, "Exception Loading GeoJSON: " + exception.toString());
        }

        return points;
    }

    @Override
    protected void onPostExecute(List<LatLng> points) {
        super.onPostExecute(points);

        if (points.size() > 0) {

            // Draw polyline on map
            mapboxMap.addPolyline(new PolylineOptions()
                    .addAll(points)
                    .color(Color.parseColor("#3bb2d0"))
                    .width(2));
        }
    }

}
