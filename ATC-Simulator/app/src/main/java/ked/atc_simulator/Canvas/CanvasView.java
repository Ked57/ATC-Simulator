package ked.atc_simulator.Canvas;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
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


    public CanvasView(Context context, GameMgr gameMgr){
        super(context);
        this.gameMgr = gameMgr;
        paintWhite = new Paint();
        paintWhite.setStyle(Paint.Style.FILL_AND_STROKE);
        paintWhite.setColor(Color.WHITE);
        paintWhite.setShadowLayer(5.0f, 0.0f, 0.0f, R.color.black_background);
        paintBlack = new Paint();
        paintBlack.setStyle(Paint.Style.FILL_AND_STROKE);
        paintBlack.setColor(Color.BLACK);
        paintBlack.setTextSize(45.0f);
        paintWhite.setTextSize(30.0f);
    }

    public void onDraw(Canvas canvas){
        this.canvas = canvas;
        canvas.drawColor(Color.BLACK);
        drawPlanes(paintWhite);
        drawTaxiways(paintWhite,paintBlack);
        drawRunway(paintWhite, paintBlack);
    }

    public void drawPlanes(Paint paint){
        Log.i("Refresh","Draw planes");
        ArrayList<Plane> planes = gameMgr.getPlanes();
        for(Plane p : planes){
            canvas.drawPath(p.getPath(),paint);
            canvas.drawTextOnPath(""+(p.getAlt()/100),p.getPath(), -5.0f, 30.0f,paintWhite);
            canvas.drawTextOnPath(""+p.getSpeed(),p.getPath(), -5.0f, 60.0f,paintWhite);
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
}
