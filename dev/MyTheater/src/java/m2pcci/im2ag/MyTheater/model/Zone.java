/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.model;

/**
 *
 * @author bannouny
 */
public class Zone {

    private final Integer numeroZone;
    private final String nomCat;
    private final Double prixZone;

    public Zone(Integer numeroZone, String nomCat, Double prixZone) {
        this.numeroZone = numeroZone;
        this.nomCat = nomCat;
        this.prixZone = prixZone;
    }

    public Integer getNumeroZone() {
        return numeroZone;
    }

    public String getNomCat() {
        return nomCat;
    }

    public Double getPrixZone() {
        return prixZone;
    }

    @Override
    public String toString() {
        return "Zone{" + "numeroZone=" + numeroZone + ", nomCat=" + nomCat + ", prixZone=" + prixZone + '}';
    }

}
