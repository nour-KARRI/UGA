/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m2pcci.im2ag.MyTheater.ctrlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import m2pcci.im2ag.MyTheater.dao.MyTheaterDAO;
import m2pcci.im2ag.MyTheater.model.Representation;
import m2pcci.im2ag.MyTheater.model.Zone;

/**
 *
 * @author nour
 */
@WebServlet(name = "progServletFiltre", urlPatterns = {"/progServletFiltre"})
public class progServletFiltre extends HttpServlet {

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

        try {
            Map<String, String> filtreMap = new HashMap<>();
            filtreMap.put("date", request.getParameter("date"));
            filtreMap.put("date1", request.getParameter("date1"));
            filtreMap.put("date2", request.getParameter("date2"));
            filtreMap.put("genre", request.getParameter("genre"));
            filtreMap.put("public", request.getParameter("public"));

            List<Representation> Representations;
            List<String> Genres;
            List<String> TypesPublic;
            List<Zone> ListeZones;

            Representations = MyTheaterDAO.getRepresentations(dataSource, filtreMap);
            Genres = MyTheaterDAO.getGenre(dataSource);
            TypesPublic = MyTheaterDAO.getTypePublic(dataSource);
            ListeZones = MyTheaterDAO.getPrixZones(dataSource);

            request.setAttribute("Representations", Representations);
            request.setAttribute("Genres", Genres);
            request.setAttribute("TypesPublic", TypesPublic);
            request.setAttribute("ListeZones", ListeZones);
            
            request.getRequestDispatcher("/WEB-INF/programmationSpectacle.jsp").forward(request, response);
        } catch (SQLException ex) {
            // le plus simple dans une phase de développement est de relancer l'exception
            // sous la forme d'une ServletException. Dans une application réelle
            // il serait bien du'avoir une gestion d'exceptions prise en charge
            // par une page d'erreur.
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
        } catch (Exception ex) {
            Logger.getLogger(progServletFiltre.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(progServletFiltre.class.getName()).log(Level.SEVERE, null, ex);
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
