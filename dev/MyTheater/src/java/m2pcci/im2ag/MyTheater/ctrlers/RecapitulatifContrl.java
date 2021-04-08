/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.ctrlers;

import java.io.IOException;
import java.sql.SQLException;
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
import m2pcci.im2ag.MyTheater.dao.PlacesDAO;

/**
 *
 * @author antoine
 */
@WebServlet(name = "RecapitulatifContrl", urlPatterns = {"/RecapitulatifContrl"})
public class RecapitulatifContrl extends HttpServlet {

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
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
           
        response.setContentType("text/html;charset=UTF-8");
        String nomBanque = request.getParameter("nomBanque");
        String numeroCompteBancaire = request.getParameter("numeroCompteBancaire");
        HttpSession session = request.getSession();
        session.setAttribute("nomBanque", nomBanque);
        session.setAttribute("numeroCompteBancaire", numeroCompteBancaire);

   
        Integer numeroDossier = (Integer) session.getAttribute("numeroDossier");
        String prixTotalDossier = PlacesDAO.getPrixTotalDossier(dataSource, numeroDossier);
        session.setAttribute("prixTotalDossier", prixTotalDossier);
        request.setAttribute("affichage", "none");
        System.out.println(request.getParameter("affichage"));
        System.out.println(request.getAttribute("affichage"));
        request.getRequestDispatcher("/WEB-INF/recapitulatif.jsp").forward(request, response);
        
        
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
            Logger.getLogger(RecapitulatifContrl.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RecapitulatifContrl.class.getName()).log(Level.SEVERE, null, ex);
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
