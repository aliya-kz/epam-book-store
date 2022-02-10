<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <script src="https://kit.fontawesome.com/d45bb5fe4a.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <title>WelcomeAdmin</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in" />
<fmt:message bundle="${content}" key="LOG_OUT" var="log_out" />
<fmt:message bundle="${content}" key="BOOKS" var="books" />
<fmt:message bundle="${content}" key="CATEGORIES" var="categories" />
<fmt:message bundle="${content}" key="USERS" var="users" />
<fmt:message bundle="${content}" key="ORDERS" var="ords" />
<fmt:message bundle="${content}" key="MESSAGES" var="messages" />
<fmt:message bundle="${content}" key="AUTHORS" var="authors" />
<fmt:message bundle="${content}" key="ANALYTICS" var="analytics" />
<header class = "header-header">
    <div class="bar">
    <ul class="bar-list">
        <li><form action = "<%= request.getContextPath()%>/controller" method = "post">
            <input type = "hidden" name = "locale" value = "en_US"/>
            <input type = "hidden" name = "uri" value="<%=request.getRequestURI()%>"/>
            <input class="lang" id = "eng" type="submit" name = "service_name" value = "change_language"/> </form></li>
        <li> <form action = "<%= request.getContextPath()%>/controller" method = "post">
            <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
            <input type = "hidden" name = "locale" value="ru_RU"/>
            <input class="lang" id ="rus" type = "submit" name = "service_name" value = "change_language"/></form></li>
        <li>
            <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <button class="fa-btn" name="service_name" value="log_out">
                    <i class="fas fa-sign-out-alt fa-lg"></i></button></form>
        </li>
    </ul>
    </div>

<form action = "<%=request.getContextPath()%>/controller" method = "post">
    <div class="nav-container">
    <nav class="nav-menu">
        <ul>
            <li> <button name="service_name" value="get_all_books"><c:out value="${books}"/> </button></li>
            <li> <button name="service_name" value="get_all_categories"><c:out value="${categories}"/> </button></li>
            <li> <button name="service_name" value="get_all_users"><c:out value="${users}"/>  </button></li>
            <li> <button name="service_name" value="get_all_orders"><c:out value="${ords}"/>  </button></li>
            <li> <button name="service_name" value="get_all_authors"><c:out value="${authors}"/>  </button></li>
            <li> <button name="service_name" value="get_all_messages"><c:out value="${messages}"/>  </button></li>
        </ul>
    </nav>
    </div>
</form>
</header>
</body>
</html>
