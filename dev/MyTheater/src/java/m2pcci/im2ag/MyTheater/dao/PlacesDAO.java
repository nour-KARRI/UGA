/*
 * Copyright (C) 2017 Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package m2pcci.im2ag.MyTheater.dao;

import static m2pcci.im2ag.MyTheater.util.GestionDates.*;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.sql.DataSource;
import m2pcci.im2ag.MyTheater.model.PlaceAchetees;
import m2pcci.im2ag.MyTheater.model.Representation;

/**
 * DAO proposant différentes méthodes concernant les places pour un spectacle
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
public class PlacesDAO {

    private static final String delete_dossier = "Delete FROM LesDossiersAchats_base WHERE numeroDossier = ?";

    private static final String delete_ticket = "Delete FROM LesTickets WHERE numeroDossier = ?;";

    private static final String Set_date_mail_achat = "UPDATE LesDossiersAchats_base SET dateCreationDossier = ?, eMailDoss = ? WHERE numeroDossier= ?;";
    /**
     * Requête pour trouver les places déjà vendues pour un spectacle donné
     */
    private static final String PLACES_VENDUES
            = "SELECT numeroPlace,numeroRang,numeroZone FROM LesTickets  WHERE dateRepr = ? AND heureRepr = ? ;";

    /**
     * creation dossiers achat
     */
    private static final String CREER_DOSSIERACHAT
            = "INSERT INTO LesDossiersAchats_base (dateCreationDossier) VALUES (?);";

    /**
     * récupère le dernier numéro dossier créé
     */
    private static final String GET_NUMERODOSSIERACHAT
            = "select max(numeroDossier) from LesDossiersAchats_base;";

    /**
     * crée les tickets pour un dossier
     */
    private static final String CREER_TICKETS
            = "INSERT INTO LesTickets (dateAchatTick,numeroRang,numeroPlace,numeroZone,dateRepr,heureRepr,numeroDossier,numeroSpec,profilSpectateur)"
            + "VALUES (?,?,?,?,?,?,?,?,?);";

    /**
     * ajouter date de paiement à tous les tickets du dossier
     */
    private static final String PAIEMENT_TICKETS = "UPDATE LesTickets set dateAchatTick=? "
            + "where numeroDossier=?;";

    /**
     * recherche pour un spectacle donné la liste des places qui ont déja été
     * vendues et la retourne sous la forme d'une chaîne JSON.Par exemple si les
     * places vendues sont les places de numéros 1, 2, 43 et 154 le code JSON
     * retournée au client sera :
     * <pre>
     * {"placesVendues":[
     *    {"placeId":1,"rang":1,"colonne":3},
     *    {"placeId":2,"rang":1,"colonne":4},
     *    {"placeId":43,"rang":2,"colonne":19},
     *    {"placeId":71,"rang":3,"colonne":19},{"placeId":100,"rang":4,"colonne":20},
     *    {"placeId":154,"rang":7,"colonne":15},
     *   ]
     * }
     * </pre> * @param ds la source de données pour les connexions JDBC
     *
     * @param ds
     * @param date
     * @param heure
     * @return la chaîne JSON
     * @throws SQLException si problème avec JDBC
     * @throws java.text.ParseException
     */
    public static String placesVenduesAsJSON(DataSource ds, String date, String heure) throws SQLException, ParseException {
        try (Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(PLACES_VENDUES)) {
            String invDate = inversionFormatDateInverse(date);
            pstmt.setString(1, invDate);
            pstmt.setString(2, heure);
            ResultSet rs = pstmt.executeQuery();
            StringWriter sw = new StringWriter();
            try (JsonGenerator gen = Json.createGenerator(sw)) {
                gen.writeStartObject()
                        .writeStartArray("placesVendues");
                while (rs.next()) {
                    gen.writeStartObject()
                            .write("placeId", rs.getInt("numeroZone") + "_" + rs.getInt("numeroRang") + "_" + rs.getInt("numeroPlace"))
                            .writeEnd();
                }
                gen.writeEnd()
                        .writeEnd();
            }
            return sw.toString();
        }
    }

    public static void SetDateAndMailDossier(DataSource ds, int numero_dossier, String mail) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(Set_date_mail_achat)) {
                conn.setAutoCommit(false);  // début d'une transaction

                Date DateHeureCourante = new Date();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateCreationDossier = formater.format(DateHeureCourante);

                pstmt.setString(1, dateCreationDossier);
                pstmt.setString(2, mail);
                pstmt.setInt(3, numero_dossier);
                pstmt.addBatch();  // ajoute la requête d'insertion au batch

                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();   // valide la transaction
            }
        }
    }

    public static void SetNumeroDossierSansDate(DataSource ds) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(CREER_DOSSIERACHAT)) {
                conn.setAutoCommit(false);  // début d'une transaction

                Date DateHeureCourante = new Date();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateCreationDossier = formater.format(DateHeureCourante);

                pstmt.setString(1, "");
                pstmt.addBatch();  // ajoute la requête d'insertion au batch

                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();   // valide la transaction
            }
        }
    }

    public static int GetNumeroDossier(DataSource ds) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(GET_NUMERODOSSIERACHAT)) {
                conn.setAutoCommit(false);  // début d'une transaction
                int numeroDossierRecupere = -1;
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    numeroDossierRecupere = Integer.parseInt(rs.getString("max(numeroDossier)"));
                }
                return numeroDossierRecupere;
            }
        }
    }

    public static void setTickets(DataSource ds, Representation representation, int numeroDossierAchat, PlaceAchetees[] placesIds) throws SQLException, ParseException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(CREER_TICKETS)) {
                //conn.setAutoCommit(false);  // début d'une transaction

                for (int i = 0; i < placesIds.length; i++) {
                    pstmt.setString(1, "NULL");
                    pstmt.setInt(2, placesIds[i].getNumeroRang());
                    pstmt.setInt(3, placesIds[i].getNumeroPlace());
                    pstmt.setInt(4, placesIds[i].getNumeroZone());
                    pstmt.setString(5, inversionFormatDateInverse(representation.getDateRepr()));
                    pstmt.setString(6, representation.getHeureRepr());
                    pstmt.setInt(7, numeroDossierAchat);
                    pstmt.setInt(8, representation.getSpectacle().getNumeroSpec());
                    pstmt.setString(9, placesIds[i].getReductionProfil());

                    pstmt.addBatch();  // ajoute la requête d'insertion au batch
                }
                pstmt.executeBatch();  // exécute les requêtes d'insertion
                //  conn.commit();   // valide la transaction
                // } catch (SQLException ex) {
                //   conn.rollback();   // annule la transaction 
            }
        }
    }

    public static String getPrixTotalDossier(DataSource ds, int numeroDossier) throws SQLException {

        String QueryPrixTotal = "SELECT Prix_total_dossier FROM LesDossiersAchats WHERE numeroDossier = ? ";
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(QueryPrixTotal)) {

                pstmt.setInt(1, numeroDossier);
                String prixTotalDossier = "null";
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    prixTotalDossier = rs.getString("Prix_total_dossier");
                }
                return prixTotalDossier;
            }
        }
    }

    /*
    public static void setValidationPaiementDossier(DataSource ds, int numeroDossier,String mail) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(Set_date_mail_achat)) {
                conn.setAutoCommit(false);  // début d'une transaction

                Date DateHeureCourante = new Date();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String datePaiementDossier = formater.format(DateHeureCourante);

                pstmt.setString(1, datePaiementDossier);
                pstmt.setString(2, mail);
                pstmt.setInt(3, datePaiementDossier);
                pstmt.addBatch();  // ajoute la requête d'insertion au batch

                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();   // valide la transaction
            }
        }
    }*/

    public static void setValidationPaiementTicket(DataSource ds, int numeroDossier) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(PAIEMENT_TICKETS)) {
                conn.setAutoCommit(false);  // début d'une transaction

                Date DateHeureCourante = new Date();
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String datePaiementDossier = formater.format(DateHeureCourante);

                pstmt.setString(1, datePaiementDossier);
                pstmt.setInt(2, numeroDossier);
                pstmt.addBatch();  // ajoute la requête d'insertion au batch

                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();   // valide la transaction
            }

        }

    }

    public static void deleteTicket(DataSource ds, int numeroDossier) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(delete_ticket)) {
                conn.setAutoCommit(false);
                pstmt.setInt(1, numeroDossier);
               pstmt.addBatch();  // ajoute la requête d'insertion au batch

                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();   
            }
        }
    }
    public static void deleteDossier(DataSource ds, int numeroDossier) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(delete_dossier)) {
                 conn.setAutoCommit(false);
                pstmt.setInt(1, numeroDossier);
                pstmt.addBatch();  // ajoute la requête d'insertion au batch

                pstmt.executeBatch();  // exécute les requêtes d'insertion
                conn.commit();  
            }
        }
    }

}
