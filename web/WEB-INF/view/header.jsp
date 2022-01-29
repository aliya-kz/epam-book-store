<%@ page import="entity.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <script src="/js/script.js"></script>
    <title>header</title>
</head>
<div id="login-background"></div>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in" />
<fmt:message bundle="${content}" key="LOG_OUT" var="log_out" />
<fmt:message bundle="${content}" key="EMAIL" var="email" />
<fmt:message bundle="${content}" key="PASSWORD" var="password" />
<fmt:message bundle="${content}" key="CATEGORY" var="category" />
<fmt:message bundle="${content}" key="NEW_ARRIVALS" var="arrivals" />
<fmt:message bundle="${content}" key="SALE" var="sale" />
<fmt:message bundle="${content}" key="DELIVERY" var="delivery" />
<fmt:message bundle="${content}" key="CONTACTS" var="contacts" />
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="FOR_NEW_USER" var="for_new_user"/>
<fmt:message bundle="${content}" key="USER_IS_BLOCKED" var="is_blocked"/>
<fmt:message bundle="${content}" key="ADMIN" var="admin"/>
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen"/>
<c:set var="user" value="${requestScope.user}"/>
<header class = "header">
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
            <li class = "logout"> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type="hidden" name="service_name" value="log_out"/>
                <button class="logout"/></form></li>
            <li class = "log">
                    <c:if test = "${user eq null}">
                        <button class="login" onclick="openForm('login-form')"></button>
                    </c:if>
                    <c:if test = "${user ne null}">
                <form action = "<%= request.getContextPath()%>/controller" method = "post">
                    <input type="hidden" name="service_name" value="log_out"/>
                    <button class="logout" ></button></form></li>
                </form>
                    </c:if>
            </li>
        </ul>
    </div>

    <div class = "logo">
        <p><a href="/index">The Reader</a></p>
    </div>


    <nav class = "nav" id = "header_nav">
        <ul class = "nav-menu">
            <li > <a href = "#"><c:out value = "${category}"/></a></li>
            <li> <a href = "#"><c:out value = "${arrivals}"/></a></li>
            <li> <a href = "#"><c:out value = "${sale}"/></a></li>
            <li> <a href = "#"><c:out value = "${delivery}"/></a></li>
            <li> <a href = "#"><c:out value = "${contacts}"/></a></li>
        </ul>
        <form action = "<%=request.getContextPath()%>/controller" method = "post">
        <div class = "search">
            <input type = "search"/>
            <button> </button>
        </div>
        </form>

        <button class = "cart"><a href = "/cart"></a></button>


        <form id = "login-form" action = "<%= request.getContextPath()%>/controller" method = "post">
                <h1> <c:out value="${log_in}"/> </h1>
            <label for="email"><c:out value="${email}"/></label>
                    <input id = "email" type = "email" name = "email" placeholder="${enter_email}" required/></br>
                    <label for="password"><c:out value="${password}"/></label>
                    <input id = "password" type = "password" name = "password" placeholder="${enter_password}" required/></br>
                    <input type="hidden" name="service_name" value="log_in">
                    <input type = "submit" class = "btn" value="${log_in}">
                    <button type="button" class="btn cancel" onclick="closeForm('login-form')">Close</button>
            <h2><c:out value="${forgot_pass}"/> </h2>
            <h2><c:out value="${for_new_user}"/> </h2>
                <a href="/signup"> <c:out value="${sign_up}"/> </a>
        </form>


        <button class = "wish-list">
               <c:if test="${user != null}">
                   <a href = "/WEB-INF/view/wishlist.jsp?id=${user.id}"></a>
               </c:if>
               <c:if test="${user == null}">
                   <button onclick="openForm(login)"/>
               </c:if>
        </button>
    </nav>
    </form>
</header>
</body>
</html>