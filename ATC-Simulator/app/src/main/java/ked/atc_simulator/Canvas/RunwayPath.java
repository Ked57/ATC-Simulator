package ked.atc_simulator.Canvas;


import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

public class RunwayPath extends Path {

    public RunwayPath(Point base, float lenght, float heading){

        Point[] points= {new Point(base.x-20,base.y-lenght/2),
                new Point(base.x-20, base.y+lenght/2),
                new Point(base.x+20,base.y+lenght/2),
                new Point(base.x+20,base.y-lenght/2)
        };

        this.moveTo( points[0].x, points[0].y);
        for (int i = 1; i < points.length; i++){
            this.lineTo( points[i].x, points[i].y);
        }

        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        this.computeBounds(bounds, true);
        mMatrix.postRotate( heading, bounds.centerX(), bounds.centerY());
        this.transform(mMatrix);

    }

}
