package ked.atc_simulator.Gameplay;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.R;

/**
 * Will build the sentences
 */

public class SentenceBuilder {

    private GameMgr gameMgr;
    private String sentence;
    private GameActivity gameActivity;
    private Plane currPlane;
    private int behavior;

    public SentenceBuilder(GameMgr gameMgr){
        this.gameMgr = gameMgr;
        sentence = "";
        gameActivity = gameMgr.getContext();
        behavior = 3;
    }

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
                    if(currPlane.equals(gameMgr.emptyPlane)){
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
                            buildLandingClearance();
                        }
                    });
                }
            });
        }
    }

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
                buildAlphaButton();
                buildBravoButton();
                buildCharlieButton();
            }
        });
    }

    public void buildTakeOffClearance(){

        gameActivity.clearChoices();

    }

    public void buildLandingClearance(){

        gameActivity.clearChoices();
    }

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
                currPlane.setBehavior(behavior);
                buildSentence();
            }
        });
    }

    public void buildBravoButton(){
        Button buttonBravo = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonBravo);
        buttonBravo.setText(R.string.sentence_taxi_bravo);
        buttonBravo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.clearChoices();
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_bravo)+" ";
                buildCharlieButton();
            }
        });
    }

    public void buildCharlieButton(){
        Button buttonCharlie = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonCharlie);
        buttonCharlie.setText(R.string.sentence_taxi_charlie);
        buttonCharlie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.clearChoices();
                sentence += gameActivity.getResources().getString(R.string.sentence_taxi_charlie)+" ";
                buildAlphaButton();
            }
        });
    }

}
