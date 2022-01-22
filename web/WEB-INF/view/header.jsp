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
%>
<header class = "header">
    <div class="bar">
        <ul class="bar-list">
            <li><form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "lang" value="en"/>
                <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/> </form></li>
            <li> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "lang" value="ru"/>
                <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form></li>
           <% if (session.getAttribute("current_user") == null) {%>
            <li class="login"> <a href="/login"><%=bundle.getString("LOG_IN")%></a></li>
            <%} else { %>
            <jsp:useBean id ="current_user" class = "entity.User" scope="session"/>
            <jsp:setProperty name="current_user" property="*"/>
            <li class="logout"> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <button class="logout" name="service_name" value="log_out"><%=bundle.getString("LOG_OUT")%></button>
            </form></li>
            <%}%>
        </ul>
    </div>

    <div class="logo">
        <p><a href="/index">BookLovers</a></p>
    </div>
    <form action = "<%= request.getContextPath()%>/controller" method = "post">
    <nav class="nav">
        <ul class="nav-menu">
            <li> <a href="#"><%=bundle.getString("CATEGORY")%></a></li>
            <li> <a href="#"><%=bundle.getString("NEW_ARRIVALS")%></a></li>
            <li> <a href="#"><%=bundle.getString("SALE")%></a> </li>
            <li> <a href="#"><%=bundle.getString("DELIVERY")%></a></li>
            <li> <a href="#"><%=bundle.getString("CONTACTS")%></a></li>
        </ul>
        <div class="search">
            <input type="search" id="search"/>
            <button> </button>
        </div>
            <button class="basket"><a href="#"></a></button>
    </nav>
    </form>
</header>
</body>
</html>