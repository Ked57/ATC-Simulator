package ked.atc_simulator.Gameplay;


public class RunwayRoute extends Route {

    private float lenght;

    public RunwayRoute(int speed, float heading, Route nextRoute, float lenght){
        super(speed,heading,nextRoute);
        this.lenght = lenght;
    }

    public float getLenght() { return lenght; }
}
