package ked.atc_simulator.Entities;


import android.util.Log;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Gameplay.Route;
import ked.atc_simulator.Gameplay.RunwayRoute;
import ked.atc_simulator.Utils.CoordinateConverter;

public class Plane {

    private PlanePath path;
    private int alt; // Imperial system (feet, knots)
    private float heading,speed;
    private Point base;
    private Route route;
    private GameActivity context;

    public Plane(GameActivity context, float x, float y, float heading, Route route){
        base = new Point(x,y);
        this.route = route;
        alt = 0;
        speed = route.getSpeed();
        this.heading = heading;
        path = new PlanePath(context, base, heading);
        this.context = context;
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

    public void calculateNewParams(){
        if(route.getNextRoute() != null) {
            float diffX = CoordinateConverter.GetXDipsFromCoordinate(context,base.x - route.getNextRoute().getStartPoint().x);
            float diffY = CoordinateConverter.GetXDipsFromCoordinate(context,base.y - route.getNextRoute().getStartPoint().y);
            Log.i("Refresh", "Calculating new params speed : "+(speed/3)+", diffX: "+diffX+" , diffY: "+diffY);
            if (diffX <= speed/3 && diffX > -(speed/3) && diffY <= speed/3 && diffY > -(speed/3)) {
                route = route.getNextRoute();
            }
        }

        heading = route.getHeading();
        speed = route.getSpeed();

        if(route instanceof RunwayRoute){
            float endx = (route.getStartPoint().x + ((RunwayRoute) route).getLenght());
            speed = route.getSpeed()*(base.x/endx);
            Log.i("Speed","runway speed:"+(route.getSpeed()*(base.x/endx)));
        }

        base.x += ((speed / 2) / 3.6) * Math.cos(Math.toRadians(heading - 90));
        base.y += ((speed / 2) / 3.6) * Math.sin(Math.toRadians(heading - 90));
        Log.i("Refresh", "Calculating new params : x = " + base.x + ", y = " + base.y);
        path.updatePoints(base, heading);
    }
}
