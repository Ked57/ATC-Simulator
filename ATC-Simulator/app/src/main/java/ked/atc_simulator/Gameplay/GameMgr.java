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

    private Route alpha, bravo, charlie, downwind, base, finale, upwind, crosswind, crosswindRN;
    private RunwayRoute runwayTO, runwayLanding;

    public GameMgr(){
        planes = new ArrayList<Plane>();
        airport = new Airport();

        finale = new Route(175, 90, "Final", 3, 17);
        finale.setStartPoint(new Point(150,545));

        crosswind = new Route(175, 0, "Crosswind", 3, 3);
        crosswind.setStartPoint(new Point(1750,950));

        upwind = new Route(175, 90, "Upwind", 3, 3);
        upwind.setStartPoint(new Point(150,950));

        base = new Route(175, 180, "Base", 3, 3);
        base.setStartPoint(new Point(150,200));

        downwind = new Route(175, 270, "Downwind", 3, 3);
        downwind.setStartPoint(new Point(1750,200));

        crosswindRN = new Route(175, 0, "CrosswindRN" , 3, 3);
        crosswindRN.setStartPoint(new Point(1750,545));

        runwayLanding = new RunwayRoute(150, 90,1000, "RunwayLanding", 9, 3);
        runwayLanding.setStartPoint(new Point(465,545));

        runwayTO = new RunwayRoute(150, 90,1000, "RunwayTO", 3, 3);
        runwayTO.setStartPoint(new Point(465,545));

        alpha = new Route(30, 0, "Alpha", 3, 3);
        alpha.setStartPoint(new Point(465,687));

        charlie = new Route(30, 270, "Charlie", 3, 3);
        charlie.setStartPoint(new Point(1490,685));

        bravo = new Route(30, 180, "Bravo", 9, 3);
        bravo.setStartPoint(new Point(1490,545));

        crosswind.setNextRoute(downwind);
        upwind.setNextRoute(crosswind);
        base.setNextRoute(upwind);
        downwind.setNextRoute(base);
        crosswindRN.setNextRoute(downwind);
        runwayLanding.setNextRoute(bravo);
        runwayTO.setNextRoute(crosswindRN);
        alpha.setNextRoute(runwayTO);
        charlie.setNextRoute(alpha);
        bravo.setNextRoute(charlie);
        finale.setNextRoute(runwayLanding);

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

    public Route getDownwind() {
        return downwind;
    }

    public Route getBase() {
        return base;
    }

    public Route getFinale() {
        return finale;
    }

    public Route getUpwind() {
        return upwind;
    }

    public Route getCrosswind() {
        return crosswind;
    }

    public Route getCrosswindRN() {
        return crosswindRN;
    }

    public RunwayRoute getRunwayTO() {
        return runwayTO;
    }

    public RunwayRoute getRunwayLanding() {
        return runwayLanding;
    }
}
