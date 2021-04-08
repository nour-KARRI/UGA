/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.model;

import java.util.Date;
import org.apache.tomcat.jni.Time;

/**
 *
 * @author bannouny
 */
public class Representation {

    private Spectacle spectacle;
    private final String dateRepr;
    private final String heureRepr;
    private final Integer nbrPlacesRestantesRepr;
    private final Boolean preReservation;
    private final Double tauxReductionRepr;

    public Representation(Spectacle spectacle, String dateRepr, String heureRepr, Integer nbrPlacesRestantesRepr, Boolean preReservation, Double tauxReductionRepr) {
        this.spectacle = spectacle;
        this.dateRepr = dateRepr;
        this.heureRepr = heureRepr;
        this.nbrPlacesRestantesRepr = nbrPlacesRestantesRepr;
        this.preReservation = preReservation;
        this.tauxReductionRepr = tauxReductionRepr;
    }

    public Representation(int numeroSpectacle, String dateRepr, String heureRepr) {
        this.spectacle = new Spectacle(numeroSpectacle, "NULL", "NULL");
        this.dateRepr = dateRepr;
        this.heureRepr = heureRepr;
        this.nbrPlacesRestantesRepr = null;
        this.preReservation = null;
        this.tauxReductionRepr = null;
    }
    
    public Boolean getPreReservation() {
        return preReservation;
    }

    public Double getTauxReductionRepr() {
        return tauxReductionRepr;
    }   

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public Integer getNbrPlacesRestantesRepr() {
        return nbrPlacesRestantesRepr;
    }

    

    public String getDateRepr() {
        return dateRepr;
    }

    public String getHeureRepr() {
        return heureRepr;
    }

    @Override
    public String toString() {
        return "Representation{" + "spectacle=" + spectacle + ", dateRepr=" + dateRepr + ", heureRepr=" + heureRepr + ", nbrPlacesRestantesRepr=" + nbrPlacesRestantesRepr + ", preReservation=" + preReservation + ", tauxReductionRepr=" + tauxReductionRepr + '}';
    }

    

}
