package ked.atc_simulator;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import ked.atc_simulator.Canvas.CanvasView;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;
import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.Utils.CoordinateConverter;

public class GameActivity extends AppCompatActivity {

    private LinearLayout rootLayout;
    private ConstraintLayout boardLayout;
    private GameMgr gameMgr;
    private CanvasView c;
    private Timer t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        boardLayout = (ConstraintLayout) findViewById(R.id.Board);

        gameMgr = new GameMgr();

        gameMgr.addPlane(new Plane(this,200,200,0));
        gameMgr.addPlane(new Plane(this,400,400,58));

        gameMgr.getAirport().addRunway(new Runway(this,975,540,1000,270));
        gameMgr.getAirport().addTaxiway(new Taxiway(this,460,555,100,180,"Alpha",5f,-1f));
        gameMgr.getAirport().addTaxiway(new Taxiway(this,1480,555,100,180,"Bravo",5f,-1f));
        gameMgr.getAirport().addTaxiway(new Taxiway(this,970,180,962,270,"Charlie",460f,-1f));

        c = new CanvasView(this,gameMgr);
        boardLayout.addView(c);

        t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        c.invalidate();
                        Log.i("Refresh","Refreshing");
                    }

                });
            }

        }, 0, 1000);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            rootLayout.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public GameMgr getGameMgr(){
        return gameMgr;
    }
}
