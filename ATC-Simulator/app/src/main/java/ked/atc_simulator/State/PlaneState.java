package ked.atc_simulator.State;

import android.util.Log;

import ked.atc_simulator.Gameplay.Route;

/**
 * Designed to work according to the state pattern
 */

public abstract class PlaneState {

    private String name;

    public PlaneState(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Route baseAction(){
        return null;
    }

    public Route crosswindRNAction(){
        return null;
    }
}
