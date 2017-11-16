package ked.atc_simulator.Gameplay;


import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ked.atc_simulator.Canvas.Point;
import ked.atc_simulator.Entities.Airport;
import ked.atc_simulator.Entities.Parking;
import ked.atc_simulator.Entities.Plane;
import ked.atc_simulator.Entities.Runway;
import ked.atc_simulator.Entities.Taxiway;
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
    private ArrayList<ParkingRoute> parkingRoutes;
    private RunwayRoute runwayTO, runwayLanding;
    private GameActivity context;
    private SentenceBuilder sentenceBuilder;
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
        parkingRoutes = new ArrayList<>();

        rate = 1; // refresh rate
        this.context = context;
        this.sentenceBuilder = new SentenceBuilder(this);

        airport.addRunway(new Runway(context,975,540,1000,270));
        airport.addTaxiway(new Taxiway(context,460,555,100,180,"Alpha",5f,-1f));
        airport.addTaxiway(new Taxiway(context,1480,555,100,180,"Bravo",5f,-1f));
        airport.addTaxiway(new Taxiway(context,970,180,962,270,"Charlie",460f,-1f));

        airport.addParking(new Parking(context,"P1",new Point(550,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P2",new Point(650,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P3",new Point(750,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P4",new Point(850,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P5",new Point(950,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P6",new Point(1050,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P7",new Point(1150,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P8",new Point(1250,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P9",new Point(1350,690),50,0,15f,-1f));
        airport.addParking(new Parking(context,"P10",new Point(1450,690),50,0,15f,-1f));

        //Création des différentes routes
        finale = new Route(175, 90, "Final", 1, 3,context.emptyRoute);
        finale.setStartPoint(new Point(150, 545));
        routes.add(finale);

        crosswind = new Route(175, 0, "Crosswind", 3, 3,context.emptyRoute);
        crosswind.setStartPoint(new Point(1750, 850));
        routes.add(crosswind);

        upwind = new Route(175, 90, "Upwind", 3, 3,context.emptyRoute);
        upwind.setStartPoint(new Point(150, 850));
        routes.add(upwind);

        base = new Route(175, 180, "Base", 3, 3,context.emptyRoute);
        base.setStartPoint(new Point(150, 200));
        routes.add(base);

        downwind = new Route(175, 270, "Downwind", 3, 3,context.emptyRoute);
        downwind.setStartPoint(new Point(1750, 200));
        routes.add(downwind);

        crosswindRN = new Route(175, 0, "CrosswindRN", 3, 3,context.emptyRoute);
        crosswindRN.setStartPoint(new Point(1750, 545));
        routes.add(crosswindRN);

        runwayLanding = new RunwayRoute(150, 90, 1000, "RunwayLanding", 9, 3,context.emptyRoute);
        runwayLanding.setStartPoint(new Point(465, 545));
        routes.add(runwayLanding);

        runwayTO = new RunwayRoute(150, 90, 1000, "RunwayTO", 3, 3,context.emptyRoute);
        runwayTO.setStartPoint(new Point(465, 545));
        routes.add(runwayTO);

        alpha = new Route(30, 0, "Alpha", 3, 3,context.emptyRoute);
        alpha.setStartPoint(new Point(465, 685));
        routes.add(alpha);

        charlie = new Route(30, 270, "Charlie", 3, 3,context.emptyRoute);
        charlie.setStartPoint(new Point(1490, 685));
        routes.add(charlie);

        bravo = new Route(30, 180, "Bravo", 9, 3,context.emptyRoute);
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

        //Création des routes de parking
        int i = 1;
        for(Parking p : getAirport().getParkings()){
            ParkingRoute pr = new ParkingRoute(30,0,"P"+i,3,3,context.emptyRoute);
            pr.setStartPoint(new Point((550+(i-1)*100),665));
            pr.setEndPoint((550+(i-1)*100),715);
            pr.setNextRoute(charlie);
            parkingRoutes.add(pr);
            Log.i("ParkingRoute","Created a parking route "+pr.getName()+" with startPoint :("+pr.getStartPoint().x+","+pr.getStartPoint().y+") and endPoint :("+pr.getEndPoint().x+","+pr.getEndPoint().y+")");
            ++i;
        }

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
        for (ParkingRoute r : parkingRoutes){
            if(r.getName().equals(name)){
                return r;
            }
        }
        return null;
    }

    public void createMockupPlanes(){
        //Création des modèles d'avions
        mockupPlanes.add(new Plane(context, "R328FS", 550, 665, 0,3, parkingRoutes.get(0), new DepartingState(this)));
        mockupPlanes.add(new Plane(context, "N851TB", 150, 850, 90,1, upwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "J458DS", 1750, 200, 270, downwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "ZEQ54D", 650, 665, 0,3, parkingRoutes.get(1), new DepartingState(this)));

        mockupPlanes.add(new Plane(context, "F8X65Z", 750, 665, 270,3, parkingRoutes.get(2), new DepartingState(this)));
        mockupPlanes.add(new Plane(context, "AB74ZE", 150, 850, 90,1, upwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "PO15AZ", 1750, 200, 270,1, downwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "ZEQ54D", 850, 665, 0,3, parkingRoutes.get(3), new DepartingState(this)));

        mockupPlanes.add(new Plane(context, "SD47AZ", 950, 665, 270,3, parkingRoutes.get(4), new DepartingState(this)));
        mockupPlanes.add(new Plane(context, "UF48XW", 150, 850, 90,1, upwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "LKI85X", 1750, 200, 270, downwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "ZEQ54D", 1050, 665, 0,3, parkingRoutes.get(5), new DepartingState(this)));

        mockupPlanes.add(new Plane(context, "Q54DEZ", 1150, 665, 270,3, parkingRoutes.get(6), new DepartingState(this)));
        mockupPlanes.add(new Plane(context, "AEG48X", 150, 850, 90,1, upwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "JEF48T", 1750, 200, 270,1, downwind, new ArrivingState(this)));
        mockupPlanes.add(new Plane(context, "ZEQ54D", 1250, 665, 0,3, parkingRoutes.get(7), new DepartingState(this)));
    }

    /**
     * Getter pour une parking route en fonction de son nom
     * @param name
     * @return
     */
    public ParkingRoute getParkingRouteByName(String name) {
        for (ParkingRoute r : parkingRoutes){
            if(r.getName().equals(name)){
                return r;
            }
        }
        return context.emptyParkingRoute;
    }

    /**
     * Getter pour les parking routes
     * @return
     */
    public ArrayList<ParkingRoute> getParkingRoutes(){ return parkingRoutes; }

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
        return context.emptyPlane;
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
        if(!getPlaneByName(p.getName()).equals(context.emptyPlane)){ // Il ne peut pas y avoir d'avions ayant le même nom dans tous les cas
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
                if(getSentenceBuilder().isSentenceEmpty()) {
                    //On met à jour le choix des avions
                    context.clearChoices();
                    getSentenceBuilder().buildSentence();
                }
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
            if(getSentenceBuilder().isSentenceEmpty()) {
                //On met à jour le choix des avions
                context.clearChoices();
                getSentenceBuilder().buildSentence();
            }
        }
    }
}
