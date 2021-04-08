<%-- 
    Document   : newjsp
    Created on : 19 mars 2019, 23:02:17
    Author     : antoine
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link href="styles/mesStyles.css" rel="stylesheet" type="text/css"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demo mail</title>
        <link href="css/demowebmail.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <%@include file="../includes/header.jspf" %>
        <h2 class="messageErreur">Désolé!<br>Votre achat n'as pu être pris en compte.</h2>
        <p>Vos billets n'ont pas pu vous être envoyé par email à l'adresse <code>${param.email}</code>
            <% session.invalidate(); %>
       </p>
       <p>
           <a href="touteLaProg">Acheter un autre ticket</a>.
       </p>
        <%@include file="../includes/sidebar.jspf" %>
        <%@include file="../includes/footer.jspf" %>
    </body>
</html>
