package ked.atc_simulator.Entities;


import android.util.Log;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Gameplay.Route;
import ked.atc_simulator.Utils.CoordinateConverter;

public class Plane {

    private PlanePath path;
    private int alt,speed; // Imperial system (feet, knots)
    private float heading;
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

    public int getSpeed() {
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
            float diffX = CoordinateConverter.GetXDipsFromCoordinate(context,base.x - route.getEndPoint().x);
            float diffY = CoordinateConverter.GetXDipsFromCoordinate(context,base.y - route.getEndPoint().y);

            if (diffX <= 10 && diffX > -10 && diffY <= 10 && diffY > -10) {
                route = route.getNextRoute();
                heading = route.getHeading();
                speed = route.getSpeed();
            }

            base.x += ((speed / 2) / 3.6) * Math.cos(Math.toRadians(heading - 90));
            base.y += ((speed / 2) / 3.6) * Math.sin(Math.toRadians(heading - 90));
            Log.i("Refresh", "Calculating new params : x = " + base.x + ", y = " + base.y);
            path.updatePoints(base, heading);
        }
    }
}
