package com.hikerr.trails;

public class TrailManagerFactory {

    private static TrailManager instance;

    public static TrailManager getInstance(){

        if (TrailManagerFactory.instance == null){

            TrailManagerFactory.instance = new TrailManager();

        }

        return TrailManagerFactory.instance;

    }

}
