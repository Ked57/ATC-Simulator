package ked.atc_simulator;

import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ked.atc_simulator.Canvas.CanvasView;
import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.Entities.Parking;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;
import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.Gameplay.ParkingRoute;
import ked.atc_simulator.Gameplay.Route;
import ked.atc_simulator.State.ArrivingState;
import ked.atc_simulator.State.DepartingState;
import ked.atc_simulator.State.PlaneState;
import ked.atc_simulator.Utils.XMLParser;

public class GameActivity extends AppCompatActivity {

    private LinearLayout rootLayout, choicesLayout;
    private ConstraintLayout boardLayout;
    private GameMgr gameMgr;
    private CanvasView c;
    private Timer t;
    protected PowerManager.WakeLock mWakeLock;
    private XMLParser parser;

    public final Plane emptyPlane = new Plane();
    public final ParkingRoute emptyParkingRoute = new ParkingRoute();
    public final Route emptyRoute = new Route();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);
        boardLayout = (ConstraintLayout) findViewById(R.id.Board);
        choicesLayout = (LinearLayout) findViewById(R.id.choicesLayout);

        gameMgr = new GameMgr(this);

        parser = new XMLParser();

        /* On récupère les avions sauvegardés */
        File save = new File(getApplicationContext().getFilesDir()+"/save.xml");
        Log.i("Parser","File Dir:"+getApplicationContext().getFilesDir()+"/save.xml");
        if(save.exists()){
            InputStream resultStream = null;
            try{
                resultStream = new FileInputStream(save);
                Log.i("Parser","File Dir:"+save.getAbsolutePath());
                List<XMLParser.Entry>entries = parser.parse(resultStream);
                Log.i("Parser","EntryNumber : "+entries.size());
                for(XMLParser.Entry entry : entries){
                    Log.i("Parser","EntryName : "+entry.nom);
                    Route route = gameMgr.getRouteByName(entry.route);
                    PlaneState state = gameMgr.getPlaneStateByName(entry.planeState);
                    gameMgr.addPlane(new Plane(this,entry.nom,entry.x, entry.y, entry.heading, entry.behavior,route, state));
                }
                resultStream.close();
            }
            catch (FileNotFoundException e) {
               Log.i("Parser",e.toString());
            } catch (IOException e) {
                Log.i("Parser",e.toString());
            } catch (XmlPullParserException e) {
                Log.i("Parser",e.toString());
            }
        }else {
            try{
            save.createNewFile();
            }
            catch (FileNotFoundException e) {
                Log.i("Parser",e.toString());
            } catch (IOException e) {
                Log.i("Parser",e.toString());
            }
            Log.i("Parser","File Dir:"+save.getAbsolutePath());
            gameMgr.addPlane(new Plane(this, "R328FS", 475, 715, 0,3, gameMgr.getCharlie(), new DepartingState(gameMgr)));
            gameMgr.addPlane(new Plane(this, "N851TB", 150, 850, 90,1, gameMgr.getUpwind(), new ArrivingState(gameMgr)));
        }
        gameMgr.createMockupPlanes();


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

    /**
     * Remet à l'état initial la barre de boutons
     */
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

    /**
     * Ajoute un bouton
     * @param button
     */
    public void choicesAddButton(Button button){
        choicesLayout.addView(button);
    }

    /**
     * Change le taux de rafraichissement en supprimant le timer en cours et supprimant
     * le précédent
     * @param rate
     */
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

    //Permet de garder l'écran tout le temps allumé
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
        try {
            parser.write(getApplicationContext(), gameMgr.getPlanes());
        }catch (FileNotFoundException e) {
            Log.i("Parser",e.toString());
        }
        catch (IllegalArgumentException e) {
            Log.i("Parser",e.toString());
        }
        catch (IllegalStateException e) {
            Log.i("Parser",e.toString());
        }
        catch (IOException e) {
            Log.i("Parser",e.toString());
        }
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        t.cancel();
        try {
            parser.write(getApplicationContext(), gameMgr.getPlanes());
        }catch (FileNotFoundException e) {
            Log.i("Parser",e.toString());
        }
        catch (IllegalArgumentException e) {
            Log.i("Parser",e.toString());
        }
        catch (IllegalStateException e) {
            Log.i("Parser",e.toString());
        }
        catch (IOException e) {
            Log.i("Parser",e.toString());
        }
        super.onDestroy();
    }

    public GameMgr getGameMgr(){
        return gameMgr;
    }

    /**
     * Setter de la phrase vers le canvas
     * @param sentence
     */
    public void setSentence(String sentence){
        c.setSentence(sentence);
    }

    public void resetSave(){
        getApplicationContext().deleteFile("save.xml");
    }
}
