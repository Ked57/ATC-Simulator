package ked.atc_simulator.Gameplay;


import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.Entities.Airport;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.GameActivity;
import ked.atc_simulator.State.ArrivingState;
import ked.atc_simulator.State.DepartingState;
import ked.atc_simulator.State.PlaneState;

/**
 * Cette classe contient et gère les éléments du jeu
 */
public class GameMgr {

    private ArrayList<Plane> planes, mockupPlanes;
    private Airport airport;

    private Route alpha, bravo, charlie, downwind, base, finale, upwind, crosswind, crosswindRN;
    private RunwayRoute runwayTO, runwayLanding;
    private GameActivity context;
    private SentenceBuilder sentenceBuilder;
    public final Plane emptyPlane = new Plane();
    private ArrayList<Route> routes;

    public int rate;

    /**
     * Constructeur de la classe GameManager
     * @param context
     */
    public GameMgr(GameActivity context) {
        planes = new ArrayList<Plane>();
        mockupPlanes = new ArrayList<>();
        airport = new Airport();
        routes = new ArrayList<>();

        rate = 1; // refresh rate
        this.context = context;
        this.sentenceBuilder = new SentenceBuilder(this);

        //Création des différentes routes
        finale = new Route(175, 90, "Final", 1, 3);
        finale.setStartPoint(new Point(150, 545));
        routes.add(finale);

        crosswind = new Route(175, 0, "Crosswind", 3, 3);
        crosswind.setStartPoint(new Point(1750, 850));
        routes.add(crosswind);

        upwind = new Route(175, 90, "Upwind", 3, 3);
        upwind.setStartPoint(new Point(150, 850));
        routes.add(upwind);

        base = new Route(175, 180, "Base", 3, 3);
        base.setStartPoint(new Point(150, 200));
        routes.add(base);

        downwind = new Route(175, 270, "Downwind", 3, 3);
        downwind.setStartPoint(new Point(1750, 200));
        routes.add(downwind);

        crosswindRN = new Route(175, 0, "CrosswindRN", 3, 3);
        crosswindRN.setStartPoint(new Point(1750, 545));
        routes.add(crosswindRN);

        runwayLanding = new RunwayRoute(150, 90, 1000, "RunwayLanding", 9, 3);
        runwayLanding.setStartPoint(new Point(465, 545));
        routes.add(runwayLanding);

        runwayTO = new RunwayRoute(150, 90, 1000, "RunwayTO", 3, 3);
        runwayTO.setStartPoint(new Point(465, 545));
        routes.add(runwayTO);

        alpha = new Route(30, 0, "Alpha", 3, 3);
        alpha.setStartPoint(new Point(465, 685));
        routes.add(alpha);

        charlie = new Route(30, 270, "Charlie", 3, 3);
        charlie.setStartPoint(new Point(1490, 685));
        routes.add(charlie);

        bravo = new Route(30, 180, "Bravo", 9, 3);
        bravo.setStartPoint(new Point(1490, 545));
        routes.add(bravo);

        //On dit a chaque route quelle est la suivante
        crosswind.setNextRoute(downwind);
        upwind.setNextRoute(crosswind);
        base.setNextRoute(upwind);
        downwind.setNextRoute(base);
        crosswindRN.setNextRoute(downwind);
        runwayLanding.setNextRoute(bravo);
        runwayTO.setNextRoute(crosswindRN);
        alpha.setNextRoute(runwayTO);
        charlie.setNextRoute(alpha);
        bravo.setNextRoute(charlie);
        finale.setNextRoute(runwayLanding);

        //Création des modèles d'avions
        mockupPlanes.add(new Plane(context, "R328FS", 1490, 685, 270,3, charlie, new DepartingState(this)));
        mockupPlanes.add(new Plane(context, "N851TB", 150, 850, 90,1, upwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "F8X65Z", 1000, 685, 270,3, charlie, new DepartingState(this)));
        mockupPlanes.add(new Plane(context, "AB74ZE", 150, 850, 90,1, upwind, new ArrivingState(this)));

    }

    /**
     * Getter pour une route en fonction de son nom
     * @param name
     * @return
     */
    public Route getRouteByName(String name) {
        for (Route r : routes) {
            if (r.getName().equals(name))
                return r;
        }
        return null;
    }

    /**
     * Retourne un état d'avion en fonction de son nom
     * @param name
     * @return
     */
    public PlaneState getPlaneStateByName(String name) {
        if (name.equals("ArrivingState"))
            return new ArrivingState(this);
        else if (name.equals("DepartingState"))
            return new DepartingState(this);
        else return null;
    }

    /**
     * Getter pour la liste des avions
     * @return
     */
    public ArrayList<Plane> getPlanes() {
        return planes;
    }

    /**
     * Getter pour l'aéroport
     * @return
     */
    public Airport getAirport() {
        return airport;
    }

