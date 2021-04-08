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
package m2pcci.im2ag.MyTheater.ctrlers;

import m2pcci.im2ag.MyTheater.model.Representation;
import m2pcci.im2ag.MyTheater.dao.PlacesDAO;
import m2pcci.im2ag.MyTheater.util.detailPlaceAchetee;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
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
import m2pcci.im2ag.MyTheater.dao.TicketsDAO;
import m2pcci.im2ag.MyTheater.model.Place;
import m2pcci.im2ag.MyTheater.model.PlaceAchetees;
import m2pcci.im2ag.MyTheater.model.Ticket;

/**
 * Enregistre dans la BD les places sélectionnées par l'utilisateur et redirige
 * sur la page confirmant l'achat.
 *
 * @author Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
 */
@WebServlet(name = "AcheterPlacesCtrler", urlPatterns = {"/acheterPlaces"})
public class AcheterPlacesCtrler extends HttpServlet {

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
     * @throws java.text.ParseException
     */
    protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {

        // récupère dans la session la représentation précédemment sélectionné
        HttpSession session = request.getSession();
        Representation representation = (Representation) session.getAttribute("Representation");

        // récupère les places séléctionnées par l'utilisateur
        String[] places = request.getParameterValues("place");
        List<Ticket> listeTickets = new ArrayList();

        PlaceAchetees[] placesIds = new PlaceAchetees[places.length];
        for (int i = 0; i < places.length; i++) {
            placesIds[i] = detailPlaceAchetee.StringToPlaceAchetee(places[i]);
        }

        for (int i = 0; i < places.length; i++) {
            Ticket t = new Ticket(representation, (Place) placesIds[i], placesIds[i].getReductionProfil(), 0, "", 0);
            listeTickets.add(t);
        }

        int numeroDossierAchat = -1;

        // on verifie si la session contient déjà le numéro de dossier
        // s'il n'existe pas, on le crée
        if (session.getAttribute("numeroDossier") == null || session.getAttribute("numeroDossier") == "0" ) {
            PlacesDAO.SetNumeroDossierSansDate(dataSource);
            // demande à la DAO de récupérer le numéro de dossier

            numeroDossierAchat = PlacesDAO.GetNumeroDossier(dataSource);
            // on met dans la session le numéro dossier
            session.setAttribute("numeroDossier", numeroDossierAchat);

            //sinon on récupère simplement le dernier numéro
        } else {
            // on recupère de la session le numéro déjà existant
            numeroDossierAchat = (int) session.getAttribute("numeroDossier");
        }

        PlacesDAO.setTickets(dataSource, representation, numeroDossierAchat, placesIds);
        List<Ticket> lt = TicketsDAO.getTicketsDAO(dataSource, numeroDossierAchat);
        session.setAttribute("listeTicket", lt);
        String prixTotalDossier = PlacesDAO.getPrixTotalDossier(dataSource, numeroDossierAchat);
        request.setAttribute("prixTotal", prixTotalDossier);

        //flag mis dans l'url (achatPlaces.js)
        if (request.getParameter("Flag").equals("1")) {
            // redirection vers la page confirmant l'achat.
            request.getRequestDispatcher("/WEB-INF/espacePanierEmail.jsp").forward(request, response);
        } else {
            // redirection vers la page d'accueil pour ajouer autre représentation
            request.getRequestDispatcher("/touteLaProg").forward(request, response);
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
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(AcheterPlacesCtrler.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(AcheterPlacesCtrler.class.getName()).log(Level.SEVERE, null, ex);
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