package ked.atc_simulator.Utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import ked.atc_simulator.R;

/*
 * This class is meant to convert the coordinates used by the game to pixels and vice versa
 * As the app is forced to landscape and we assume that everybody uses 16/9 screens we can convert this pretty easily
 * The game uses coordinates from 0 to 1920 for the x axis and from 0 to 1080 for the y axis
 */
public class CoordinateConverter {

    public static float GetXDipsFromPixel(Context context, float coordinate)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display =   wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float pixels = coordinate*width/1920.0f;
        Log.i("PixelsX",""+pixels+", from "+coordinate+"*("+width+"/1920)");
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.i("ScaleX",""+scale);
        return pixels - 0.5f / scale;
    }

    public static float GetYDipsFromPixel(Context context, float coordinate)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display =   wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float height = size.y;
        float pixels = coordinate*height/1080.0f;
        Log.i("PixelsY",""+pixels+", from "+coordinate+"*("+height+"/1080)");
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.i("ScaleY",""+scale);
        return pixels - 0.5f / scale;
    }
}
