package ked.atc_simulator.Canvas;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PlaneCanvasView extends View {

    private ArrayList<PlaneCanvas> planeCanvases;


    public PlaneCanvasView(Context context){
        super(context);
    }

    public void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);
        drawPlane(canvas,paint);
    }

    public void drawPlane(Canvas canvas, Paint paint){

        PlaneCanvas planeCanvas = new PlaneCanvas(new Point(200,200),0);
        Point[] points = planeCanvas.getPoints();
        Path planePath = new Path();

        planePath.moveTo(((float) points[0].x), ((float) points[0].y));
        for (int i = 1; i < points.length; i++){
            Log.i("Cast"+i,"Double: "+points[i].x+","+points[i].y);
            planePath.lineTo(((float) points[i].x), ((float) points[i].y));
        }


        canvas.drawPath(planePath,paint);
    }
}
