package ked.atc_simulator.Entities;


import ked.atc_simulator.Canvas.Point;

import ked.atc_simulator.Canvas.RunwayPath;
import ked.atc_simulator.GameActivity;

public class Runway {

    private RunwayPath path;
    private int number;

    public Runway(GameActivity context, float x, float y, float lenght, float heading){

        number = ((int)heading)/10;
        path = new RunwayPath(context, new Point(x,y),lenght,heading);

    }

    public int getNumber() { return number; }

    public RunwayPath getPath(){ return path; }
}
