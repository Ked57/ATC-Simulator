package ked.atc_simulator.Gameplay;


import ked.atc_simulator.Canvas.Point;

public class Route {

    private Point startPoint;
    private int speed, precisionCoefx, precisionCoefy;
    private float heading;
    private Route nextRoute;
    private String name;

    public Route(int speed, float heading, String name, int precisionCoefx, int precisionCoefy){
        this.speed = speed;
        this.heading = heading;
        startPoint = new Point(0,0);
        this.name = name;
        this.precisionCoefx = precisionCoefx;
        this.precisionCoefy = precisionCoefy;
    }

    public float getHeading(){
        return heading;
    }

    public int getPrecisionCoefX() { return precisionCoefx; }

    public int getPrecisionCoefY() { return precisionCoefy; }

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

    public String getName() { return name; }
}
