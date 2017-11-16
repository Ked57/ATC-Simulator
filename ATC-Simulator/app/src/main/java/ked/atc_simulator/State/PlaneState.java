package ked.atc_simulator.State;

import ked.atc_simulator.Gameplay.Route;

/**
 * Classe mère basée sur le StatePattern
 * Le code utilise ces classes de façon à fonctionner différemment selon
 * le type de l'objet qu'il manipule
 */

public abstract class PlaneState {

    private String name;

    /**
     * Constructeur de la classe PlaneState
     * @param name
     */
    public PlaneState(String name){
        this.name = name;
    }

    /**
     * Getter du nom de l'état
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Getter de l'action à effectuer à la route "base"
     * @return
     */
    public Route baseAction(){
        return null;
    }

    /**
     * Getter de l'action à effectuer à la route "crosswindRN"
     * @return
     */
    public Route crosswindRNAction(){
        return null;
    }
}
