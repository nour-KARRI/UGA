<%-- 
    Document   : ConfirmationPreresevation
    Created on : 21 mars 2019, 11:44:12
    Author     : bannouny
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="../includes/header.jspf" %>
        <h1>Confirmation de Votre Prereservation</h1>

        <p>Votre numero de Dossier est le <% out.println(request.getAttribute("numeroDossier")); %> </p>
        <p>Vous receverez un mail de confirmation de votre Pre-reservation</p>
        <a href="touteLaProg">Retour à la consultation</a>
         <%@include file="../includes/sidebar.jspf" %>
        <%@include file="../includes/footer.jspf" %>
    </body>
</html>
