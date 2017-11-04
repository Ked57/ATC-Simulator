package ked.atc_simulator.State;

import android.util.Log;

import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.Gameplay.Route;

/**
 * Created by Clement on 03/11/2017.
 */

public class ArrivingState extends PlaneState {

    private GameMgr gameMgr;

    public ArrivingState(GameMgr gameMgr){
        this.gameMgr = gameMgr;
    }

    @Override
    public Route baseAction(){
        Log.i("Refresh","ArrivingState baseAction "+gameMgr.getFinale().toString());
        return gameMgr.getFinale();
    }

    @Override
    public Route crosswindRNAction(){
        return gameMgr.getDownwind();
    }
}