package ked.atc_simulator.Entities;


import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;

public class Plane {

    private PlanePath path;
    private int alt,speed;
    private float heading; // Imperial system (feet, knots)

    public Plane(float x, float y, float heading){
        path = new PlanePath(new Point(x,y), heading);
        alt = 5000;
        speed = 150;
        this.heading = heading;
    }

    public PlanePath getPath(){ return path; }

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
}
