package ked.atc_simulator.Canvas;

/* Le centre d'un point : 5,25 */

import android.util.Log;


public class PlaneCanvas {

    private Point[] points;

    public PlaneCanvas(Point PlanePos, double heading){
        double x = PlanePos.x;
        double y = PlanePos.y;
        heading = Math.toRadians(heading);

        points = new Point[] {
                new Point(calculateX(x,-5,y,-25,heading), calculateY(x,-5,y,-25,heading)),
                new Point(calculateX(x,-5,y,25,heading), calculateY(x,-5,y,25,heading)),
                new Point(calculateX(x,5,y,25,heading), calculateY(x,5,y,25,heading)),
                new Point(calculateX(x,5,y,5,heading), calculateY(x,5,y,5,heading)),
                new Point(calculateX(x,45,y,5,heading), calculateY(x,45,y,5,heading)),
                new Point(calculateX(x,45,y,-5,heading), calculateY(x,45,y,-5,heading)),
                new Point(calculateX(x,5,y,-5,heading), calculateY(x,5,y,-5,heading)),
                new Point(calculateX(x,5,y,-25,heading), calculateY(x,5,y,-25,heading))};

        /*points = new Point[] { new Point((x-5)+(-5)*Math.cos(heading), (y-25)+(-25)*Math.sin(heading)),
                new Point((x-5)+(-5)*Math.cos(heading), (y+25)+25*Math.sin(heading)),
                new Point((x+5)+5*Math.cos(heading), (y+25)+25*Math.sin(heading)),
                new Point((x+5)+5*Math.cos(heading), (y+5)+5*Math.sin(heading)),
                new Point((x+45)+45*Math.cos(heading), (y+5)+5*Math.sin(heading)),
                new Point((x+45)+45*Math.cos(heading), (y-5)+(-5)*Math.sin(heading)),
                new Point((x+5)+5*Math.cos(heading), (y-5)+(-5)*Math.sin(heading)),
                new Point((x+5)+5*Math.cos(heading), (y-25)+(-25)*Math.sin(heading))};*/
    }

    public Point[] getPoints() { return points;}

    public double calculateX(double x, double dx,double y, double dy, double heading){
        //return (x+dx)*Math.cos(heading) - (y+dy)*Math.sin(heading);
        return (x+dx)+dx*Math.cos(heading);
    }
    public double calculateY(double x, double dx,double y, double dy, double heading){
        //return (x+dx)*Math.sin(heading) + (y+dy)*Math.cos(heading);
        return (y+dy)+dy*Math.sin(heading);
    }
}
