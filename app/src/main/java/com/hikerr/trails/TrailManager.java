package com.hikerr.trails;

import android.location.Location;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class TrailManager {

    List<Location> locationList = new LinkedList<>();


    public void addLocation(Location location){

        Log.d("TrailManagerAddLocation", "addLocation: " + location);

        this.locationList.add(location);
    }


    public List<Location> getTrail(){
        return this.locationList;
    }

}
