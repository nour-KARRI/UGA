/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.dao;

import m2pcci.im2ag.MyTheater.model.Spectacle;
import m2pcci.im2ag.MyTheater.model.Representation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import m2pcci.im2ag.MyTheater.model.Zone;
import static m2pcci.im2ag.MyTheater.util.GestionDates.*;

/**
 *
 * @author nour
 */
public class MyTheaterDAO {

    static String Query3 = "SELECT R.numeroSpec,S.nomSpec,R.dateRepr,R.heureRepr,R.nbPlacesRestantes,S.nomGenre,R.preReservation,R.tauxReductionRepr"
            + " FROM LesRepresentations R"
            + " JOIN LesSpectacles S on S.numeroSpec = R.numeroSpec"
            + " WHERE R.dateRepr >= ? AND dateRepr <= ?;";

    static String Query4 = "SELECT synopsis"
            + " From LesSpectacles "
            + " WHERE numeroSpec = ?;";

    /**
     * Permet de récupérer les différents noms de genre
     */
    static String QueryGenre = "SELECT DISTINCT nomGenre"
            + " FROM LesSpectacles "
            + " ORDER BY nomGenre";

    /**
     * Permet de récupérer les différents typesPublics
     */
    static String QueryTypePublic = "SELECT DISTINCT typePublic"
            + " FROM LesSpectacles "
            + " ORDER BY typePublic";

    /**
     * Permet de récupérer les catégories et leurs prix utilisés pour des zones
     */
    static String QueryPrixPlace = "SELECT DISTINCT Z.numeroZone,Z.nomCat,C.prixCat"
            + " FROM LesZones Z"
            + " JOIN LesCategoriesZones C ON Z.nomCat=C.nomCat"
            + " GROUP BY Z.nomCat,C.prixCat"
            + " ORDER BY C.prixCat";

    static String Query5 = "SELECT R.numeroSpec,S.nomSpec,R.dateRepr,R.heureRepr,R.nbPlacesRestantes,S.nomGenre,R.preReservation,R.tauxReductionRepr"
            + " FROM LesRepresentations R"
            + " JOIN LesSpectacles S on S.numeroSpec = R.numeroSpec"
            + " WHERE R.numeroSpec= ? AND R.dateRepr= ? ;";

    public static List<Representation> getRepresentations(DataSource ds) throws SQLException, Exception {
        String Query = "SELECT R.numeroSpec as numeroSpec,S.nomSpec as nomSpec,R.dateRepr as dateRepr,R.heureRepr as heureRepr,R.nbPlacesRestantes as nbPlacesRestantes,S.nomGenre as nomGenre,R.preReservation as preReservation,R.tauxReductionRepr as tauxReductionRepr"
                + " FROM LesRepresentations R " + "JOIN LesSpectacles S on S.numeroSpec = R.numeroSpec "
                + " WHERE R.dateRepr >= ? "
                + " ORDER BY dateRepr";

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(Query)) {

            stmt.setString(1, inversionFormatDateInverse(dateToString(new Date())));

            try (ResultSet rs = stmt.executeQuery()) {
                List<Representation> listeRepresentations = new ArrayList();
                while (rs.next()) {
                    Integer numSpec = rs.getInt("numeroSpec");
                    String nomSpec = rs.getString("nomSpec");
                    String dateSpec = rs.getString("dateRepr");
                    String heureSpec = rs.getString("heureRepr");
                    Integer nbPlacesRestantes = rs.getInt("nbPlacesRestantes");
                    Double tauxReductionRepr = rs.getDouble("tauxReductionRepr");
                    Integer preReservation = rs.getInt("preReservation");

                    Representation p = new Representation(new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"), rs.getString("nomGenre")), inversionFormatDate(dateSpec), heureSpec, nbPlacesRestantes, (1 == rs.getInt("preReservation")), rs.getDouble("tauxReductionRepr"));
                    listeRepresentations.add(p);

                }
                return listeRepresentations;
            }
        }
    }

