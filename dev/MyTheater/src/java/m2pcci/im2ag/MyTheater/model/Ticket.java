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
public class Ticket {

    private Representation representation;
    private Place place;
    private String profilSpectateur;
    private Integer numeroTick;
    private String dateHeureAchatTick;
    private Integer numeroDossier;

    public Ticket(Representation representation, Place place, String profilSpectateur, Integer numeroTick, String dateAchatTick, Integer numeroDossier) {
        this.representation = representation;
        this.place = place;
        this.profilSpectateur = profilSpectateur;
        this.numeroTick = numeroTick;
        this.dateHeureAchatTick = dateAchatTick;
        this.numeroDossier = numeroDossier;

    }
    // /public Ticket(Integer numeroTick, String dateAchatTick, String dateRepr, String heureRepr, Integer numeroSpec, int numeroDossier, Integer numeroZone, Integer numeroRang, Integer numeroPlace, String profilSpectateur) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //  }

    public Integer getNumeroDossier() {
        return numeroDossier;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public Place getPlace() {
        return place;

    }

    public String getProfilSpectateur() {
        return profilSpectateur;
    }

    public Integer getNumeroTick() {
        return numeroTick;
    }

    public String getDateHeureAchatTick() {
        return dateHeureAchatTick;
    }

    @Override
    public String toString() {
        return "Ticket{" + "numeroTick=" + numeroTick + ", dateHeureAchatTick=" + dateHeureAchatTick
                + ", dateRepr=" + representation.getDateRepr() + ", heureRepr=" + representation.getHeureRepr()
                + ", numeroSpectacle=" + representation.getSpectacle().getNumeroSpec() + ", numeroDossier=" + numeroDossier + ", numeroZone=" + place.getNumeroZone()
                + ", numeroRang=" + place.getNumeroRang() + ", numeroPlace=" + place.getNumeroPlace() + ",profilSpectateur=" + profilSpectateur + '}';
    }

}
