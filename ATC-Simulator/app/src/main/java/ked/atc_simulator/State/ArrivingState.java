package ked.atc_simulator.State;

import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.Gameplay.Route;

/**
 * Classe fille de PlaneState basée sur le StatePattern
 * Le code utilise ces classes de façon à fonctionner différemment selon
 * le type de l'objet qu'il manipule
 */

public class ArrivingState extends PlaneState {

    private GameMgr gameMgr;

    /**
     * Constructeur de la classe ArrivingState
     * @param gameMgr
     */
    public ArrivingState(GameMgr gameMgr){
        super("ArrivingState");
        this.gameMgr = gameMgr;
    }

    /**
     * Getter de l'action à effectuer à la route "base"
     * @return
     */
    @Override
    public Route baseAction(){
        return gameMgr.getFinale();
    }

    /**
     * Getter de l'action à effectuer à la route "crosswindRN"
     * @return
     */
    @Override
    public Route crosswindRNAction(){
        return gameMgr.getDownwind();
    }
}
