package ked.atc_simulator.Entities;


import android.util.Log;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Gameplay.Route;
import ked.atc_simulator.Gameplay.RunwayRoute;
import ked.atc_simulator.State.PlaneState;
import ked.atc_simulator.Utils.CoordinateConverter;

public class Plane {

    private PlanePath path;
    private float heading, speed;
    private Point base;
    private Route route;
    private GameActivity context;
    private PlaneState planeState;
    private int behavior; // 0 "normal",  1 "holding" -> Stays in the holding circuit, 2 "runway" -> waits before runway entrance, 3 "waiting" -> stops until further notice
    private String name;

    public Plane(GameActivity context,String name, float x, float y, float heading, Route route, PlaneState planeState) {
        base = new Point(x, y);
        this.route = route;
        speed = route.getSpeed();
        this.heading = heading;
        path = new PlanePath(context, base, heading);
        this.context = context;
        this.planeState = planeState;
        behavior = 1;
        this.name = name;
    }

    public Plane(GameActivity context,String name, float x, float y, float heading,int behavior, Route route, PlaneState planeState) {
        base = new Point(x, y);
        this.route = route;
        speed = route.getSpeed();
        this.heading = heading;
        path = new PlanePath(context, base, heading);
        this.context = context;
        this.planeState = planeState;
        this.behavior = behavior;
        this.name = name;
    }

    public Plane(){}

    public PlanePath getPath() {
        return path;
    }

    public void setRoue(Route route) {
        this.route = route;
        this.heading = route.getHeading();
    }

    public void setBehavior(int b) {
        if (b == 3 && (!route.getName().equals("Alpha") && !route.getName().equals("Bravo") && !route.getName().equals("Charlie"))) {
            behavior = 2;
        } else behavior = b;
        Log.i("Refresh", "Setting behavior to " + b);
    }

    public Point getBase(){ return base; }

    public Route getRoute(){ return route; }

    public int getBehavior(){return behavior; }

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

    public String getName() { return name; }

    public void calculateNewParams() {
        if (behavior != 3) {
            if (route.getNextRoute() != null) {
                float diffX = CoordinateConverter.GetXDipsFromCoordinate(context, base.x - route.getNextRoute().getStartPoint().x);
                float diffY = CoordinateConverter.GetXDipsFromCoordinate(context, base.y - route.getNextRoute().getStartPoint().y);

                Log.i("Refresh", "Calculating new params routeName: " + route.getName() + ", speed : " + (speed / 3) + ", diffX: " + diffX + " , diffY: " + diffY);
                int pcx = route.getNextRoute().getPrecisionCoefX();
                int pcy = route.getNextRoute().getPrecisionCoefY();

                if (diffX <= (speed / pcx) && diffX > -(speed / pcx) && diffY <= (speed / pcy) && diffY > -(speed / pcy)) {
                    if (route.getNextRoute() instanceof RunwayRoute && behavior == 2) {
                        behavior = 3;
                        return; // not sure about this
                    } else route = route.getNextRoute();
                    Log.i("Refresh", "Switching route " + route.getName());
                }

                if (route.getName().equals("Base") && behavior != 1 && !planeState.baseAction().equals(null) && !route.getNextRoute().equals(planeState.baseAction())) {
                    route.setNextRoute(planeState.baseAction());
                    Log.i("RefreshState", "BaseAction , routeName : " + route.getNextRoute().getName());
                } else if (route.getName().equals("CrosswindRN") && behavior != 1 && !planeState.crosswindRNAction().equals(null) && !route.getNextRoute().equals(planeState.crosswindRNAction())) {
                    route.setNextRoute(planeState.crosswindRNAction());
                    Log.i("RefreshState", "CrosswindRNAction , routeName : " + route.getName());
                } else if (route.getName().equals("Final") && (diffY >= 5 || diffY <= 2)) {
                    base.y = route.getStartPoint().y + 10;
                    Log.i("RefreshState", "Final Action");
                }

                heading = route.getHeading();
                speed = route.getSpeed();

                if (route instanceof RunwayRoute) {
                    float endx = (route.getStartPoint().x + ((RunwayRoute) route).getLenght());
                    if (route.getName().equals("RunwayTO"))
                        speed = route.getSpeed() * (base.x / endx);
                    else speed = route.getSpeed() * (route.getStartPoint().x / base.x);
                    Log.i("Speed", "runway speed:" + (route.getSpeed() / (base.x / endx)));
                }
            }

            base.x += ((speed / 2) / 3.6) * Math.cos(Math.toRadians(heading - 90));
            base.y += ((speed / 2) / 3.6) * Math.sin(Math.toRadians(heading - 90));
            Log.i("Refresh", "Calculating new params : x = " + base.x + ", y = " + base.y);
        }
        path.updatePoints(base, heading);
    }
    /* Cette fonction vérifie que l'avion est toujours sur l'écran

     */
    public boolean isOutOfScreen(){
        if(base.x > 1920 || base.x < 0 || base.y < 0 || base.y > 1080) {//Coordonées, pas DiP
            Log.i("Cleanup",name+" is currently out of the screen");
            return true;
        }else return false;
    }
}
