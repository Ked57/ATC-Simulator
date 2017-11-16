package ked.atc_simulator.Gameplay;

import android.graphics.Point;

/**
 * Classe fille de la classe route, elle rajoute un point de fin à la route. Arrivé à ce point l'avion sera supprimé
 */

public class ParkingRoute extends Route {

    private Point endPoint;

    /**
     * Constructeur de la classe ParkingRoute
     * @param speed
     * @param heading
     * @param name
     * @param precisionCoefx
     * @param precisionCoefy
     */
    public ParkingRoute(int speed, float heading, String name, int precisionCoefx, int precisionCoefy, Route nextRoute) {
        super(speed, heading, name, precisionCoefx, precisionCoefy, nextRoute);
        endPoint = new Point(-1,-1); // Valeurs temporaires par defaut
    }

    /**
     * Constructeur vide de la classe ParkingRoute, permet d'avoir un object emptyParkingRoute dans la classe Plane
     * quand l'avion ne doit pas aller au parking et donc éviter les PointerNullException car c'est plus facile à comparer
     */
    public ParkingRoute() {super(0,0,"",0,0,null);}

    /**
     * Setter du Point endPoint
     * @param x
     * @param y
     */
    public void setEndPoint(int x, int y){
        endPoint.x = x;
        endPoint.y = y;
    }

    /**
     * Getter du point endPoint
     * @return
     */
    public Point getEndPoint() { return endPoint; }

}
