package ked.atc_simulator.Canvas;


import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ked.atc_simulator.GameActivity;
import ked.atc_simulator.Utils.CoordinateConverter;

/**
 * Extension de la classe path gérant le dessin sur les canvas
 * Celle ci gère les avions avec des fonctions qui lui sont propres
 */
public class PlanePath extends Path {

    private ArrayList<Point> points;
    private GameActivity context;
    private float heading; // Le cap

    /**
     * Constructeur de la classe PlanePath
     * @param context
     * @param planePos
     * @param initialHeading
     */
    public PlanePath(GameActivity context, Point planePos, float initialHeading){
        this.context = context;
        this.heading = initialHeading;

        points = new ArrayList<>();
        updatePoints(planePos ,heading);

        Log.i("Heading",""+initialHeading);
    }

    /**
     *  Cette fonction permet de mettre à jour les différents points de l'avion et son cap
     * @param planePos - De type Point (avec une position x et y)
     * @param heading - Le cap de l'avion pour le dessiner dans le bon sens
     */
    public void updatePoints(Point planePos, float heading){
        this.heading = heading;

        points.clear(); //On vide la liste de points
        /*Puis on ajoute les nouveaux, chaque point est calculé en fonction de la position de l'avion
            qui se situe a peu prêt à l'intersection de ses deux rectangles
        */
        points.add(new Point(planePos.x-25,planePos.y+5));
        points.add(new Point(planePos.x-25, planePos.y-5));
        points.add(new Point(planePos.x-5, planePos.y-5));
        points.add(new Point(planePos.x-5, planePos.y-45));
        points.add(new Point(planePos.x+5, planePos.y-45));
        points.add(new Point(planePos.x+5, planePos.y-5));
        points.add(new Point(planePos.x+25,planePos.y-5));
        points.add(new Point(planePos.x+25,planePos.y+5));

        // Move to permet de reset le path
        //CoordinateConverter est une classe statique qui me permet de gérer toutes les tailles d'écrans, plus de détails dans la classe en question
        this.moveTo( CoordinateConverter.GetXDipsFromCoordinate(context,points.get(0).x), CoordinateConverter.GetYDipsFromCoordinate(context,points.get(0).y));
        for (int i = 1; i < points.size(); i++){
            //lineTo permet de dessiner une ligne entre le dernier point et celui passé en paramètre
            this.lineTo( CoordinateConverter.GetXDipsFromCoordinate(context,points.get(i).x), CoordinateConverter.GetYDipsFromCoordinate(context,points.get(i).y));
        }
        //Cette manipulation permet de tourner le path en fonction du cap
        Matrix mMatrix = new Matrix();
        RectF bounds = new RectF();
        this.computeBounds(bounds, true);
        mMatrix.postRotate( heading, bounds.centerX(), bounds.centerY());
        this.transform(mMatrix);
    }

    /**
     * Retourne le point de départ du path
     * @return
     */
    public Point getStartPoint(){
        return points.get(0);
    }
}
