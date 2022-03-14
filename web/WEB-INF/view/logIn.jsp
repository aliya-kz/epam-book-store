<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/formStyle.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <title>LogIn</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in"/>
<fmt:message bundle="${content}" key="ENTER_EMAIL" var="enter_email"/>
<fmt:message bundle="${content}" key="ENTER_PASSWORD" var="enter_password"/>
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="FOR_NEW_USER" var="for_new_user"/>
<fmt:message bundle="${content}" key="USER_IS_BLOCKED" var="is_blocked"/>
<fmt:message bundle="${content}" key="ADMIN" var="admin"/>
<fmt:message bundle="${content}" key="WRONG" var="wrong"/>
<fmt:message bundle="${content}" key="FORGOT_PASSWORD" var="forgot_pass"/>
<fmt:message bundle="${content}" key="HOME" var="home"/>

<header class="header">
    <div class="bar">
        <ul class="bar-list">
            <li><a href="/index"><c:out value="${home}"/></a></li>
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
        </ul>
    </div>
</header>

<main class="login-main">
    <div class="form-container">
        <form id="login-page" action="<%= request.getContextPath()%>/controller" method="post">
            <h1><c:out value="${log_in}"/></h1>
            <div class="msg-div"></div>
            <label for="email"><c:out value="${email}"/></label>
            <input id="email" type="email" name="email" placeholder="${enter_email}" required/><br>
            <label for="password"><c:out value="${password}"/></label>
            <input id="password" type="password" name="password" placeholder="${enter_password}" required/><br>
            <input type="hidden" name="service_name" value="log_in">
            <input type="submit" class="btn" value="${log_in}">

            <h2><c:out value="${forgot_pass}"/></h2>
            <h2><c:out value="${for_new_user}"/></h2>
            <a href="/signup" id="sign-up-link"> <c:out value="${sign_up}"/> </a>
        </form>
        <div class="error">
            <%
                String msg = request.getParameter("msg");
                request.setAttribute("msg", msg);
                ;
            %>
            <c:if test="${msg eq 'wrong'}">
                <c:out value="${wrong}"/>
            </c:if>
            <c:if test="${msg eq 'blocked'}">
                <c:out value="${is_blocked}"/>
                <a href="mailto@zhumagul100@gmail.com"><c:out value="${admin}"></c:out></a>
            </c:if>
        </div>
    </div>
</main>
</body>
</html>
