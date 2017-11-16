package ked.atc_simulator.Gameplay;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.R;
import ked.atc_simulator.State.ArrivingState;
import ked.atc_simulator.State.DepartingState;

/**
 * Classe permettant de construire les phrases que le joueur dit aux avions
 */

public class SentenceBuilder {

    private GameMgr gameMgr;
    private String sentence;
    private GameActivity gameActivity;
    private Plane currPlane;
    private int behavior;

    /**
     * Constructeur de la classe SentenceBuilder
     * @param gameMgr
     */
    public SentenceBuilder(GameMgr gameMgr){
        this.gameMgr = gameMgr;
        sentence = "";
        gameActivity = gameMgr.getContext();
        behavior = 3;
    }

    public boolean isSentenceEmpty(){
        if(sentence.equals(""))
            return true;
        else return false;
    }

    /**
     * Permet de commencer a construire une phrase
     */
    public void buildSentence(){
        sentence = "";
        ArrayList<Plane> planes = gameMgr.getPlanes();
        Log.i("Sentence","Got "+planes.size()+" planes");
        gameActivity.clearChoices();
        for(Plane p : planes){
            Button button = new Button(gameActivity);
            button.setText(p.getName());
            gameActivity.choicesAddButton(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sentence += ((Button)v).getText().toString();
                    currPlane = gameMgr.getPlaneByName(sentence);
                    if(currPlane.equals(gameMgr.getContext().emptyPlane)){
                        Log.i("Sentence","currPlane is null");
                        return;
                    }
                    sentence += " ";
                    gameActivity.clearChoices();

                    Button buttonTaxi = new Button(gameActivity);
                    gameActivity.choicesAddButton(buttonTaxi);
                    buttonTaxi.setText(R.string.sentence_taxi_cleared);
                    buttonTaxi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sentence += gameActivity.getResources().getString(R.string.sentence_taxi_cleared)+" ";
                            gameActivity.setSentence(sentence);
                            buildTaxiClearance();
                        }
                    });

                    Button buttonTakeoff = new Button(gameActivity);
                    gameActivity.choicesAddButton(buttonTakeoff);
                    buttonTakeoff.setText(R.string.sentence_takeoff_cleared);
                    buttonTakeoff.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sentence += gameActivity.getResources().getString(R.string.sentence_takeoff_cleared)+" ";
                            gameActivity.setSentence(sentence);
                            buildTakeOffClearance();
                        }
                    });

                    Button buttonLanding = new Button(gameActivity);
                    gameActivity.choicesAddButton(buttonLanding);
                    buttonLanding.setText(R.string.sentence_landing_cleared);
                    buttonLanding.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sentence += gameActivity.getResources().getString(R.string.sentence_landing_cleared)+" ";
                            gameActivity.setSentence(sentence);
                            buildLandingClearance();
                        }
                    });
                    Button buttonStop = new Button(gameActivity);
                    gameActivity.choicesAddButton(buttonStop);
                    if(currPlane.getBehavior() != 3)
                        buttonStop.setText(R.string.sentence_stop);
                    else
                        buttonStop.setText(R.string.sentence_continue);
                    buttonStop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(currPlane.getBehavior() != 3) {
                                sentence += gameActivity.getResources().getString(R.string.sentence_stop) + " ";
                                currPlane.setBehavior(3);//Stop
                            }
                            else {
                                sentence += gameActivity.getResources().getString(R.string.sentence_continue)+" ";
                                currPlane.setBehavior(2);//Continue until runway
                            }
                            gameActivity.setSentence(sentence);
                            buildSentence();
                        }
                    });
                }
            });
        }
    }

    /**
     * Permet de construire l'autorisation au taxi
     */
    public void buildTaxiClearance(){

        gameActivity.clearChoices();
        Button buttonHoldShort = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonHoldShort);
        buttonHoldShort.setText(R.string.sentence_taxi_holdshort);
        buttonHoldShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior = 2; // Hold short
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_holdshort)+" ";
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_via)+" ";
                gameActivity.clearChoices();
                gameActivity.setSentence(sentence);
                buildAlphaButton();
                buildBravoButton();
                buildCharlieButton();
            }
        });
    }

    /**
     * Permet de construire l'autorisation au décollage
     */
    public void buildTakeOffClearance(){
        Log.i("Sentence","take off sentence");

        gameActivity.clearChoices();
        Button buttonRunway27 = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonRunway27);
        buttonRunway27.setText(R.string.sentence_takeoff_rw27);
        buttonRunway27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPlane.setBehavior(0);// Normal
                currPlane.setPlaneState(new DepartingState(gameMgr));
                sentence += gameActivity.getResources().getString(R.string.sentence_takeoff_rw27);
                gameActivity.clearChoices();
                Log.i("Sentence","the sentence is : "+sentence);
                gameActivity.setSentence(sentence);
                buildSentence(); // Start over sentence building
            }
        });
    }

    /**
     * Permet de construire l'autorisation à l'atterissage
     */
    public void buildLandingClearance(){

        gameActivity.clearChoices();
        Button buttonRunway27 = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonRunway27);
        buttonRunway27.setText(R.string.sentence_takeoff_rw27);
        buttonRunway27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPlane.setBehavior(0);// Normal
                currPlane.setPlaneState(new ArrivingState(gameMgr));
                sentence += gameActivity.getResources().getString(R.string.sentence_takeoff_rw27);
                gameActivity.clearChoices();
                Log.i("Sentence","the sentence is : "+sentence);
                gameActivity.setSentence(sentence);
                buildSentence(); // Start over sentence building
            }
        });
    }

    /**
     * Construit le bouton Alpha
     */
    public void buildAlphaButton(){
        Button buttonAlpha = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonAlpha);
        buttonAlpha.setText(R.string.sentence_taxi_alpha);
        buttonAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.clearChoices();
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_alpha)+" ";
                Log.i("Sentence","the sentence is : "+sentence);
                gameActivity.setSentence(sentence);
                currPlane.setBehavior(behavior);
                buildSentence();
            }
        });
    }

    /**
     * Construit le bouton Bravo
     */
    public void buildBravoButton(){
        Button buttonBravo = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonBravo);
        buttonBravo.setText(R.string.sentence_taxi_bravo);
        buttonBravo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.clearChoices();
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_bravo)+" ";
                gameActivity.setSentence(sentence);
                buildCharlieButton();
            }
        });
    }

    /**
     * Construit le bouton Charlie
     */
    public void buildCharlieButton(){
        Button buttonCharlie = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonCharlie);
        buttonCharlie.setText(R.string.sentence_taxi_charlie);
        buttonCharlie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.clearChoices();
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_charlie)+" ";
                gameActivity.setSentence(sentence);
                buildAlphaButton();
            }
        });
    }

}
