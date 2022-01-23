<%@ page import="java.util.Locale" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <title>LogIn</title>
</head>
<body>


<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in" />
<fmt:message bundle="${content}" key="ENTER_EMAIL" var="enter_email" />
<fmt:message bundle="${content}" key="ENTER_PASSWORD" var="enter_password" />
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="FOR_NEW_USER" var="for_new_user"/>
<% String msg = request.getParameter("msg");
%>

<header class = "header">
    <div class="bar">
        <ul class="bar-list">
            <li><form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "locale" value ="en_US"/>
                <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/> </form></li>
            <li> <form action = "<%=request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "locale" value = "ru_RU"/>
                <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form></li>
        </ul>
    </div>

    <div class = "login-container">
        <h1> <c:out value="${log_in}"/> </h1>
        <div class = "msg-div"></div>

        <form id = "login-form" action = "<%= request.getContextPath()%>/controller" method = "post">
            <div id="error"></div>
            <input id = "email" type = "email" name = "email" placeholder="${enter_email}" required/></br>
        <input id = "password" type = "password" name = "password" placeholder="${enter_password}" required/></br>
        <input type="hidden" name="service_name" value="log_in">
        <input type = "submit" value="${log_in}">
    </form>


    <h2> <c:out value="${for_new_user}"/> </h2>
    <a href="/signup"> <c:out value="${sign_up}"/> </a>


</div>
</header>
</body>
</html>
