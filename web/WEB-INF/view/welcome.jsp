<%@ page import="service.ChangeLanguageService" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/validation.js"> </script>
    <title>welcome</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>

<main class = "welcome-main">
    <div class="welcome-form-container">
    <form id = "welcome-form" action = "<%= request.getContextPath()%>/controller" method = "post">
        <select name = "locale">
               <option value ="en_EN">English</option>
              <option value ="ru_RU">Русский</option>
        </select>
        <input  id="eng" type="hidden" name="service_name" value="welcome"/>
    <button class = "go-btn"> GO </button>
    </form>
    </div>
</main>
</body>
</html>
