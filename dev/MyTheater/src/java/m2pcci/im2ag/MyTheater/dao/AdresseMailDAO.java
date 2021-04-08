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
import javax.sql.DataSource;

/**
 *
 * @author antoine
 */
public class AdresseMailDAO {

    static String QueryAdresseMail = "SELECT eMailDoss "
            + " FROM LesDossiersAchats_base "
            + " WHERE numeroDossier = ? ;";

    public static String getAdresseMail(DataSource ds, int numeroDossier) throws SQLException, Exception {

        try (Connection conn = ds.getConnection(); // on fait l'appel au JDBC par l'intermedaire de la methode getConnection() de ds
                PreparedStatement stmt = conn.prepareStatement(QueryAdresseMail)) {
            stmt.setInt(1, numeroDossier);
            try (ResultSet rs = stmt.executeQuery()) {
                String adresseMail = "";
                if (rs.next()) {
                    adresseMail = rs.getString("eMailDoss");
                }
                return adresseMail;
            }
        }
    }

}
