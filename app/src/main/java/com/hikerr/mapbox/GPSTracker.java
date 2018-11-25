package com.hikerr.mapbox;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.hikerr.trails.TrailManager;
import com.hikerr.trails.TrailManagerFactory;

public class GPSTracker extends Service {

    private final Context mContext;

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 1; // 1 minute

    // Declaring a Location Manager
    private static GPSTracker instance;

    public static GPSTracker getInstance(Context context){

        if(GPSTracker.instance == null){
            GPSTracker.instance = new GPSTracker(context);
        }

        return GPSTracker.instance;

    }


    public GPSTracker(Context context) {
        this.mContext = context;

        LocationManager locationManager = getLocationManager();

    }

    @SuppressLint("MissingPermission")
    public void bindForUpdates(LocationListener listener) {

        getLocationManager().requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);

    }

    public Location getLastKnownLocation(){

        LocationManager locationManager = getLocationManager();
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    @SuppressLint("MissingPermission")
    public LocationManager getLocationManager(){

        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
                // GPS is not enabled for some reason wtf do we do here?
                this.canGetLocation = false;
            } else {
                this.canGetLocation = true;
                return locationManager;

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}