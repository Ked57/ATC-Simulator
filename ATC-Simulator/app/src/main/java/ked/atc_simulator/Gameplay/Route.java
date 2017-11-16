package ked.atc_simulator.Gameplay;


import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.GameActivity;

/**
 * Cette classe contient toutes les informations relatives aux routes
 */
public class Route {

    private Point startPoint;
    private int speed, precisionCoefx, precisionCoefy;
    private float heading;
    private Route nextRoute;
    private String name;

    /**
     * Constructeur de la classe Route
     * @param speed
     * @param heading - cap
     * @param name
     * @param precisionCoefx - Le coefficient de précision en X pour la détection d'entrée sur la route
     * @param precisionCoefy - Le coefficient e précision en Y pour la détection d'entrée sur la route
     */
    public Route(int speed, float heading, String name, int precisionCoefx, int precisionCoefy,Route nextRoute){
        this.speed = speed;
        this.heading = heading;
        startPoint = new Point(0,0);
        this.name = name;
        this.precisionCoefx = precisionCoefx;
        this.precisionCoefy = precisionCoefy;
        this.nextRoute = nextRoute;
    }

    /**
     * Constructueur de la classe route pour créer une route vide
     */
    public Route(){}

    /**
     * Getter pour le cap de la oute
     * @return
     */
    public float getHeading(){
        return heading;
    }

    /**
     * Getter pour le coefficient de précision en X
     * @return
     */
    public int getPrecisionCoefX() { return precisionCoefx; }

    /**
     * Getter pour le coefficient de précision en Y
     * @return
     */
    public int getPrecisionCoefY() { return precisionCoefy; }

    /**
     * Getter pour la vitesse qu'un avion doit emprunter sur la route
     * @return
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * Getter du point de départ de la route
     * @return
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Setter du point de départ de la route
     * @param startPoint
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Setter de la route suivante que doit emprunter l'avion une fois arrivé au bout
     * @param route
     */
    public void setNextRoute(Route route){ nextRoute = route;}

    /**
     * Getter de la route suivante que doit emprunter l'avion une fois arrivé au bout
     * @return
     */
    public Route getNextRoute() { return nextRoute; }

    /**
     * Getter du nom de l'avion
     * @return
     */
    public String getName() { return name; }
}
