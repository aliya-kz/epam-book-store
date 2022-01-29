<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="/WEB-INF/admin/adminHeader.jsp"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="WELCOME_ADMIN" var="welcome_admin" />


<main class="admin-main">
    <p><c:out value="${welcome_admin}"/></p>
</main>
</body>
</html>