    /**
     *
     * @param ds
     * @param map
     * @param date ==> date choisie par l'utilisateur
     * @return une liste de spectacles correspondant à la programmation à une
     * date donnée
     * @throws SQLException
     * @throws Exception
     */
    public static List<Representation> getRepresentations(DataSource ds, Map<String, String> map) throws SQLException, Exception {
        String Query = "SELECT R.numeroSpec as numeroSpec,S.nomSpec as nomSpec,R.dateRepr as dateRepr,R.heureRepr as heureRepr,R.nbPlacesRestantes as nbPlacesRestantes,S.nomGenre as nomGenre,R.preReservation as preReservation,R.tauxReductionRepr as tauxReductionRepr"
                + " FROM LesRepresentations R " + "JOIN LesSpectacles S on S.numeroSpec = R.numeroSpec ";

        boolean premier = true;

        for (String key : map.keySet()) {
            if (!premier & !Query.endsWith("AND")) {
                Query = Query + " AND";

            }
            switch (key) {
                case "date":
                    if (!(map.get(key).equals(""))) {
                        if (premier) {
                            Query = Query + " WHERE ";
                            premier = false;
                        }
                        Query = Query + " dateRepr= '" + inversionFormatDateTiretVersSlash(map.get(key)) + "' ";

                    }
                    break;
                case "date1":
                    if (!map.get("date").equals("")) {
                        break;
                    } else {
                        if (premier) {
                            Query = Query + " WHERE ";
                            premier = false;
                        }
                        Query = Query + " dateRepr >= '";
                        if (map.get(key).equals("")) {
                            Query += inversionFormatDateInverse(dateToString(new Date())) + "' ";

                        } else {
                            Query += inversionFormatDateTiretVersSlash(map.get(key)) + "' ";

                        }
                    }
                    break;
                case "date2":
                    if (!"".equals(map.get("date"))) {
                        break;
                    } else {
                        if (premier) {
                            Query = Query + " WHERE ";
                            premier = false;
                        }
                        Query = Query + " dateRepr <= '";
                        if (map.get(key).equals("")) {
                            Query += "2099/12/31' ";

                        } else {
                            Query += inversionFormatDateTiretVersSlash(map.get(key)) + "' ";

                        }
                    }
                    break;
                case "genre":
                    if (!map.get(key).equals("Choisir...")) {
                        if (premier) {
                            Query = Query + " WHERE ";
                            premier = false;
                        }
                        Query = Query + " nomGenre = '" + map.get(key) + "' ";

                    }
                    break;
                case "public":
                    if (!map.get(key).equals("Choisir...")) {
                        if (premier) {
                            Query = Query + " WHERE ";
                            premier = false;
                        }
                        Query = Query + " typePublic = '" + map.get(key) + "' ";

                    }
                    break;
            }
        }
        if (Query.endsWith("AND")) {
            Query = Query.replace(Query, Query.subSequence(0, Query.lastIndexOf(" ")));
        }
        Query += " ORDER BY dateRepr";

        try (Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Representation> listeRepresentations = new ArrayList();
                while (rs.next()) {
                    Integer numSpec = rs.getInt("numeroSpec");
                    String nomSpec = rs.getString("nomSpec");
                    String dateSpec = rs.getString("dateRepr");
                    String heureSpec = rs.getString("heureRepr");
                    Integer nbPlacesRestantes = rs.getInt("nbPlacesRestantes");
                    Boolean preReservation = rs.getBoolean("preReservation");
                    Double tauxReductionRepr = rs.getDouble("tauxReductionRepr");

                    Representation p = new Representation(new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"), rs.getString("nomGenre")), inversionFormatDate(dateSpec), heureSpec, nbPlacesRestantes, (1 == rs.getInt("preReservation")), rs.getDouble("tauxReductionRepr"));
                    listeRepresentations.add(p);
                }
                return listeRepresentations;
            }
        }

    }

    /**
     *
     * @param ds => la data source pour les connexions
     * @param specId l'identifiant du spectacle
     * @return le synospis du spectacle, si il existe un spectacle d'id specID,
     * null sinon
     * @throws SQLException si un problème avec la base de données.
     */
    public static String getSynopsis(DataSource ds, int specId) throws SQLException, Exception {
        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(Query4)) {
            stmt.setInt(1, specId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("synopsis");
                }
                return null;
            }
        }
    }

    /**
     * Permet de récupérer une liste de Genres de spectacles
     *
     * @param ds => la data source pour les connexions
     * @return une liste de genre
     * @throws SQLException
     */
    public static List<String> getGenre(DataSource ds) throws SQLException {

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(QueryGenre)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> listeGenre = new ArrayList();
                while (rs.next()) {
                    listeGenre.add(rs.getString("nomGenre"));
                }
                return listeGenre;
            }
        }
    }

    /**
     * Permet de récupérer une liste de Types de Public concernés par les
     * spectacles
     *
     * @param ds => la data source pour les connexions
     * @return liste de type de public
     * @throws SQLException
     */
    public static List<String> getTypePublic(DataSource ds) throws SQLException {

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(QueryTypePublic)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<String> listeTypePublic = new ArrayList();
                while (rs.next()) {
                    listeTypePublic.add(rs.getString("typePublic"));
                }
                return listeTypePublic;
            }
        }
    }

    public static Representation getRep(DataSource ds, int nospectacle, String date) throws SQLException, Exception {
        try (Connection conn = ds.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query5)) {
            stmt.setInt(1, nospectacle);
            stmt.setString(2, inversionFormatDateInverse(date));
            try (ResultSet rs = stmt.executeQuery()) {
                Representation r = null;
                while (rs.next()) {
                    String dateSpec = rs.getString("dateRepr");
                    r = new Representation(new Spectacle(rs.getInt("numeroSpec"), rs.getString("nomSpec"), rs.getString("nomGenre")), inversionFormatDate(dateSpec), rs.getString("heureRepr"), rs.getInt("nbPlacesRestantes"), (1 == rs.getInt("preReservation")), rs.getDouble("tauxReductionRepr"));
                }
                return r;
            }
        } //To change body of generated methods, choose Tools | Templates.
    }

    public static List<Zone> getPrixZones(DataSource ds) throws SQLException {

        List<Zone> listeZones = new ArrayList();
        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(QueryPrixPlace)) {
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int numeroZone = rs.getInt("numeroZone");
                    String nomCat = rs.getString("nomCat");
                    Double prixCat = rs.getDouble("prixCat");

                    Zone z = new Zone(numeroZone, nomCat, prixCat);
                    listeZones.add(z);
                }

                return listeZones;
            }
        }
    }

    public static Boolean existDossier(DataSource ds, int numeroDossier) throws SQLException {

        String QueryExist = "SELECT count(*) as exist FROM LesDossiersAchats WHERE numeroDossier = ? ";

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(QueryExist)) {
            stmt.setInt(1, numeroDossier);
            try (ResultSet rs = stmt.executeQuery()) {

                boolean vrai = false;
                if (rs.next()) {
                    vrai = rs.getInt("exist") == 1;
                }
                return vrai;
            }
        }
    }
    
        

}
