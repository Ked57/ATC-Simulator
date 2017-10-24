package ked.atc_simulator.Entities;


import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.Canvas.TaxiwayPath;
import ked.atc_simulator.GameActivity;

public class Taxiway {

    private TaxiwayPath path;
    private String nom;
    private float x,y,lenght,heading, hOffset, vOffset;

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

    public String getNom() { return nom; }
    public TaxiwayPath getPath() { return path; }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLenght() {
        return lenght;
    }

    public float getHeading() {
        return heading;
    }

    public float gethOffset() {
        return hOffset;
    }

    public float getvOffset() {
        return vOffset;
    }
}
