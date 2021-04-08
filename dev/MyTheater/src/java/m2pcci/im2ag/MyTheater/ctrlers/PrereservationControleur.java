/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.ctrlers;

;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import static m2pcci.im2ag.MyTheater.dao.PlacesDAO.SetDateAndMailDossier;
import m2pcci.im2ag.MyTheater.mail.MailSender;

/**
 *
 * @author bannouny
 */

public class PrereservationControleur extends HttpServlet {

    @Resource(name = "jdbc/SPEC")
    private DataSource dataSource;
    private Session mailSession;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String mail = (String) request.getParameter("coordonnees");
        HttpSession session = request.getSession();
        Integer numero_dossier = (int) session.getAttribute("numeroDossier");
        SetDateAndMailDossier(dataSource, numero_dossier, mail);

        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", getInitParameter("smtp_server"));
            prop.put("mail.smtp.port", getInitParameter("smtp_port"));

            mailSession = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(getInitParameter("mail_user_name"), getInitParameter("mail_user_passwd"));
                }
            });

            // création du message
            String messageBody = "Merci pour votre Réservation\n"
                    + "Votre numero de Dossier est le" + numero_dossier + "\n" + "Ce numero de Dossier vous servira à finaliser l'achat de vos places"
                    + " Pour récupérer votre dossier, rendez vous sur l'application MyTheatre et touchez le bouton \"Récupérer Panier\" ";

            MailSender.sendMail(mailSession,
                    getInitParameter("sender"),
                    mail,
                    getInitParameter("title"),
                    messageBody);

            
            request.setAttribute("numeroDossier", numero_dossier);
            request.getRequestDispatcher("/WEB-INF/ConfirmationPreresevation.jsp").forward(request, response);
            session.invalidate();
        } catch (MessagingException ex) {
            // le ticket n'a pas été envoyé
            // un message d'erreur sera envoyé à l'utilisateur
            throw new ServletException(ex.getMessage(), ex);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PrereservationControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(PrereservationControleur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
