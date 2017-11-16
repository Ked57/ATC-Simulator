package ked.atc_simulator.Utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/*
 * Cette classe convertie les coordonées utilisées par le jeu en DP
 * Comme l'application est forcée en mode paysage, il devrait y avoir quelques variations en fonction des appareils
 * car il n'y a pas vraiment de standard pour la hauteur et la largeur des écrans et leur ratio n'est jamais le même
 * Le jeu utilise des coordonées de 0 à 1920 pour l'axe x et de 0 a 1080 pour l'axe y
 */
public class CoordinateConverter {

    /**
     * Converti uen coordonnées X en Dip
     * @param context
     * @param coordinate
     * @return
     */
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

    /**
     * Converti une coordonée Y en Dip
     * @param context
     * @param coordinate
     * @return
     */
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
