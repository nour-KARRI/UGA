<%--
    Document   : espacePanier
    Created on : Mar 20, 2019, 10:59:25 AM
    Author     : nour
--%>


<%@page import="m2pcci.im2ag.MyTheater.dao.PlacesDAO"%>
<%@page import="java.util.List"%>
<%@page import="m2pcci.im2ag.MyTheater.model.Ticket"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="js/jQuery-Seat-Charts/jquery.seat-charts.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="shortcut icon" href="images/icon.png">
        <title>MyTheatre</title>
        <link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <%@include file="../includes/header.jspf" %>
        <div>
            <h1 class="mesPlaces"> Mes places sélectionnées </h1>
        </div>



        <div class="container-fluid">
            <div class="row container-fluid">
                <div class="col container-fluid  col-md-8">
                    <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar_panier">

                        <table class="table table-fixed table-bordered table-striped" style='table-layout: auto;' id="tableRepresentation">
                            <thead class="thead-dark">
                            <th>N° ticket</th><th>Pièce</th><th>Date</th><th>Heure</th><th>N° de rang</th><th>N° de place</th><th>N° de zone</th><th>Profil reduction</th>
                            </thead>
                            <tbody>
                                <%                                    List<Ticket> listeTicket = (List<Ticket>) session.getAttribute("listeTicket");
                                    for (Ticket p : listeTicket) {
                                        out.println("<tr>");
                                        out.println("<td>" + p.getNumeroTick() + " " + "</td>");
                                        out.println("<td>" + p.getRepresentation().getSpectacle().getNomSpec() + " " + "</td>");
                                        out.println("<td>" + p.getRepresentation().getDateRepr() + " " + "</td>");
                                        out.println("<td>" + p.getRepresentation().getHeureRepr() + " " + "</td>");
                                        out.println("<td>" + p.getPlace().getNumeroRang() + " " + "</td>");
                                        out.println("<td>" + p.getPlace().getNumeroPlace() + " " + "</td>");
                                        out.println("<td>" + p.getPlace().getNumeroZone() + " " + "</td>");
                                        out.println("<td>" + p.getProfilSpectateur() + " " + "</td>");
                                    }

                                %>

                            </tbody>
                        </table>

                        <%                                    out.println("<h2> Prix total du panier N° " + session.getAttribute("numeroDossier") + " :    " + request.getAttribute("prixTotal") + " euros</h2>");
                        %>
                    </div>
                </div>
            </div>

            <div class=" container-fluid ">
                <div class="col container-fluid  col-md-4">
                    <form>
                        <fieldset class="field">
                            <legend>Validation du panier</legend>

                            <div class="input-group input-group-sm mb-4">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Veuillez saisir votre eMail</span>
                                    <input input name="coordonnees" type="text" id="Mail" placeholder="ex: mail@monmail.com"
                                           <%                                        String coordonnees = (String) session.getAttribute("coordonnees");
                                               if (coordonnees != null) {
                                                   out.println("value =\"" + coordonnees + "\"");
                                                   out.println(" disabled  ");

                                               }%>
                                           />
                                    <%
                                        if (coordonnees != null) {
                                            out.println("<input input name=\"coordonnees\" type=\"hidden\" value=\"" + coordonnees + "\" />");
                                        }
                                    %>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md">
                                    <button type="submit" class="btn btn-primary btn-sm" value="Aller au paiment" formaction="coordonneesBancaires.jsp" onclick="return VerifMail();">Aller au paiment</button>
                                    <button type="submit" class="btn btn-secondary btn-sm" id="pres" value="Pré-réserver"  formaction="PrereservationControleur" onclick="return VerifMail();">Pré-réserver</button>
                                </div>
                            </div>

                            <div class="row">        
                                <div class="col-md">
                                    <button  type="submit" class="btn btn-danger btn-sm"  value="Supprimer panier" formaction="ViderPanier">Supprimer panier</button>
                                </div>
                            </div>
                            </div>


                        </fieldset>
                    </form>
                </div>
            </div>


            <%@include file="../includes/sidebar.jspf" %>
            <%@include file="../includes/footer.jspf" %>


            <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
            <script src="js/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
            <script src="js/achatPlaces.js"></script>


    </body>

</html>
