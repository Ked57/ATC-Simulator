package ked.atc_simulator.Gameplay;

import java.util.ArrayList;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Entities.Airport;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;

public class GameMgr {

    private ArrayList<Plane> planes;
    private Airport airport;

    public GameMgr(){
        planes = new ArrayList<Plane>();
        airport = new Airport();
    }

    public ArrayList<Plane> getPlanes(){ return planes;}

    public Airport getAirport(){ return airport; }

    public void addPlane(Plane plane){
        planes.add(plane);
    }

    public void removePlane(Plane plane){
        planes.remove(plane);
    }

}
