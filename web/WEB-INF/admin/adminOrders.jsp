<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="entity.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>admin-books</title>
    </head>
<body>
<jsp:include page="/admin/adminHeader"/>

    <% Locale locale = (Locale) session.getAttribute("locale");
    System.out.println("locale in adminbooks " + locale);
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
%>
<form action = "<%= request.getContextPath()%>/controller" method = "post">
   <body>

</body>
</form>
</html>
