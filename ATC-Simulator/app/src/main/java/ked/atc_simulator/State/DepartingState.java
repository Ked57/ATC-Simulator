package ked.atc_simulator.State;

import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.Gameplay.Route;

/**
 * Created by Clement on 03/11/2017.
 */

public class DepartingState extends PlaneState {

    private GameMgr gameMgr;

    public DepartingState(GameMgr gameMgr){
        super("DepartingState");
        this.gameMgr = gameMgr;
    }

    public Route baseAction(){
        return gameMgr.getUpwind();
    }

    public Route crosswindRNAction(){
        return new Route(175, 90, "StraitEstDeparture", 3, 3);
    }


}
