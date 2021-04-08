package m2pcci.im2ag.MyTheater.ctrlers;

import m2pcci.im2ag.MyTheater.ticket.TicketPDF;
import m2pcci.im2ag.MyTheater.mail.MailSender;
import com.google.zxing.WriterException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import m2pcci.im2ag.MyTheater.dao.TicketsDAO;
import m2pcci.im2ag.MyTheater.model.Ticket;

/**
 * Cette servlet, réalise l'action associée au bouton acheter du formulaire
 * "d'achat" défini dans la page index.html. Elle récupère les paramètres du
 * formulaire (nom, date de l'épreuve, nombre de place, identité et email de
 * l'utilisateur), génère un billet au format pdf qu'elle envoie par email à
 * l'acheteur, ensuite la servlet redirige la requête vers une page jsp afin de
 * confirmer "l'achat" et l'envoi du courriel ou d'afficher un message d'erreur
 * en cas de problème.
 *
 * @author Philippe Genoud (Université Grenoble Alpes - laboratoire LIG STeamer)
 */
public class TicketsCtrler extends HttpServlet {

    /**
     * l'objet mailSession est une ressource gérée par le conteneur de servlets.
     * Il est défini dans le fichier de configuration context.xml et dans le
     * fichier web.xml. L'annotation ci-dessous permet de récupérer sa
     * référence.
     */
    @Resource(name = "jdbc/SPEC")
    private DataSource dataSource;
    private Session mailSession;

    /* 
       cela serait mieux de procéder avec une ressource gérée par le serveur
       mais cela impose d'avoir le droit de copier la fichier javax.mail.jar dans
       le lib du serveur tomcat. Mais sur les machines de l'ufr les étudiants n'ont
       pas les droits adminsitrateur, et ecrire ce fichier dans le répertoire 
       nblib, de .netbeans ne suffit pas.
       C'est pourquoi l'objet mailSession est configuré avec des paramètres définis
       comme paramètres d'initialisation de la servlet, dans le fichier web.xml.
     */
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //----------------------------------------------------------------------
        // recupération des pramètres de la requête
        //----------------------------------------------------------------------
        HttpSession session = request.getSession();
        String recipientEMail = (String) session.getAttribute("coordonnees");
        String titulaire = request.getParameter("titulaireBillet");
        //int nbTickets = Integer.parseInt(request.getParameter("nbTickets"));
        Integer numeroDossier = (Integer) session.getAttribute("numeroDossier");
        // on récupère l'objet Epreuve représentant l'épreuve d'identifiant idEpreuve
        List<Ticket> lt = TicketsDAO.getTicketsDAO(dataSource, numeroDossier);


        //----------------------------------------------------------------------
        // génération du ticket au format pdf et envoi par couriel de ce fichier
        // à l'adresse email de l'utilisateur telle que définie dans le formulaire
        //----------------------------------------------------------------------
        boolean ticketEnvoye = false;
        try {
            //------------------------------------------------------------------
            // construction du fichier pdf correspondant au ticket
            //------------------------------------------------------------------
            // récupération le chemin absolu de l'image du logo
            ServletContext cntx = getServletContext();
            String logoImagePath = cntx.getRealPath("/images/logo_old.png");

            byte[] pdfTicket = TicketPDF.createPDF_AsByteArray(titulaire, lt, lt.size(), logoImagePath);

            //-----------------------------------------------------------------------
            // envoi du courriel avec comme document attaché le fichier pdf du ticket
            //-----------------------------------------------------------------------
            // création de l'objet mail session, ce code pourrait être avantageusement
            // remplacé par 
            //    @Resource(name = "mail/DEMO")
            //    private Session mailSession;
            // voir au debut de la servlet, mais pour des raison de configuration
            // (pas les droits administrateur) pour les étudiants ce n'est pas possible
            // au niveau des machine de l'ufr.
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", getInitParameter("smtp_server"));
            props.put("mail.smtp.port", getInitParameter("smtp_port"));

            mailSession = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(getInitParameter("mail_user_name"), getInitParameter("mail_user_passwd"));
                }
            });

            // création du message
            String messageBody = "Merci de votre Achat\n"
                    + "voici en document attaché votre billet electronique";
            MailSender.sendMailWithAttachedFile(mailSession,
                    getInitParameter("sender"),
                    recipientEMail,
                    getInitParameter("title"),
                    messageBody,
                    "ticket.pdf",
                    pdfTicket,
                    "application/pdf");
            ticketEnvoye = true;

            //----------------------------------------------------------------------
            // redirection vers la page jsp appropriée
            //----------------------------------------------------------------------
            request.setAttribute("listeTickets", lt);
            if (ticketEnvoye) {
                request.getRequestDispatcher("/WEB-INF/AchatReussi.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/EchecAchat.jsp").forward(request, response);
            }
        } catch (IOException | MessagingException | WriterException ex) {
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
            Logger.getLogger(TicketsCtrler.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TicketsCtrler.class.getName()).log(Level.SEVERE, null, ex);
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
