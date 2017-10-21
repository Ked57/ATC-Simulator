package ked.atc_simulator.Canvas;


import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

public class PlanePath extends Path {

    private Point position;
    private Point[] points;
    private float heading;

    public PlanePath(Point planePos, float initialHeading){
        super();

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
        return x+offset;
    }

    public float calculateY(float y, float offset){
        return y+offset;
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
