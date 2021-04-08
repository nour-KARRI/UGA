/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.util;

import m2pcci.im2ag.MyTheater.model.PlaceAchetees;

/**
 *
 * @author marjo
 */
public class detailPlaceAchetee {

    /**
     *
     * @param idPlaceAchetee
     * @return un objet PlaceAchetees cette fonction permet de traduire les
     * paramètres de l'URL envoyés à la servlet AcheterPlace en valeur pour
     * requete à envoyée à la BDD par la DAO
     */
    public static PlaceAchetees StringToPlaceAchetee(String idPlaceAchetee) {
        String idZone = "";
        String idPlace = "";
        String idRang = "";
        String reductionProfil = "";

        int i = 0;

        while ((idPlaceAchetee.charAt(i)) != '_') {
            idZone += idPlaceAchetee.charAt(i);
            i++;
        }

        i++;

        while ((idPlaceAchetee.charAt(i)) != '_') {
            idRang += idPlaceAchetee.charAt(i);
            i++;
        }

        i++;

        while ((idPlaceAchetee.charAt(i)) != '_') {
            idPlace += idPlaceAchetee.charAt(i);
            i++;
        }

        char lettreReduction = idPlaceAchetee.charAt(idPlaceAchetee.length() - 1);
        switch (lettreReduction) {
            case 'A':
                reductionProfil = "Adherent";
                break;
            case 'E':
                reductionProfil = "Etudiant";
                break;
            case 'F':
                reductionProfil = "Famille nombreuse";
                break;
            case 'T':
                reductionProfil = "Plein tarif";
                break;
        }

        return new PlaceAchetees(Integer.parseInt(idRang), Integer.parseInt(idPlace), Integer.parseInt(idZone), reductionProfil);
    }
}
