package ked.atc_simulator;

import android.graphics.Color;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

import ked.atc_simulator.Canvas.CanvasView;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;
import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.State.ArrivingState;
import ked.atc_simulator.State.DepartingState;
import ked.atc_simulator.Utils.CoordinateConverter;

public class GameActivity extends AppCompatActivity {

    private LinearLayout rootLayout, choicesLayout;
    private ConstraintLayout boardLayout;
    private GameMgr gameMgr;
    private CanvasView c;
    private Timer t;
    protected PowerManager.WakeLock mWakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        boardLayout = (ConstraintLayout) findViewById(R.id.Board);
        choicesLayout = (LinearLayout) findViewById(R.id.choicesLayout);

        gameMgr = new GameMgr(this);

        gameMgr.addPlane(new Plane(this,"R328FS",1490,685,270, gameMgr.getCharlie(), new DepartingState(gameMgr)));
        gameMgr.addPlane(new Plane(this,"N851TB",150,950,90, gameMgr.getUpwind(), new ArrivingState(gameMgr)));
        gameMgr.getPlanes().get(0).setBehavior(3);

        gameMgr.getAirport().addRunway(new Runway(this,975,540,1000,270));
        gameMgr.getAirport().addTaxiway(new Taxiway(this,460,555,100,180,"Alpha",5f,-1f));
        gameMgr.getAirport().addTaxiway(new Taxiway(this,1480,555,100,180,"Bravo",5f,-1f));
        gameMgr.getAirport().addTaxiway(new Taxiway(this,970,180,962,270,"Charlie",460f,-1f));

        c = new CanvasView(this,gameMgr);
        boardLayout.addView(c);

        t = new Timer();
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

        setRefreshRate(1);

         /* This code together with the one in onDestroy()
         * will make the screen be always on until this Activity gets destroyed. */
        final PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();


        gameMgr.getSentenceBuilder().buildSentence();
    }

    public void clearChoices(){

        choicesLayout.removeAllViewsInLayout();
        Button backButton = new Button(this);
        backButton.setText(R.string.backButtonText);
        choicesLayout.addView(backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameMgr.getSentenceBuilder().buildSentence();
            }
        });
    }

    public void choicesAddButton(Button button){
        choicesLayout.addView(button);
    }

    public void setRefreshRate(int rate){
        t.cancel();
        t.purge();

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

        }, 0, 1000/rate);
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

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        t.cancel();
        super.onDestroy();
    }

    public GameMgr getGameMgr(){
        return gameMgr;
    }

    public void setSentence(String sentence){
        c.setSentence(sentence);
    }
}
