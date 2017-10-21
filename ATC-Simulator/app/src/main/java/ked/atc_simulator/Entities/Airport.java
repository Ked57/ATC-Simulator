package ked.atc_simulator.Entities;


import android.graphics.Point;

import java.util.ArrayList;

import ked.atc_simulator.Canvas.RunwayPath;

public class Airport {

    private ArrayList<Runway> runways;
    private ArrayList<Taxiway> taxiways;

    public Airport(){

        this.runways = new ArrayList<Runway>();
        this.taxiways = new ArrayList<Taxiway>();
    }

    public ArrayList<Runway> getRunways(){
        return runways;
    }

    public void addRunway(Runway runway) { runways.add(runway); }

    public void removeRunway(Runway runway) { runways.remove(runway); }
}
