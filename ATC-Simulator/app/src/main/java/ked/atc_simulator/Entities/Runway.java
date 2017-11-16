package ked.atc_simulator.Entities;


import ked.atc_simulator.Canvas.Point;

import ked.atc_simulator.Canvas.RunwayPath;
import ked.atc_simulator.GameActivity;

/**
 * Cette classe contient les informations d'une piste
 * Il n'y a pas de Setter définit car les informations ne sont pas
 * sensées changer
 */
public class Runway {

    private RunwayPath path;
    private int number;

    /**
     * Constructeur de la classe Runway
     * @param context
     * @param x
     * @param y
     * @param lenght - taille de la piste
     * @param heading - cap de la piste
     */
    public Runway(GameActivity context, float x, float y, float lenght, float heading){

        number = ((int)heading)/10;// Le numéro de piste correspond au cap / 10. Pour une piste au cap 241 : Piste 24
        path = new RunwayPath(context, new Point(x,y),lenght,heading);

    }

    /**
     * Getter du numéro de piste
     * @return
     */
    public int getNumber() { return number; }

    /**
     * Getter du path de la piste
     * @return
     */
    public RunwayPath getPath(){ return path; }
}
