package ked.atc_simulator.Entities;

import android.content.Context;

import ked.atc_simulator.Canvas.ParkingPath;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;

/**
 * Classe contenant les informations relatives aux parkings
 */

public class Parking {

    private ParkingPath path;
    private String name;
    private Point base;
    private float hOffSet, vOffSet,lenght,heading;

    public Parking(GameActivity context, String name, Point base, float lenght, float heading, float hOffSet, float vOffSet) {
        this.path = new ParkingPath(context,base,lenght,heading);
        this.name = name;
        this.base = base;
        this.lenght = lenght;
        this.hOffSet = hOffSet;
        this.vOffSet = vOffSet;
    }

    public ParkingPath getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Point getBase() {
        return base;
    }

    public float gethOffSet() {
        return hOffSet;
    }

    public float getvOffSet() {
        return vOffSet;
    }

    public float getLenght() {
        return lenght;
    }

    public float getHeading() {
        return heading;
    }
}
