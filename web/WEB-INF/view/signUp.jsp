<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/css/formStyle.css"/>
    <script src="/js/validation.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/d45bb5fe4a.js" crossorigin="anonymous"></script>
    <title>Registration</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="EMAIL" var="email"/>
<fmt:message bundle="${content}" key="PASSWORD" var="password"/>
<fmt:message bundle="${content}" key="NAME" var="name"/>
<fmt:message bundle="${content}" key="SURNAME" var="surname"/>
<fmt:message bundle="${content}" key="ADDRESS" var="address"/>
<fmt:message bundle="${content}" key="PHONE_NUMBER" var="phone"/>
<fmt:message bundle="${content}" key="DATE_OF_BIRTH" var="date"/>
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="CREATE_ACCOUNT" var="create_account"/>
<fmt:message bundle="${content}" key="CARD_NUMBER" var="card"/>
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen"/>
<fmt:message bundle="${content}" key="ERROR_EMAIL_EXISTS" var="error_email"/>
<fmt:message bundle="${content}" key="SIGN_UP_COMPLETED" var="successful"/>
<fmt:message bundle="${content}" key="CONFIRM_PASSWORD" var="confirm_password"/>
<fmt:message bundle="${content}" key="LOG_IN" var="login"/>
<fmt:message bundle="${content}" key="HOME" var="home"/>

<header class="header">
    <div class="bar">
        <ul class="bar-list">
            <li><a href="/index"><i class="fas fa-home fa-2x"></i></a></li>
            <li><a href="/login"><i class="fas fa-user fa-lg"></i></a></li>
            <li>
                <form action="<%= request.getContextPath()%>/controller" method="post">
                    <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                    <input type="hidden" name="locale" value="en_US"/>
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
<main class="signup-main">
    <section class="signup-container">
        <div class="signup-header">
            <h1><c:out value="${create_account}"/></h1>
        </div>
        <form class="signup-form" id="signup-id" onsubmit="return checkInputs(this)"
              action="<%= request.getContextPath()%>/controller" method="post">
            <div class="form-control">
                <label for="name"><c:out value="${name}"/> </label>
                <input id="name" type="text" name="name" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="surname"><c:out value="${surname}"/> </label>
                <input id="surname" type="text" name="surname" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="email"><c:out value="${email}"/> </label>
                <input id="email" type="email" name="email" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="phone"><c:out value="${phone}"/> </label>
                <input id="phone" type="text" name="phone" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="address"><c:out value="${address}"/> </label>
                <input id="address" type="text" name="address" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="date"><c:out value="${date}"/> </label>
                <input id="date" type="date" name="date_of_birth" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="card"><c:out value="${card}"/> </label>
                <input id="card" type="text" name="card" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="password"><c:out value="${password}"/> </label>
                <input id="password" type="password" name="password" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>

            <div class="form-control">
                <label for="password1"><c:out value="${confirm_password}"/> </label>
                <input id="password1" type="password" name="password1" required>
                <i class="fas-fa-check-circle"></i>
                <i class="fas-fa-check-exclamation-circle"></i>
                <small>Error message</small>
            </div>
            <input type="hidden" name="service_name" value="sign_up"/>
            <button type="submit" id="submit-btn" onclick="checkInputs()"><c:out value="${sign_up}"/></button>
        </form>
    </section>
    <section class="msg-section">
        <%
            String msg = request.getParameter("msg");
            request.setAttribute("msg", msg);
            ;
        %>
        <p>
            <c:choose>
                <c:when test="${msg == 'error'}">
                    <c:out value="${error_gen}"/>
                </c:when>
                <c:when test="${msg == 'user_exists'}">
                    <c:out value="${error_email}"/>
                </c:when>
                <c:when test="${msg == 'success'}">
                    <c:out value="${successful}"/> <br>
                </c:when>
            </c:choose>
        </p>
    </section>
</main>
</body>
</html>

