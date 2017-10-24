package ked.atc_simulator.Entities;


import android.util.Log;

import ked.atc_simulator.Canvas.PlanePath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;

public class Plane {

    private PlanePath path;
    private int alt,speed; // Imperial system (feet, knots)
    private float heading;
    private Point base;

    public Plane(GameActivity context, float x, float y, float heading){
        base = new Point(x,y);
        path = new PlanePath(context, base, heading);
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

    public void calculateNewParams(){
        base.x += (speed/3.6)*Math.cos(heading);
        base.y += (speed/3.6)*Math.sin(heading);
        Log.i("Refresh","Calculating new params : x = "+base.x+", y = "+base.y);
        path.updatePoints(base);
    }
}
