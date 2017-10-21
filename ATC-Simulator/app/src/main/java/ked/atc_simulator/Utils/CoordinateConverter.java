package ked.atc_simulator.Utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/*
 * This class is meant to convert the coordinates used by the game to DP
 * As the app is forced to landscape, their might be some slight variations between devices their is no standard
 * widht/height ratio as for computer screens or TVs
 * The game uses coordinates from 0 to 1920 for the x axis and from 0 to 1080 for the y axis
 */
public class CoordinateConverter {

    public static float GetXDipsFromCoordinate(Context context, float coordinate)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display =   wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float pixels = coordinate*width/1920.0f;
        final float scale = context.getResources().getDisplayMetrics().density;
        return pixels - 0.5f / scale;
    }

    public static float GetYDipsFromCoordinate(Context context, float coordinate)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display =   wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float height = size.y;
        float pixels = coordinate*height/1080.0f;
        final float scale = context.getResources().getDisplayMetrics().density;
        return pixels - 0.5f / scale;
    }
}
