package ked.atc_simulator.Gameplay;


import java.util.ArrayList;

import ked.atc_simulator.Canvas.Point;

public class Route {

    private Point startPoint;
    private int speed;
    private float heading;
    private Route nextRoute;

    public Route(int speed, float heading){
        this.speed = speed;
        this.heading = heading;
        startPoint = new Point(0,0);
    }

    public float getHeading(){
        return heading;
    }

    public int getSpeed(){
        return speed;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public void setNextRoute(Route route){ nextRoute = route;}

    public Route getNextRoute() { return nextRoute; }
}
