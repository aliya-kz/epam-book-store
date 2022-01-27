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
    <title>header</title>
</head>
<body>
<%User user = (User) session.getAttribute("user");
System.out.println (" user" + user);
%>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="LOG_IN" var="log_in" />
<fmt:message bundle="${content}" key="LOG_OUT" var="log_out" />
<fmt:message bundle="${content}" key="CATEGORY" var="category" />
<fmt:message bundle="${content}" key="NEW_ARRIVALS" var="arrivals" />
<fmt:message bundle="${content}" key="SALE" var="sale" />
<fmt:message bundle="${content}" key="DELIVERY" var="delivery" />
<fmt:message bundle="${content}" key="CONTACTS" var="contacts" />
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
                <button class="logout" name="service_name" value="log_out">${log_out}</button></form></li>
            <li class = "log">

                <form action = "<%= request.getContextPath()%>/controller" method = "post">
                    <c:if test = "${user == null}">
                        <a href = "/login"><c:out value="${log_in}"/></a>
                    </c:if>
                    <c:if test = "${user != null}">
                        <button class = "logout" name = "service_name" value = "log_out"><c:out value="${log_out}"/></button>
                    </c:if>
                </form>
            </li>
        </ul>
    </div>

    <div class = "logo">
        <p><a href="/index">The Reader</a></p>
    </div>

    <form action = "<%=request.getContextPath()%>/controller" method = "post">
    <nav class = "nav" id = "header_nav">
        <ul class = "nav-menu">
            <li > <a href = "#"><c:out value = "${category}"/></a></li>
            <li> <a href = "#"><c:out value = "${arrivals}"/></a></li>
            <li> <a href = "#"><c:out value = "${sale}"/></a></li>
            <li> <a href = "#"><c:out value = "${delivery}"/></a></li>
            <li> <a href = "#"><c:out value = "${contacts}"/></a></li>
        </ul>
        <div class = "search">
            <input type = "search"/>
            <button> </button>
        </div>

        <button class = "cart"><a href = "/cart"></a></button>
        <button class = "wish-list">
           <div class = "wish-list">
               <c:if test="${user != null}">
                   <a href = "/wish-list?id=${user.id}"></a>
               </c:if>
               <c:if test="${user != null}">
                   <a href = "/wish-list?id=${user.id}"></a></button>
               </c:if>
           </div>
    </nav>
    </form>
</header>
</body>
</html>