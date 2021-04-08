/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import m2pcci.im2ag.MyTheater.model.Place;
import m2pcci.im2ag.MyTheater.model.Representation;
import m2pcci.im2ag.MyTheater.model.Spectacle;
import m2pcci.im2ag.MyTheater.model.Ticket;

/**
 *
 * @author antoine
 */
public class TicketsDAO {

    public static String Query = "SELECT T.numeroTick as numeroTick, T.dateAchatTick as dateAchatTick, T.dateRepr as dateRepr, T.heureRepr as heureRepr, \n"
            + "T.numeroSpec as numeroSpec, T.numeroRang as numeroRang, T.numeroPlace as numeroPlace, S.nomSpec as nomSpec,S.nomGenre as nomGenre,S.typePublic as typePublic,\n"
            + "T.numeroZone as numeroZone, T.profilSpectateur as profilSpectateur \n"
            + "FROM LesTickets T\n"
            + "JOIN LesDossiersAchats_base DA  ON (T.numeroDossier = DA.numeroDossier)\n"
            + "JOIN LesSpectacles S on (S.numeroSpec = T.numeroSpec)\n"
            + "WHERE DA.numeroDossier = ? ;";

    public static List<Ticket> getTicketsDAO(DataSource ds, int numeroDossier) throws SQLException {

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(Query)) {
            stmt.setInt(1, numeroDossier);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Ticket> listeTickets = new ArrayList();
                while (rs.next()) {
                    Integer numeroTick = rs.getInt("numeroTick");
                    String dateAchatTick = rs.getString("dateAchatTick");
                    String dateRepr = rs.getString("dateRepr");
                    String heureRepr = rs.getString("heureRepr");
                    Integer numeroSpec = rs.getInt("numeroSpec");
                    String nomSpec = rs.getString("nomSpec");
                    String nomGenre = rs.getString("nomGenre");
                    String typePublic = rs.getString("typePublic");

                    Integer numeroZone = rs.getInt("numeroZone");
                    Integer numeroRang = rs.getInt("numeroRang");
                    Integer numeroPlace = rs.getInt("numeroPlace");
                    String profilSpectateur = rs.getString("profilSpectateur");

                    Place place = new Place(numeroRang, numeroPlace, numeroZone);
                    Spectacle s = new Spectacle(numeroSpec, nomSpec, nomGenre);
                    Representation representation = new Representation(s, dateRepr, heureRepr, numeroDossier, null, null);
                    Ticket t = new Ticket(representation, place, profilSpectateur, numeroTick, dateAchatTick, numeroDossier);
                    listeTickets.add(t);

                }
                return listeTickets;
            }
        }

    }
    public static Boolean dossierIncomplet (DataSource ds, int numeroDossier) throws SQLException{
        
        String QueryExist = "SELECT dateAchatTick FROM LesTickets WHERE numeroDossier = ? ";

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(QueryExist)) {
            stmt.setInt(1, numeroDossier);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean incomplet = false;
                if (rs.next()) {
                    incomplet = "NULL".equals(rs.getString("dateAchatTick"));
                }
                return incomplet;
            }
        }
    }

}
