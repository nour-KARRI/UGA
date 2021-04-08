/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.model;

import java.util.ArrayList;

/**
 *
 * @author bannouny
 */
public class DossierAchat {

    static Integer numeroDossier;
    private String email;
    private Double prixGlobalDossier;
    private String dateCreationDossier;
    private ArrayList<Ticket> Tickets;

    public DossierAchat(String email, Double prixGlobalDossier, String dateCreationDossier, ArrayList<Ticket> Tickets) {
        this.email = email;
        this.prixGlobalDossier = prixGlobalDossier;
        this.dateCreationDossier = dateCreationDossier;
        this.Tickets = Tickets;
    }

    public String getdateCreationDossier() {
        return dateCreationDossier;
    }

   

    public Integer getNumeroDossier() {
        return numeroDossier;
    }

    public String getEmail() {
        return email;
    }

    public Double getPrixGlobalDossier() {
        return prixGlobalDossier;
    }

    public ArrayList<Ticket> getTickets() {
        return Tickets;
    }

    @Override
    public String toString() {
        return "DossierAchat{" + "email=" + email + ", prixGlobalDossier=" + prixGlobalDossier + ", dateAchat=" + dateCreationDossier + ", Tickets=" + Tickets + '}';
    }

    

}
