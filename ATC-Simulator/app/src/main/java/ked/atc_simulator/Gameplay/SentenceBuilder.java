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
    private int stage;
    private String sentence;
    private GameActivity gameActivity;

    public SentenceBuilder(GameMgr gameMgr){
        this.gameMgr = gameMgr;
        sentence = "";
        stage = 1;
        gameActivity = gameMgr.getContext();
    }

    public void buildSentence(){

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
                    gameActivity.clearChoices();
                    setStage(2);
                }
            });
        }
        /*while(stage <= 2){
        }//On attend le passage à l'étape 2*/

        Button buttonTaxi = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonTaxi);
        buttonTaxi.setText(R.string.sentence_taxi_cleared);
        buttonTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildTaxiClearance();
            }
        });

        Button buttonTakeoff = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonTakeoff);
        buttonTakeoff.setText(R.string.sentence_takeoff_cleared);
        buttonTakeoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildTakeOffClearance();
            }
        });

        Button buttonLanding = new Button(gameActivity);
        gameActivity.choicesAddButton(buttonLanding);
        buttonLanding.setText(R.string.sentence_landing_cleared);
        buttonLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildLandingClearance();
            }
        });


    }

    public void buildTaxiClearance(){
        gameActivity.clearChoices();

    }

    public void buildTakeOffClearance(){
        gameActivity.clearChoices();
    }

    public void buildLandingClearance(){
        gameActivity.clearChoices();
    }

    public void setStage(int stage){ this.stage = stage;}
}
