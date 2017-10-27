package ked.atc_simulator.Gameplay;

import java.util.ArrayList;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.Entities.Airport;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;

public class GameMgr {

    private ArrayList<Plane> planes;
    private Airport airport;

    private Route alpha, bravo, charlie, holding;

    public GameMgr(){
        planes = new ArrayList<Plane>();
        airport = new Airport();

        alpha = new Route(30, 0, null);
        alpha.setStartPoint(new Point(465,687));
        alpha.setEndPoint(new Point(465,585));

        charlie = new Route(30, 270,alpha);
        charlie.setStartPoint(new Point(1490,685));
        charlie.setEndPoint(new Point(465,687));

        bravo = new Route(30, 180,charlie);
        bravo.setStartPoint(new Point(1490,585));
        bravo.setEndPoint(new Point(1490,687));


    }

    public ArrayList<Plane> getPlanes(){ return planes;}

    public Airport getAirport(){ return airport; }

    public void addPlane(Plane plane){
        planes.add(plane);
    }

    public void removePlane(Plane plane){
        planes.remove(plane);
    }

    public Route getAlpha() {
        return alpha;
    }

    public Route getBravo() {
        return bravo;
    }

    public Route getCharlie() {
        return charlie;
    }
}
