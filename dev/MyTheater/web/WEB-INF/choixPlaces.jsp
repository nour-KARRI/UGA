<%-- 
    Document   : achatPlaces
    Cette page utilise le plugin jQuery jQuery Seat Charts pour afficher un
    plan de salle sur lequel l'utilisateur peut sélectionner ses places et
    les acheter.
    Cette page utilise JQuery pour à intervalles réguliers rafraichir le plan de
    salle afin de mettre à jour la liste des places disponibles.

    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>
<%@page import="m2pcci.im2ag.MyTheater.model.Representation"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Choix places</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="styles/mesStyles.css">
        <script src="js/achatPlaces.js"></script>        
        <link rel="shortcut icon" href="images/icon.png">
        <link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
        <link href="js/jQuery-Seat-Charts/jquery.seat-charts.css" rel="stylesheet" type="text/css"/>
        <link href="styles/stylesTheatre.css" rel="stylesheet" type="text/css"/>

    </head>
    <body>
        <%@include file="../includes/header.jspf" %>


        <h1 style="text-align: center">
            Pièce "${Representation.spectacle.nomSpec}" - Programmée le ${Representation.dateRepr} à ${Representation.heureRepr}
        </h1>

        <div class="wrapper">
            <div id="map-container">
                <!-- Le div qui contient le plan de la salle -->
                <div id="seat-map">
                    <div class="front-indicator">Scène</div>
                </div>
                <!-- Le div qui contient le récapitulatif des places sélectionnées par
                     l'utilisateur -->
                <div id="commande">
                    <div id="legend"></div>
                    <h2>Votre sélection de places</h2>
                    <p>Nombre de places: <strong><span id="nbplaces">0</span></strong></p>

                    <!--div pour les bouton d'action -->

                    <!--enregistrer en bdd et aller à la page panier -->
                    <div>
                        <button class="btn btn btn-primary btn-sm" id="achatBtn">Confirmer et terminer</button>
                    </div>


                    <!--enregistrer en bdd et aller à la page d'accueil pour sélectionner autre représentation -->
                    <div>
                        <button class="btn btn btn-secondary btn-sm "id="SauveBtn">Enregistrer et choisir une autre représentation</button> <br>
                    </div>                   

                    <div>
                        <!-- rien n'est sauvegardé et retour page consultation programmation -->
                        <button class="btn btn-danger btn-sm" type="submit" id="validerLaRecherche" onclick="window.location = '/MyTheater'">Annuler et revenir à l'accueil</button>
                    </div>
                </div>
            </div>
            <hr>

            <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar-places">
                <div class="row justify-content-md-center">
                    <div class="col-md-7">
                        <div class="container-fluid table-wrapper-scroll-y my-custom-scrollbar">
                            <table class="table table-fixed table-bordered table-striped" style='table-layout: auto;'>
                                <thead class="thead-dark">
                                <title> Places Choisies</title>
                                <th>N°</th><th>N° de Zone</th><th>N° de Rang</th><th>N° de place</th><th>Profil reduction</th>
                                </thead>
                                <tbody id="places">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>              
        </div>

        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="js/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
        <script src="js/achatPlaces.js" type="text/javascript"></script>
        <%@include file="../includes/sidebar.jspf" %>
        <%@include file="../includes/footer.jspf" %>
    </body>
</html>

