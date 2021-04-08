<%-- 
    Document   : coordonneesBancaires
    Created on : 20 mars 2019, 15:25:40
    Author     : antoine
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/mesStyles.css">
        <title>Informations bancaires</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="js/achatPlaces.js"></script>        
        <link rel="shortcut icon" href="images/icon.png">
        <link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>


    </head>
    <body>
        <%@include file="../includes/header.jspf" %>

        <div class="container-fluid">
            <div class="row justify-content-md-center">
                <div class="col-md">

                    <form id="coordonneesBancaires" action="RecapitulatifContrl" method="POST">
                        <fieldset>
                            <legend>Veuillez renseigner vos coordonnées bancaires</legend>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Nom Banque</span>                    
                                    <input type="text" name="nomBanque" id="nomBanque" required="true"/>

                                </div>
                            </div>

                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">Numéro de compte bancaire</span>                    
                                    <input type="number" name="numeroCompteBancaire" id="numeroCompteBancaire" required="true"/>
                                </div>
                            </div>

                            <div>
                                <button class="btn btn-primary btn-sm" type="submit" id="validerCoordonneesBancaires">Valider</button>
                            </div>
                            <div>
                                <button class="btn btn-secondary btn-sm" type="submit" disabled>Retour au panier</button>
                            </div>
                        </fieldset>
                    </form>


                    <%
                        session.setAttribute("coordonnees", request.getParameter("coordonnees"));
                    %>
                </div>
            </div>
        </div>

        <%@include file="../includes/sidebar.jspf" %>
        <%@include file="../includes/footer.jspf" %>

        <script src="js/achatPlaces.js" type="text/javascript"></script>
    </body>
</html>
