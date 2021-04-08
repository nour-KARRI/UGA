<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="shortcut icon" href="images/icon.png">
        <script src="//code.jquery.com/jquery-3.1.1.min.js"></script>
        <title>Recuperation d'un Panier d'achat</title>
        <link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
        <script src="js/programmation.js"></script>
    </head>
    <body>

        <%@include file="includes/header.jspf" %>

        <h1>Récupérer et accéder à son panier</h1>

        <div class="container-fluid justify-content-center-md"  >
            <div class="row justify-content-center-md">
                <div class="col col-md-8">
                    <% if (request.getAttribute("erreur") != null) {
                            out.println("<h1 class=\"alert alert-danger\">" + request.getAttribute("erreur") + "</h1>");
                        }%>

                </div>
            </div>
            <div class="row justify-content-center-md">
                <div class="col justify-content-center-md">

                    <form name="versEspacePanier" action="RecuperationPanier" method="POST">
                        <fieldset class="justify-content-center-md" style="width:400px;">
                            <legend>Renseignez le numéro de Panier reçu par mail</legend>

                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">N° de panier</span>
                                    <input type="number" name="numeroDossier" id="numeroDossier" value="<%=session.getAttribute("numeroDossier")%>" required />
                                </div>
                            </div>
                            <span>
                                </form>
                                <form>
                                    <div>
                                        <input class="btn btn-secondary" formaction="touteLaProg"  type="submit" value="Retour"/>
                                    </div>
                                </form>
                                <input class="btn btn-danger" type="submit"  value="Envoyer">
                            </span>
                        </fieldset>
                </div>
            </div>
        </div>

        <%@include file="includes/sidebar.jspf" %>
        <%@include file="includes/footer.jspf" %>
        <script src="js/panierSauvegarde.js"></script>
    </body>
</html>

