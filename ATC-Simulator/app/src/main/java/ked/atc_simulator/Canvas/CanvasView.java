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

public class CanvasView extends View {

    private GameMgr gameMgr;
    private Canvas canvas;
    private Paint paintWhite;
    private Paint paintBlack;
    private Paint paintBlue;
    private Bitmap backward, forward;
    private String sentence;


    public CanvasView(Context context, GameMgr gameMgr){
        super(context);
        this.gameMgr = gameMgr;
        backward = BitmapFactory.decodeResource(getResources(), R.drawable.backward);
        forward = BitmapFactory.decodeResource(getResources(), R.drawable.forward);
        sentence = "";
    }

    public void onDraw(Canvas canvas){
        paintWhite = new Paint();
        paintWhite.setStyle(Paint.Style.FILL_AND_STROKE);
        paintWhite.setColor(Color.WHITE);


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
        drawTaxiways(paintBlue,paintBlack);
        drawRunway(paintBlue, paintBlack);
        drawPlanes(paintWhite);


        canvas.drawBitmap(backward, 0,0, paintWhite);
        canvas.drawBitmap(forward, 200, 0, paintWhite);

        canvas.drawText("x"+gameMgr.getRate(),200,200,paintWhite);
        canvas.drawText(sentence,500,100,paintWhite);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        float x = ev.getX();
        float y = ev.getY();
        if(x <= 150 && y <= 150){
            Log.i("touch","backward");
            gameMgr.backward();
        } else if (x <= 300 && y <= 150) {
            gameMgr.forward();
            Log.i("touch","forward");
        }

        return true;
    }

    public void setSentence(String sentence){
        this.sentence = sentence;
    }

    public void drawPlanes(Paint paint){
        Log.i("Refresh","Draw planes");
        ArrayList<Plane> planes = gameMgr.getPlanes();
        for(Plane p : planes){
            canvas.drawPath(p.getPath(),paint);
            //canvas.drawTextOnPath(""+(p.getAlt()/100),p.getPath(), -20.0f, 40.0f,paintWhite);
            //canvas.drawTextOnPath(""+p.getSpeed(),p.getPath(), 5.0f, 30.0f,paintWhite);
        }
    }

    public void drawRunway(Paint paint, Paint paintBlack){
        paintBlack.setTextSize(45.0f);
        ArrayList<Runway> runways = gameMgr.getAirport().getRunways();
        for(Runway r : runways){
            canvas.drawPath(r.getPath(),paint);
            canvas.drawTextOnPath(""+r.getNumber(), r.getPath(), 5.0f, -2.0f,paintBlack);
        }
    }

    public void drawTaxiways(Paint paint, Paint paintBlack){
        paintBlack.setTextSize(25.0f);
        ArrayList<Taxiway> taxiways = gameMgr.getAirport().getTaxiways();
        for(Taxiway t : taxiways){
            Log.i("Drawing","drawing "+t.getNom());
            t.getPath().logPoints();
            canvas.drawPath(t.getPath(),paint);
            canvas.drawTextOnPath(t.getNom(), t.getPath(), t.gethOffset(), t.getvOffset(),paintBlack);
        }
    }

    public Canvas getCanvas(){ return canvas; }

    public void resetPlanes(){
        ArrayList<Plane> planes = gameMgr.getPlanes();
        for(Plane p : planes){
            p.getPath().rewind();
            p.calculateNewParams();
        }
    }
}
