<%@ page import="entity.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <title>header</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in" />
<fmt:message bundle="${content}" key="LOG_OUT" var="log_out" />
<fmt:message bundle="${content}" key="CATEGORY" var="catgeory" />
<fmt:message bundle="${content}" key="NEW_ARRIVALS" var="arrivals" />
<fmt:message bundle="${content}" key="SALE" var="sale" />
<fmt:message bundle="${content}" key="DELIVERY" var="delivery" />
<fmt:message bundle="${content}" key="CONTACTS" var="contacts" />
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
                <button class="logout" name="service_name" value="log_out">${log_out}</button></form></li>
            <% User currentUser = (User) session.getAttribute("current_user");%>
            <c:if test="${current_user == null}">
                <li class="log-in"> <a href="/login">${log_in}</a></li>
            </c:if>
            <c:if test="${current_user != null}">
                <li class="log-out"> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                    <button class="logout" name="service_name" value="log_out">${log_out}</button>
                </form></li>
                <jsp:useBean id ="current_user" class = "entity.User" scope="session"/>
                <jsp:setProperty name="current_user" property="*"/>
            </c:if>
        </ul>
    </div>

    <div class="logo">
        <p><a href="/index">BookLovers</a></p>
    </div>

    <form action = "<%= request.getContextPath()%>/controller" method = "post">
    <nav class="nav">
        <ul class="nav-menu">
            <li> <a href="#">${catgeory}</a></li>
            <li> <a href="#">${arrivals}</a></li>
            <li> <a href="#">${sale}</a></li>
            <li> <a href="#">${delivery}</a></li>
            <li> <a href="#">${contacts}</a></li>
        </ul>
        <div class="search">
            <input type = "search" id = "search"/>
            <button> </button>
        </div>
            <button class="basket"><a href="#"></a></button>
    </nav>
    </form>
</header>
</body>
</html>