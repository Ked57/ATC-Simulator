package ked.atc_simulator.Canvas;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;
import ked.atc_simulator.Gameplay.GameMgr;
import ked.atc_simulator.R;
import ked.atc_simulator.Utils.CoordinateConverter;

/**
 *  Extension de la classe View permettant de dessiner les différents éléments (avions, pistes, ...)
 *  de l'application
 */
public class CanvasView extends View {

    private GameMgr gameMgr;
    private Canvas canvas;
    private Paint paintWhite, paintText;
    private Paint paintBlack;
    private Paint paintBlue;
    private Bitmap backward, forward;
    private String sentence;
    private Context context;

    /**
     * Constructeur de la classe CanvasView
     * @param context
     * @param gameMgr
     */
    public CanvasView(Context context, GameMgr gameMgr){
        super(context);
        this.gameMgr = gameMgr;
        backward = BitmapFactory.decodeResource(getResources(), R.drawable.backward);
        forward = BitmapFactory.decodeResource(getResources(), R.drawable.forward);
        sentence = "";
        this.context = context;
    }

    /**
     * Fonction appelée automatiquement quand la view est dessinée
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        paintWhite = new Paint();
        paintWhite.setStyle(Paint.Style.FILL_AND_STROKE);
        paintWhite.setColor(Color.WHITE);

        paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(30f);

        paintBlue = new Paint();
        paintBlue.setStyle(Paint.Style.FILL_AND_STROKE);
        paintBlue.setColor(Color.BLUE);

        paintBlack = new Paint();
        paintBlack.setStyle(Paint.Style.FILL_AND_STROKE);
        paintBlack.setColor(Color.BLACK);
        paintBlack.setTextSize(45.0f);
        paintWhite.setTextSize(30.0f);
        resetPlanes();

        this.canvas = canvas;
        canvas.drawColor(Color.BLACK);
        drawTaxiways(paintBlue,paintBlack); // Dessine les taxiways
        drawRunway(paintBlue, paintBlack); // Dessine les pistes
        drawPlanes(paintWhite); // dessine les avions


        canvas.drawBitmap(backward, 0,0, paintWhite); // Flèche d'avance rapide en arrière
        canvas.drawBitmap(forward, 200, 0, paintWhite); // Flèche d'avance rapide en avant

        canvas.drawText("x"+gameMgr.getRate(),200,200,paintWhite); // Affichage du taux d'avance rapide
        canvas.drawText(sentence,500,100,paintWhite); // Affichage de la phrase
    }

    /**
     *  Gestion des onTouchEvent pour les flèches d'avance rapide
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev){
        float x = ev.getX();
        float y = ev.getY();
        if(x <= 200 && y <= 150){
            Log.i("touch","backward");
            gameMgr.backward();
        } else if (x <= 400 && y <= 150) {
            gameMgr.forward();
            Log.i("touch","forward");
        }

        return true;
    }

    /**
     * Setter pour la phrase à afficher
     * @param sentence
     */
    public void setSentence(String sentence){
        this.sentence = sentence;
    }

    /**
     * Dessine les avions
     * @param paint
     */
    public void drawPlanes(Paint paint){
        Log.i("Refresh","Draw planes");
        ArrayList<Plane> planes = gameMgr.getPlanes();
        for(Plane p : planes){
            canvas.drawPath(p.getPath(),paint);
            // Affiche le nom de l'avion en dessous du dessin
            canvas.drawText(p.getName(), CoordinateConverter.GetXDipsFromCoordinate(context,p.getPath().getStartPoint().x), CoordinateConverter.GetYDipsFromCoordinate(context,p.getPath().getStartPoint().y+30),paintText);
        }
    }

    /**
     * Dessine les pistes
     * @param paint
     * @param paintBlack
     */
    public void drawRunway(Paint paint, Paint paintBlack){
        paintBlack.setTextSize(45.0f);
        ArrayList<Runway> runways = gameMgr.getAirport().getRunways();
        for(Runway r : runways){
            canvas.drawPath(r.getPath(),paint);
            // Affiche le numéro de la piste
            canvas.drawTextOnPath(""+r.getNumber(), r.getPath(), 5.0f, -2.0f,paintBlack);
        }
    }

    /**
     * Dessine les taxiways
     * @param paint
     * @param paintBlack
     */
    public void drawTaxiways(Paint paint, Paint paintBlack){
        paintBlack.setTextSize(25.0f);
        ArrayList<Taxiway> taxiways = gameMgr.getAirport().getTaxiways();
        for(Taxiway t : taxiways){
            Log.i("Drawing","drawing "+t.getNom());
            t.getPath().logPoints();
            canvas.drawPath(t.getPath(),paint);
            //Affiche le nom du taxiway
            canvas.drawTextOnPath(t.getNom(), t.getPath(), t.gethOffset(), t.getvOffset(),paintBlack);
        }
    }

    /**
     * Getter pour le canvas
     * @return
     */
    public Canvas getCanvas(){ return canvas; }

    /**
     * Fonction appelée à chaque rafraichissement permettant de calculer les positions des avions
     */
    public void resetPlanes(){
        ArrayList<Plane> planes = gameMgr.getPlanes();
        gameMgr.cleanupPlanes();
        for(Plane p : planes){
            p.getPath().rewind(); // Pour effacer les avions du rafaichissement précédent
            p.calculateNewParams();
        }
    }
}
