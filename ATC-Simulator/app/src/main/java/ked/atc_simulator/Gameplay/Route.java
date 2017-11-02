package ked.atc_simulator.Gameplay;


import java.util.ArrayList;

import ked.atc_simulator.Canvas.Point;

public class Route {

    private Point startPoint;
    private int speed;
    private float heading;
    private Route nextRoute;

    public Route(int speed, float heading, Route nextRoute){
        this.speed = speed;
        this.heading = heading;
        startPoint = new Point(0,0);
        this.nextRoute = nextRoute;
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

    public Route getNextRoute() { return nextRoute; }
}
