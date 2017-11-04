package ked.atc_simulator.Entities;


import android.util.Log;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Gameplay.Route;
import ked.atc_simulator.Gameplay.RunwayRoute;
import ked.atc_simulator.State.ArrivingState;
import ked.atc_simulator.State.PlaneState;
import ked.atc_simulator.Utils.CoordinateConverter;

public class Plane {

    private PlanePath path;
    private int alt; // Imperial system (feet, knots)
    private float heading,speed;
    private Point base;
    private Route route;
    private GameActivity context;
    private PlaneState planeState;

    public Plane(GameActivity context, float x, float y, float heading, Route route, PlaneState planeState){
        base = new Point(x,y);
        this.route = route;
        alt = 0;
        speed = route.getSpeed();
        this.heading = heading;
        path = new PlanePath(context, base, heading);
        this.context = context;
        this.planeState = planeState;
    }

    public PlanePath getPath(){ return path; }

    public void setRoue(Route route){
        this.route = route;
        this.heading = route.getHeading();
    }

    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public PlaneState getPlaneState() {
        return planeState;
    }

    public void setPlaneState(PlaneState planeState) {
        this.planeState = planeState;
    }

    public void calculateNewParams(){
        if(route.getNextRoute() != null) {
            float diffX = CoordinateConverter.GetXDipsFromCoordinate(context,base.x - route.getNextRoute().getStartPoint().x);
            float diffY = CoordinateConverter.GetXDipsFromCoordinate(context,base.y - route.getNextRoute().getStartPoint().y);
            Log.i("Refresh", "Calculating new params routeName: "+route.getName()+", speed : "+(speed/3)+", diffX: "+diffX+" , diffY: "+diffY);
            int pcx = route.getNextRoute().getPrecisionCoefX();
            int pcy = route.getNextRoute().getPrecisionCoefY();
            if(diffX <= speed/pcx && diffX > -(speed/pcx) && diffY <= speed/pcy && diffY > -(speed/pcy)) {
               route = route.getNextRoute();
                Log.i("Refresh","Switching route "+route.getName());
            }

            if(route.getName().equals("Base")){
                if(planeState instanceof ArrivingState) Log.i("Refresh","planeState is ArrivingState"); else Log.i("Refresh","planeState is PlaneState");
                route.setNextRoute(planeState.baseAction());
                Log.i("RefreshState","BaseAction , routeName : "+route.getNextRoute().getName());
            }else if(route.getName().equals("CrosswindRN")){
                route.setNextRoute(planeState.crosswindRNAction());
                Log.i("RefreshState","CrosswindRNAction , routeName : "+route.getName());
            }else if(route.getName().equals("Final") && (diffY >= 5 || diffY <= -5)){
                base.y = route.getStartPoint().y;
            }

            heading = route.getHeading();
            speed = route.getSpeed();

            if(route instanceof RunwayRoute){
                float endx = (route.getStartPoint().x + ((RunwayRoute) route).getLenght());
                if(route.getName().equals("RunwayTO"))
                    speed = route.getSpeed()*(base.x/endx);
                else speed = route.getSpeed()*(route.getStartPoint().x/base.x);
                Log.i("Speed","runway speed:"+(route.getSpeed()/(base.x/endx)));
            }
        }

        base.x += ((speed / 2) / 3.6) * Math.cos(Math.toRadians(heading - 90));
        base.y += ((speed / 2) / 3.6) * Math.sin(Math.toRadians(heading - 90));
        Log.i("Refresh", "Calculating new params : x = " + base.x + ", y = " + base.y);
        path.updatePoints(base, heading);
    }
}
