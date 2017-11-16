package ked.atc_simulator.Canvas;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.ArrayList;

import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Utils.CoordinateConverter;

/**
 * Extension de la classe path gérant le dessin sur les canvas
 * Celle ci gère les parkings avec des fonctions qui lui sont propres
 * Il n'y a pas besoin d'autres fonctions car la position de la piste
 * n'est jamais modifiée
 */

public class ParkingPath extends Path {

        private ArrayList<Point> points;


    public ParkingPath(GameActivity context, Point base, float lenght, float heading){
        points = new ArrayList<>();
        points.add(new Point(base.x-10,base.y-lenght/2));
        points.add(new Point(base.x-10, base.y+lenght/2));
        points.add(new Point(base.x+10,base.y+lenght/2));
        points.add(new Point(base.x+10,base.y-lenght/2));


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
