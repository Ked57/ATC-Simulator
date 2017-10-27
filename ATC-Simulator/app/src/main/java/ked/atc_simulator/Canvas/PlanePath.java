package ked.atc_simulator.Canvas;


import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Utils.CoordinateConverter;

public class PlanePath extends Path {

    private ArrayList<Point> points;
    private GameActivity context;
    private float heading;

    public PlanePath(GameActivity context, Point planePos, float initialHeading){
        this.context = context;
        this.heading = initialHeading;

        points = new ArrayList<>();
        updatePoints(planePos ,heading);

        Log.i("Heading",""+initialHeading);
    }

    public void updatePoints(Point planePos, float heading){
        this.heading = heading;

        points.clear();
        points.add(new Point(planePos.x-25,planePos.y+5));
        points.add(new Point(planePos.x-25, planePos.y-5));
        points.add(new Point(planePos.x-5, planePos.y-5));
        points.add(new Point(planePos.x-5, planePos.y-45));
        points.add(new Point(planePos.x+5, planePos.y-45));
        points.add(new Point(planePos.x+5, planePos.y-5));
        points.add(new Point(planePos.x+25,planePos.y-5));
        points.add(new Point(planePos.x+25,planePos.y+5));

        this.moveTo( CoordinateConverter.GetXDipsFromCoordinate(context,points.get(0).x), CoordinateConverter.GetYDipsFromCoordinate(context,points.get(0).y));
        for (int i = 1; i < points.size(); i++){
            this.lineTo( CoordinateConverter.GetXDipsFromCoordinate(context,points.get(i).x), CoordinateConverter.GetYDipsFromCoordinate(context,points.get(i).y));
        }
        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        this.computeBounds(bounds, true);
        mMatrix.postRotate( heading, bounds.centerX(), bounds.centerY());
        this.transform(mMatrix);
    }

}
