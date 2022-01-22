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
    if (locale==null) {
        locale=Locale.ENGLISH;
    }
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
%>
<header class = "header">
    <div class="bar">
        <ul class="bar-list">
            <li><form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "lang" value="en"/>
                <input type = "hidden" name = "uri" value="<%=request.getRequestURI()%>"/>
                <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/> </form></li>
            <li> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value="<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "lang" value="ru"/>
                <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form></li>
            <li class="logout"> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <button class="logout" name="service_name" value="log_out"><%=bundle.getString("LOG_OUT")%></button></form></li>
        </ul>
    </div>
    </header>
<form action = "<%=request.getContextPath()%>/controller" method = "post">
    <nav class="nav">
        <ul class="nav-menu">
            <li> <button class="admin-menu-btn" name="service_name" value="get_all_books"><%=bundle.getString("BOOKS")%> </button></li>
            <li> <button class="admin-menu-btn" name="service_name" value="get_all_categories"><%=bundle.getString("CATEGORIES")%></button></li>
            <li> <button class="admin-menu-btn" name="service_name" value="get_all_users"><%=bundle.getString("USERS")%> </button></li>
            <li> <button class="admin-menu-btn" name="service_name" value="get_all_orders"><%=bundle.getString("ORDERS")%> </button></li>
            <li> <button class="admin-menu-btn" name="service_name" value="get_all_messages"><%=bundle.getString("MESSAGES")%> </button></li>
            <li> <button class="admin-menu-btn" name="service_name" value="get_all_authors"><%=bundle.getString("AUTHORS")%> </button></li>
            <li> <button class="admin-menu-btn" name="service_name" value="get_analytics"><%=bundle.getString("ANALYTICS")%> </button></li>
        </ul>
    </nav>
</form>
    </header>
</body>
</html>
