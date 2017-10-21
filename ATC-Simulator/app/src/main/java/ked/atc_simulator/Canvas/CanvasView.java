package ked.atc_simulator.Canvas;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
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
        paintBlack.setTextSize(50.0f);
        paintWhite.setTextSize(30.0f);
    }

    public void onDraw(Canvas canvas){
        this.canvas = canvas;
        canvas.drawColor(Color.BLACK);
        drawPlanes(canvas,paintWhite);
        drawRunway(canvas,paintWhite, paintBlack);
    }

    public void drawPlanes(Canvas canvas, Paint paint){
        ArrayList<Plane> planes = gameMgr.getPlanes();
        for(Plane p : planes){
            canvas.drawPath(p.getPath(),paint);
            canvas.drawTextOnPath(""+(p.getAlt()/100),p.getPath(), -5.0f, 30.0f,paintWhite);
            canvas.drawTextOnPath(""+p.getSpeed(),p.getPath(), -5.0f, 60.0f,paintWhite);
        }
    }

    public void drawRunway(Canvas canvas, Paint paint, Paint paintBlack){
        ArrayList<Runway> runways = gameMgr.getAirport().getRunways();
        for(Runway r : runways){
            canvas.drawPath(r.getPath(),paint);
            canvas.drawTextOnPath(""+r.getNumber(), r.getPath(), 5.0f, -2.0f,paintBlack);
        }
    }

    public Canvas getCanvas(){ return canvas; }
}
