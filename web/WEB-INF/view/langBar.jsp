<%@ page import="entity.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script src="https://kit.fontawesome.com/d45bb5fe4a.js" crossorigin="anonymous"></script>
    <title>lang bar</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/formStyle.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <script src="/js/script.js"></script>
    <script src="/js/validation.js"></script>
</head>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in"/>
<fmt:message bundle="${content}" key="LOG_OUT" var="log_out"/>
<fmt:message bundle="${content}" key="EMAIL" var="email"/>
<fmt:message bundle="${content}" key="PASSWORD" var="password"/>
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="FOR_NEW_USER" var="for_new_user"/>
<fmt:message bundle="${content}" key="USER_IS_BLOCKED" var="is_blocked"/>
<fmt:message bundle="${content}" key="ADMIN" var="admin"/>
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen"/>
<fmt:message bundle="${content}" key="FORGOT_PASSWORD" var="forgot_pass"/>

<body>
<div class="bar">
    <ul class="bar-list">
        <li>
            <form action="<%= request.getContextPath()%>/controller" method="post">
                <input type="hidden" name="locale" value="en_US"/>
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/></form>
        </li>
        <li>
            <form action="<%= request.getContextPath()%>/controller" method="post">
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <input type="hidden" name="locale" value="ru_RU"/>
                <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form>
        </li>
        <li id="for-signed-user">
            <c:choose>
                <c:when test="${empty user}">
                    <button class="fa-btn" onclick="openForm('login-form')"><i class="fas fa-user fa-lg"></i></button>
                </c:when>
                <c:otherwise>
                    <a href="/profile"><i class="fas fa-user fa-lg"></i></a>
                </c:otherwise>
            </c:choose>
        </li>
        <c:if test="${not empty user}">
            <li>
                <form action="<%= request.getContextPath()%>/controller" method="post">
                    <button class="fa-btn" name="service_name" value="log_out"><i class="fas fa-sign-out-alt fa-lg"></i>
                    </button>
                </form>
            </li>
        </c:if>
    </ul>
</div>

<a href="/index">
    <div class="logo">The Reader</div>
</a>

<form id="login-form" action="<%= request.getContextPath()%>/controller" method="post">
    <h1><c:out value="${log_in}"/></h1>
    <label for="email"><c:out value="${email}"/></label>
    <input id="email" type="email" name="email" placeholder="${enter_email}" required/><br>
    <label for="password"><c:out value="${password}"/></label>
    <input id="password" type="password" name="password" placeholder="${enter_password}" required/><br>
    <input type="hidden" name="service_name" value="log_in">
    <input type="submit" class="btn" value="${log_in}">
    <button type="button" class="btn cancel" onclick="closeForm('login-form')">Close</button>
    <h2><c:out value="${forgot_pass}"/></h2>
    <h2><c:out value="${for_new_user}"/></h2>
    <a href="/signup"> <c:out value="${sign_up}"/> </a>
</form>
<div class="error">
    <%
        String msg = request.getParameter("msg");
        request.setAttribute("msg", msg);
    %>
    <c:if test="${msg eq 'wrong'}">
        <c:out value="${wrong}"/>
    </c:if>
    <c:if test="${msg eq 'blocked'}">
        <c:out value="${is_blocked}"/>
        <a href="mailto@zhumagul100@gmail.com"><c:out value="${admin}"></c:out></a>
    </c:if>
</div>
</body>
</html>
