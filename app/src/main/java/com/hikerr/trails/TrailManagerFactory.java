package com.hikerr.trails;

public class TrailManagerFactory {

    TrailManager instance;

    public static TrailManager getInstance(){

        if (this.instance == null){

            this.instance = new TrailManager();

        }

        return this.instance;

    }

}
