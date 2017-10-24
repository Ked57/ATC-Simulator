package ked.atc_simulator.Canvas;


import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Utils.CoordinateConverter;

public class PlanePath extends Path {

    private Point position;
    private Point[] points;
    private float heading;
    private GameActivity context;

    public PlanePath(GameActivity context, Point planePos, float initialHeading){
        super();
        this.context = context;

        this.position = planePos;
        float x = planePos.x;
        float y = planePos.y;

        points = new Point[] {
                new Point(calculateX(x,-5), calculateY(y,-25)),
                new Point(calculateX(x,-5), calculateY(y,25)),
                new Point(calculateX(x,5), calculateY(y,25)),
                new Point(calculateX(x,5), calculateY(y,5)),
                new Point(calculateX(x,45), calculateY(y,5)),
                new Point(calculateX(x,45), calculateY(y,-5)),
                new Point(calculateX(x,5), calculateY(y,-5)),
                new Point(calculateX(x,5), calculateY(y,-25))};

        heading = 0;
        this.drawLines();
        rotate(initialHeading-heading);

    }

    public float calculateX(float x, float offset){
        return CoordinateConverter.GetXDipsFromCoordinate(context,x+offset);
    }

    public float calculateY(float y, float offset){
        return CoordinateConverter.GetYDipsFromCoordinate(context, y+offset);
    }

    public void rotate(float angle){
        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        this.computeBounds(bounds, true);
        mMatrix.postRotate( angle, bounds.centerX(), bounds.centerY());
        this.transform(mMatrix);
        heading += angle;
    }

    public void drawLines(){
        this.moveTo( points[0].x, points[0].y);
        for (int i = 1; i < points.length; i++){
            this.lineTo( points[i].x, points[i].y);
        }
    }
}
