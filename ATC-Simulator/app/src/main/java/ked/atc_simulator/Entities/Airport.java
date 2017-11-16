package ked.atc_simulator.Entities;


import java.util.ArrayList;

import ked.atc_simulator.Canvas.RunwayPath;

/**
 * Classe contenant les pistes et les taxiway
 */
public class Airport {

    private ArrayList<Runway> runways;
    private ArrayList<Taxiway> taxiways;
    private ArrayList<Parking> parkings;

    /**
     * Constructeur de la classe Airport
     */
    public Airport(){

        this.runways = new ArrayList<Runway>();
        this.taxiways = new ArrayList<Taxiway>();
        this.parkings = new ArrayList<>();
    }

    /**
     * Getter pour la liste runways
     * @return
     */
    public ArrayList<Runway> getRunways(){
        return runways;
    }

    /**
     * Getter pour la liste taxiways
     * @return
     */
    public ArrayList<Taxiway> getTaxiways(){
        return taxiways;
    }

    /**
     * Permet d'ajouter une piste
     * @param runway
     */
    public void addRunway(Runway runway) { runways.add(runway); }

    /**
     * permet de supprimer une piste
     * @param runway
     */
    public void removeRunway(Runway runway) { runways.remove(runway); }

    /**
     * Permet d'ajouter un taxiway
     * @param taxiway
     */
    public void addTaxiway(Taxiway taxiway) { taxiways.add(taxiway); }

    /**
     * permet de supprimer un taxiway
     * @param taxiway
     */
    public void removeTaxiway(Taxiway taxiway) { taxiways.remove(taxiway); }

    /**
     * Getter pour les parkings
     * @return
     */
    public ArrayList<Parking> getParkings() {
        return parkings;
    }

    /**
     * Permet d'ajouter un parking
     * @param p
     */
    public void addParking(Parking p){
        parkings.add(p);
    }

    /**
     * Permet de supprimer un parking
     * @param p
     */
    public void removeParking(Parking p){
        parkings.remove(p);
    }
}