    /**
     * Ajoute un avion
     * @param plane
     */
    public void addPlane(Plane plane) {
        planes.add(plane);
    }

    /**
     * Supprimer un avion
     * @param plane
     */
    public void removePlane(Plane plane) {
        planes.remove(plane);
    }

    /**
     * Getter pour la route Alpha
     * @return
     */
    public Route getAlpha() {
        return alpha;
    }

    /**
     * Getter pour la route Bravo
     * @return
     */
    public Route getBravo() {
        return bravo;
    }

    /**
     * Getter pour la route Charlie
     * @return
     */
    public Route getCharlie() {
        return charlie;
    }

    /**
     * Getter pour la route Downwind
     * @return
     */
    public Route getDownwind() {
        return downwind;
    }

    /**
     * Getter pour la route Base
     * @return
     */
    public Route getBase() {
        return base;
    }

    /**
     * Getter pour la route Finale
     * @return
     */
    public Route getFinale() {
        return finale;
    }

    /**
     * Getter pour la route Upwind
     * @return
     */
    public Route getUpwind() {
        return upwind;
    }

    /**
     * Getter pour la route Crosswind
     * @return
     */
    public Route getCrosswind() {
        return crosswind;
    }

    /**
     * Getter pour la route CrosswindRN
     * @return
     */
    public Route getCrosswindRN() {
        return crosswindRN;
    }

    /**
     * Getter pour la route RunwayTO
     * @return
     */
    public RunwayRoute getRunwayTO() {
        return runwayTO;
    }

    /**
     * Getter pour la route RunwayLanding
     * @return
     */
    public RunwayRoute getRunwayLanding() {
        return runwayLanding;
    }

    /**
     * Getter pour le contexte
     * @return
     */
    public GameActivity getContext() {
        return context;
    }

    /**
     * Getter pour le constructeur de phrases
     * @return
     */
    public SentenceBuilder getSentenceBuilder() {
        return sentenceBuilder;
    }

    /**
     * Retourne un avion en fonction de son nom
     * Retourne l'objet emptyPlane si l'avion n'existe pas
     * @param name
     * @return
     */
    public Plane getPlaneByName(String name) {
        for (Plane p : planes) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return emptyPlane;
    }

    /**
     * Construit un nouveau constructeur de phrases
     */
    public void newSentenceBuilder() {
        sentenceBuilder = new SentenceBuilder(this);
    }

    /**
     * Avance rapide
     * Augmente de rafraichissement
     */
    public void forward() {
        if (rate < 10) {
            ++rate;
            context.setRefreshRate(rate);
            Log.i("touch", "rate = " + rate + " ms: " + (1000 / rate));
        }
    }

    /**
     * Avance rapide
     * Réduit le rafraichissement
     */
    public void backward() {
        if (rate > 1) {
            --rate;
            context.setRefreshRate(rate);
            Log.i("touch", "rate = " + rate + " ms: " + (1000 / rate));
        }
    }

    /**
     * Getter pour le taux de rafraichissement
     * @return
     */
    public int getRate() {
        return rate;
    }

    /**
     * Cette fonction vérifie qu'un avion n'est pas déjà en jeu
     * @param p
     * @return
     */
    public boolean isPlaneAlreadySpawned(Plane p){
        if(!getPlaneByName(p.getName()).equals(emptyPlane)){ // Il ne peut pas y avoir d'avions ayant le même nom dans tous les cas
            return true;
        }else return false;
    }

    /**
     * Cette fonction va checker les coordonées des avions et enlever les objets en dehors des coordonnées
     * Elle va aussi rajouter quelques avions, le nombre ici est 2 et arbitraire. Il pourrait être augmenté
     * dans l'application elle même comme un choix de difficulté
     */
    public void cleanupPlanes() {
        Log.i("Cleanup","Currently "+planes.size()+" planes");
        for (int i = 0; i < planes.size(); ++i) {
            Plane p = planes.get(i);
            if (p.isOutOfScreen() || p.isMarkedForRemoval()) {
                Log.i("Cleanup","Deleting "+p.getName());
                planes.remove(i);
            }
        }
        while (planes.size() < 2) {
            Random r = new Random();
            Plane p;
            Log.i("Cleanup","Planes size is now "+planes.size());
            do {
                int rand = r.nextInt(mockupPlanes.size()-1);
                p = mockupPlanes.get(rand);
            } while (isPlaneAlreadySpawned(p));
            //On ajoute un nouvel avion basé sur le mockup
            addPlane(new Plane(context,p.getName(),p.getBase().x,p.getBase().y,p.getHeading(),p.getBehavior(),p.getRoute(),p.getPlaneState()));
            Log.i("Cleanup","Added a new planes based on "+p.getName());
        }
    }
}
