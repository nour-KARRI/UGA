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
public class Place {

    private final Integer numeroRang;
    private final Integer numeroPlace;
    private final Integer numeroZone;

    public Place(Integer numeroRang, Integer numeroPlace, Integer numeroZone) {
        this.numeroRang = numeroRang;
        this.numeroPlace = numeroPlace;
        this.numeroZone = numeroZone;
    }

    public Integer getNumeroRang() {
        return numeroRang;
    }

    public Integer getNumeroPlace() {
        return numeroPlace;
    }

    public Integer getNumeroZone() {
        return numeroZone;
    }

    @Override
    public String toString() {
        return "Place{" + "numeroRang=" + numeroRang + ", numeroPlace=" + numeroPlace + ", numeroZone=" + numeroZone + '}';
    }

}
