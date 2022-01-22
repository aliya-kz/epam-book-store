<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <title>WelcomeAdmin</title>
</head>
<body>

<% Locale locale = (Locale) session.getAttribute("locale");
if (locale == null) {
    locale = Locale.ENGLISH;
    session.setAttribute("locale", locale);
}
ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
    System.out.println(("locale " + locale));
    System.out.println("page req uri "+ request.getRequestURI());
%>

<% session.setAttribute("uri", request.getRequestURI());
    System.out.println("session id " + session.getId());%>
<jsp:include page="/admin/adminHeader"/>

<main class="admin-main">
    <p><%=bundle.getString("WELCOME_ADMIN")%>></p>
</main>
</body>
</html>
