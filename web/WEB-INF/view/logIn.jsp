<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <script src="/js/validation.js"></script>
    <title>LogIn</title>
</head>
<body>
<% Locale locale = (Locale) session.getAttribute("locale");
    if (locale == null) {
        locale = Locale.ENGLISH;
        session.setAttribute("locale", locale);
    }
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
    String msg = request.getParameter("msg");
%>

<header class = "header">
    <div class="bar">
        <ul class="bar-list">
            <li><form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "lang" value="en"/>
                <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/> </form></li>
            <li> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "lang" value="ru"/>
                <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form></li>
        </ul>
    </div>

    <div class = "login-container">
        <h1> <%=bundle.getString("LOG_IN")%> </h1>
        <div class = "msg-div"></div>

        <form id = "login-form" action = "<%= request.getContextPath()%>/controller" method = "post">
            <div id="error"></div>
            <input id = "email" type = "email" name = "email" placeholder="<%=bundle.getString("ENTER_EMAIL")%>" required/></br>
        <input id = "password" type = "password" name = "password" placeholder="<%=bundle.getString("ENTER_PASSWORD")%>" required/></br>
        <input type="hidden" name="service_name" value="log_in">
        <input type = "submit" value="<%=bundle.getString("LOG_IN")%>">
    </form>


    <h2> <%=bundle.getString("FOR_NEW_USER")%> </h2>
    <a href="/signup"> <%=bundle.getString("SIGN_UP")%> </a>


</div>
</header>
</body>
</html>
