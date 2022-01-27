<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <title>welcome</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>

<main class = "welcome-main">
    <p id="logo">The Reader</p>
    <div class="welcome-form-container">
    <form id = "welcome-form" action = "<%= request.getContextPath()%>/controller" method = "post">
        <select name = "locale">
            <option value = "en_US">English</option>
            <option value = "ru_RU">Русский</option>
        </select>
        <input  type="hidden" name = "service_name" value = "welcome"/>
    <button class = "go-btn"> GO </button>
    </form>
    </div>
</main>
</body>
</html>
