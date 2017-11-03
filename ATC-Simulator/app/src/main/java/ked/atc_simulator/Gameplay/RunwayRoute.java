package ked.atc_simulator.Gameplay;


public class RunwayRoute extends Route {

    private float lenght;

    public RunwayRoute(int speed, float heading, float lenght, String name, int precisionCoefx, int precisionCoefy){
        super(speed,heading, name, precisionCoefx, precisionCoefy);
        this.lenght = lenght;
    }

    public float getLenght() { return lenght; }
}
