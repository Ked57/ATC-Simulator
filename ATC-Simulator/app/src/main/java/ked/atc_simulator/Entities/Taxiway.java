package ked.atc_simulator.Entities;


import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.Canvas.TaxiwayPath;
import ked.atc_simulator.GameActivity;

/**
 * Cette classe contient les informations relatives à un Taxiway
 * Il n'y a pas de Setter car les informations ne sont pas sensées changer
 */
public class Taxiway {

    private TaxiwayPath path;
    private String nom;
    private float x,y,lenght,heading, hOffset, vOffset;

    /**
     * Constructeur de la classe Taxiway
     * @param context
     * @param x - Coordonnée X du point de base du taxiway
     * @param y - Coordonnée Y du point de base du taxiway
     * @param lenght - taille
     * @param heading- cap
     * @param nom
     * @param hOffset - Le décalage horizontal utilisé pour écrire le nom du taxiway
     * @param vOffset - Le décalage vertical utilisé pour écrire le nom du taxiway
     */
    public Taxiway(GameActivity context, float x, float y, float lenght, float heading, String nom, float hOffset, float vOffset){
        path = new TaxiwayPath(context,new Point(x,y),lenght,heading);
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.lenght = lenght;
        this.heading = heading;
        this.hOffset = hOffset;
        this.vOffset = vOffset;
    }

    /**
     * Getter pour le nom du taxiway
     * @return
     */
    public String getNom() { return nom; }

    /**
     * Getter pour le path du taxiway
     * @return
     */
    public TaxiwayPath getPath() { return path; }

    /**
     * Getter pour la coordonnée X du taxiway
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     * Getter pour la coordonnée Y du taxiway
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     * Getter pour le décalage horizontal de l'écriture du nom du taxiway
     * @return
     */
    public float gethOffset() {
        return hOffset;
    }

    /**
     * Getter pour le décalage vertical de l'écriture du nom du taxiway
     * @return
     */
    public float getvOffset() {
        return vOffset;
    }
}
