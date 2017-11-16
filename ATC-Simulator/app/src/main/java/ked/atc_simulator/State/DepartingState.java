package ked.atc_simulator.State;

import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.Gameplay.Route;

/**
 * Classe fille de PlaneState basée sur le StatePattern
 * Le code utilise ces classes de façon à fonctionner différemment selon
 * le type de l'objet qu'il manipule
 */

public class DepartingState extends PlaneState {

    private GameMgr gameMgr;

    /**
     * Constructeur de la classe DepartingState
     * @param gameMgr
     */
    public DepartingState(GameMgr gameMgr){
        super("DepartingState");
        this.gameMgr = gameMgr;
    }

    /**
     * Getter de l'action à effectuer à la route "base"
     * @return
     */
    public Route baseAction(){
        return gameMgr.getUpwind();
    }

    /**
     * Getter de l'action à effectuer à la route "crosswindRN"
     * @return
     */
    public Route crosswindRNAction(){
        return new Route(175, 90, "StraitEastDeparture", 3, 3);
    }


}
