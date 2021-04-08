/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.ctrlers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import m2pcci.im2ag.MyTheater.dao.AdresseMailDAO;
import m2pcci.im2ag.MyTheater.dao.MyTheaterDAO;
import m2pcci.im2ag.MyTheater.dao.PlacesDAO;
import m2pcci.im2ag.MyTheater.dao.TicketsDAO;
import m2pcci.im2ag.MyTheater.model.Ticket;

/**
 *
 * @author antoine
 */
@WebServlet(name = "RecuperationPanier", urlPatterns = {"/RecuperationPanier"})
public class RecuperationPanier extends HttpServlet {

    @Resource(name = "jdbc/SPEC")
    private DataSource dataSource;

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
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Integer numeroDossier = Integer.parseInt(request.getParameter("numeroDossier"));
        System.out.println("numeroDossier= " + numeroDossier);

        if (MyTheaterDAO.existDossier(dataSource, numeroDossier)) {
            if (TicketsDAO.dossierIncomplet(dataSource, numeroDossier)) {
                session.setAttribute("numeroDossier", numeroDossier);
                String adresseMail = AdresseMailDAO.getAdresseMail(dataSource, numeroDossier);
                session.setAttribute("coordonnees", adresseMail);
                List<Ticket> lt = TicketsDAO.getTicketsDAO(dataSource, numeroDossier);
                session.setAttribute("listeTicket", lt);
                String prixTotalDossier = PlacesDAO.getPrixTotalDossier(dataSource,numeroDossier);
                request.setAttribute("prixTotal", prixTotalDossier);
                request.getRequestDispatcher("/WEB-INF/espacePanierEmail.jsp").forward(request, response);
            } else {
                String erreur = "Panier déja acheté";
                request.setAttribute("erreur", erreur);
                request.getRequestDispatcher("/panierSauvegarde.jsp").forward(request, response);
            }
        } else {
            String erreur = "Numéro de Dossier inexistant";
            request.setAttribute("erreur", erreur);
            request.getRequestDispatcher("/panierSauvegarde.jsp").forward(request, response);
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
        } catch (Exception ex) {
            Logger.getLogger(RecuperationPanier.class
                    .getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(RecuperationPanier.class
                    .getName()).log(Level.SEVERE, null, ex);
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
