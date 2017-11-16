package ked.atc_simulator.Gameplay;

/**
 * Classe fille de la classe route, permet de gérer la vitesse sur les pistes
 */
public class RunwayRoute extends Route {

    private float lenght;

    /**
     * Constrcuteur de la classe RunwayRoute
     * @param speed
     * @param heading - cap
     * @param lenght - la taille de la route
     * @param name
     * @param precisionCoefx - Le coefficient de précision en X pour la détection d'entrée sur la route
     * @param precisionCoefy - Le coefficient e précision en Y pour la détection d'entrée sur la route
     */
    public RunwayRoute(int speed, float heading, float lenght, String name, int precisionCoefx, int precisionCoefy, Route nextRoute){
        super(speed,heading, name, precisionCoefx, precisionCoefy, nextRoute);
        this.lenght = lenght;
    }

    /**
     * Getter de la taille de la route
     * @return
     */
    public float getLenght() { return lenght; }
}
