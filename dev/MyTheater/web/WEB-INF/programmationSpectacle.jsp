<%--
    Document   : page JSP pour l'affichage du programme (liste des représentations programmees
    Created on : 19 févr. 2019
    Author     : GP04 -
--%>
<%@page import="m2pcci.im2ag.MyTheater.model.Representation"%>
<%@page import="java.util.List"%>
<%@page import="m2pcci.im2ag.MyTheater.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="shortcut icon" href="images/icon.png">
        <title>MyTheatre</title>
        <link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <%@include file="../includes/header.jspf" %>

        
        <div >
           <h1 class="program">Programmation des pièces</h1>
        </div>
        
        <div class="container-fluid">
            <div class="row justify-content-md-left">
                <div class="col-6 container-fluid">
                    <form action="progServletFiltre" id="rechercheRepresentations" method="GET">
                        <fieldset id="fieldsetRecherche">
                            <legend>Rechercher des représentations</legend>
                            <div class="row">
                                <div class="col-md-8 container-fluid">
                                    <div class="input-group input-group-sm mb-3">
                                        <div class="input-group-prepend" >
                                            <span class="input-group-text">A la date du</span>
                                            <input type="date"  min="" name="date" id="dateUnique" value=${param.date}>

                                        </div>
                                    </div>

                                    <div class="input-group input-group-sm mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">A partir du</span>
                                            <input type="date" min="" name="date1" id="datePlage1"  value=${param.date1}>
                                        </div>
                                        <div class="input-group-prepend">
                                            <span class="input-group-text" id="">jusqu'au</span>
                                            <input type="date" min="" name="date2" id="datePlage2" value=${param.date2}>

                                        </div>
                                    </div>
                                </div>

                                <div class="col-4">
                                    <div class="input-group input-group-sm mb-3" >
                                        <div class="input-group-prepend">
                                            <label class="input-group-text" for="genre" >Genre</label>
                                        </div>


                                        <select class="custom-select" name ="genre" id="genre">


                                            <option selected>Choisir...</option>
                                            <%
                                                List<String> listeGenre = (List<String>) request.getAttribute("Genres");
                                                for (String p : listeGenre) {
                                                    out.println("<option value='" + p + "'>" + p + "</option>");
                                                }
                                            %>
                                        </select>
                                    </div>

                                    <div class="input-group input-group-sm mb-3" >
                                        <div class="input-group-prepend">
                                            <label class="input-group-text" for="public" >Type public</label>
                                        </div>
                                        <select class="custom-select" id="public" name ="public" >
                                            <option selected >Choisir...</option>
                                            <%
                                                List<String> listeTypePublic = (List<String>) request.getAttribute("TypesPublic");
                                                for (String p : listeTypePublic) {
                                                    out.println("<option value='" + p + "'>" + p + "</option>");
                                                }
                                            %>
                                        </select>

                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col">
                                    <button class="btn btn-primary btn-sm" type="submit" id="validerLaRecherche">Appliquer les filtres</button>
                                    <button class="btn btn-secondary btn-sm" type="submit" formaction="touteLaProg" onclick="effacer()">Effacer les filtres</button>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>

                <div class="col-3 container-fluid">
                    <fieldset id="fieldsetTarifs">
                        <legend>Tarifs des places</legend>
                        <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar">
                            <table class="table table-bordered table-striped mb-0" style='table-layout:auto;' id="tablePrixCategories">
                                <thead>
                                <th>Catégorie</th><th>Prix</th>
                                </thead>
                                <tbody>
                                    <%
                                        List<Zone> listeZones = (List<Zone>) request.getAttribute("ListeZones");
                                        for (Zone z : listeZones) {
                                            out.println("<tr>");
                                            out.println("<td>" + z.getNomCat() + " " + "</td>");
                                            out.println("<td>" + z.getPrixZone() + " € " + "</td>");
                                            out.println("</tr>");
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </fieldset>
                </div>
                <div class="col-3 container-fluid"></div>            

            </div>

            <div class="row justify-content-md-left">
                <div class="col-8 container-fluid">
                    <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar-representation">
                        <table class="table table-fixed table-bordered table-striped" style='table-layout: auto;' id="tableRepresentation">
                            <thead class="thead-dark">
                            <th>Pièce</th><th>Date</th><th>Heure</th><th>Genre</th><th>Réduction</th><th>Disponibilité</th><th>Pre-Réservation</th><th></th>
                            </thead>
                            <tbody>
                                <%
                                    
                                    
                                    List<Representation> listeRep = (List<Representation>) request.getAttribute("Representations");
                                    if (listeRep.size() == 0) {
                                        out.println("<tr>");
                                        out.println("<td colspan=8>" + "<span class=aucunResultat>" + "Aucun résultat trouvé" + "</span>" + "</td>");
                                        out.println("</tr>");
                                    } else {
                                        String Pre_reservation;
                                        int numeroBouton = 1;
                                        for (Representation p : listeRep) {
                                            if (p.getPreReservation()) {
                                                Pre_reservation = "Disponible";
                                            } else {
                                                Pre_reservation = "Non Disponible";
                                            }
                                            out.println("<tr>");
                                            out.println("<td>" + p.getSpectacle().getNomSpec() + " " + "</td>");
                                            out.println("<td>" + p.getDateRepr() + " " + "</td>");
                                            out.println("<td>" + p.getHeureRepr() + " " + "</td>");
                                            out.println("<td>" + p.getSpectacle().getNomGenre() + " " + "</td>");
                                            out.println("<td>" + p.getTauxReductionRepr() * 100 + "%" + " " + "</td>");
                                            out.println("<td>" + p.getNbrPlacesRestantesRepr() + " place(s) restante(s) " + "</td>");
                                            out.println("<td>" + Pre_reservation + " " + "</td>");
                                            out.println("<td><button type='button' class='btn btn-secondary btn-sm' onclick='Synopsis(" + p.getSpectacle().getNumeroSpec()
                                                    + "," + numeroBouton
                                                    + ",\"" + p.getSpectacle().getNomSpec()
                                                    + "\",\"" + p.getSpectacle().getNomGenre()
                                                    + "\")'>Voir détails</button>"
                                                    + " " + "<button type='button' class='btn btn-primary btn-sm' onclick=" + "\"location.href='Achat?nospectacle=" + p.getSpectacle().getNumeroSpec() + "&date=" + p.getDateRepr() + "'\"" ); if(p.getNbrPlacesRestantesRepr()==0){out.println(" disabled >");}else{out.println(">");} 
                                                    out.println("Sélectionner les places</button></td>");
                                            out.println("</tr>");
                                            numeroBouton++;
                                        }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="col-3 container-fluid">
                    <div id="synopsis">
                        <h1 id="titre" ></h1>
                        <h2 class="syn">Synopsis :</h2><p id ="genreSpec"></p> 
                        <p id="synopsi"></p>
                    </div>
                </div>

                <div class="col-1 container-fluid">
                </div>
            </div>


        </div>
                           
                                

        <%@include file="../includes/sidebar.jspf" %>
        <%@include file="../includes/footer.jspf" %>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
        <script src="js/programmation.js"></script>



    </body>
</html>
