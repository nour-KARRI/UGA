<%-- 
    Document   : newjsp
    Created on : 19 mars 2019, 23:01:34
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
        <h2 class="messageOK">Merci de votre achat.</h2>
        <p>Vos billets vous ont été envoyés à l'adresse <code>${param.email}</code>
            <% session.invalidate();%>
    </p>
    <p>
        <a href="touteLaProg">Acheter d'autres billets</a>.
    </p>
    
     <%@include file="../includes/sidebar.jspf" %>
        <%@include file="../includes/footer.jspf" %>
</body>
</html>

