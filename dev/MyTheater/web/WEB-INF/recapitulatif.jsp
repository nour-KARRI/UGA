<%@page import="m2pcci.im2ag.MyTheater.dao.PlacesDAO"%>
<%@page import="m2pcci.im2ag.MyTheater.model.Ticket"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <title>Récapitulatif achat</title>
        <link rel="stylesheet" type="text/css" href="styles/mesStyles.css">
        <script src="js/achatPlaces.js"></script>        
        <link rel="shortcut icon" href="images/icon.png">
        <link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%@include file="../includes/header.jspf" %>

        <div class="container-fluid">
            <div class="row container-fluid justify-content-md-left">
                <div class="col col-md-7">
                    <div class="container-fluid">
                        <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar_recapTicket">
                            <table class="table table-fixed table-bordered table-striped" style='table-layout: auto;' >                   
                                <thead class="thead-dark">                        
                                <th>Pièce</th><th>Date</th><th>Heure</th><th>N° de zone</th><th>N° de rang</th><th>N° de place</th><th>Profil reduction</th>
                                </thead>
                                <tbody>
                                    <%                                        session = request.getSession();

                                        List<Ticket> listeTickets = (List<Ticket>) session.getAttribute("listeTicket");
                                        if (listeTickets.size() == 0) {
                                            out.println("<tr>");
                                            out.println("<td> Aucun résultat trouvé" + "</td>");
                                            out.println("</tr>");
                                        } else {
                                            for (Ticket t : listeTickets) {

                                                out.println("<tr>");
                                                out.println("<td>" + t.getRepresentation().getSpectacle().getNomSpec() + " " + "</td>");
                                                out.println("<td>" + t.getRepresentation().getDateRepr() + " " + "</td>");
                                                out.println("<td>" + t.getRepresentation().getHeureRepr() + " " + "</td>");
                                                out.println("<td>" + t.getPlace().getNumeroZone() + " " + "</td>");
                                                out.println("<td>" + t.getPlace().getNumeroRang() + " " + "</td>");
                                                out.println("<td>" + t.getPlace().getNumeroPlace() + " " + "</td>");
                                                out.println("<td>" + t.getProfilSpectateur() + " " + "</td>");
                                            }
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
          


            <div class="col container-fluid col-md-4 my-custom-scrollbar_paiement">   
                <fieldset>
                    <legend>Informations bancaires</legend>
                    <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar">
                        <table class="table table-bordered">
                            <thead class="thead-dark">
                            <th>Nom de la banque</th><th>Numero du compte bancaire</th>
                            </thead>
                            <tbody>
                                <%
                                    out.println("<tr>");
                                    out.println("<td>" + session.getAttribute("nomBanque") + " " + "</td>");
                                    out.println("<td>" + session.getAttribute("numeroCompteBancaire") + " " + "</td>");
                                %>
                            </tbody>
                        </table> 
                    </div>
                </fieldset>

                <form id="achatForm" action="payerDossier" method="POST">
                    <fieldset>
                        <legend>Informations panier</legend>
                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Panier N°</span>
                                <input style="text-align:center" class="text-align-right" type="text" name="numeroDossier" id="numeroDossier" value='<% out.println(session.getAttribute("numeroDossier"));%>' readonly/>
                            </div>
                        </div>

                        <div class="input-group input-group-sm mb-3">
                            <div class="input-group-prepend" >
                                <span class="input-group-text">Montant restant à payer :</span>
                                <input style="text-align:center" type="text" id="montant" value="<% out.println(session.getAttribute("prixTotalDossier"));%>" readonly/>
                                <span>euros</span>
                            </div>
                        </div>
                        <button class="btn btn-primary" type="submit" id="Payer" value="Valider le paiement">Valider le paiement</button>
                    </fieldset>
                </form>

                <br>

                <div>

                    <form action="TicketsCtrler" id="envoyerTicket" style="display: <%out.println(request.getAttribute("affichage"));%>">  
                        <fieldset>
                            <legend>Obtenir les tickets</legend>

                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Titulaire</span>
                                    <input type="text" name="titulaireBillet" id="titulaireBillet" placeholder="A saisir..."  required value=""> 
                                </div>
                            </div>

                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">eMail</span>
                                    <input name="email" id="email" value="<%out.println(session.getAttribute("coordonnees"));%>" readonly >
                                </div>

                            </div>
                            <div>        


                                <div class="btn-group" role="group" aria-label="bouton2">
                                    <input type="submit" class="btn btn-primary" value="Version mail">
                                    <input type="submit" class="btn btn-primary" value="Version PDF téléchargeable" disabled>
                                </div>

                            </div>
                        </fieldset>
                    </form>     

                </div>
            </div>
        </div>
    </div>



    <%@include file="../includes/sidebar.jspf" %>
    <%@include file="../includes/footer.jspf" %>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>

</body>
</html>