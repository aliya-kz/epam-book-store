<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<html>
<head>
    <script src="https://kit.fontawesome.com/d45bb5fe4a.js" crossorigin="anonymous"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/formStyle.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <script src="/js/script.js"></script>
    <script src="/js/validation.js"> </script>
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
<fmt:message bundle="${content}" key="CATEGORY" var="cat" />
<fmt:message bundle="${content}" key="NEW_ARRIVALS" var="arrivals" />
<fmt:message bundle="${content}" key="DELIVERY" var="delivery" />
<fmt:message bundle="${content}" key="CONTACTS" var="contacts" />
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="FOR_NEW_USER" var="for_new_user"/>
<fmt:message bundle="${content}" key="USER_IS_BLOCKED" var="is_blocked"/>
<fmt:message bundle="${content}" key="ADMIN" var="admin"/>
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen"/>
<fmt:message bundle="${content}" key="FORGOT_PASSWORD" var="forgot_pass"/>
<fmt:message bundle="${content}" key="HOME" var="home"/>
<fmt:message bundle="${content}" key="AUTHORS" var="auths"/>


<header class = "header-header">
    <jsp:include page="/lang-bar"></jsp:include>
    <div class="nav-container">
        <nav class = "nav-menu" id = "header_nav">
            <ul>
                <li> <a href = "/index"><c:out value = "${home}"/></a></li>
                <li><a href = "#"><c:out value = "${cat}"/></a>
                    <ul>
                        <c:forEach var="category" items="${categories}">
                            <li><a href="/books"><c:out value="${category.categoryName}"/> </a></li>
                        </c:forEach>
                    </ul>
                </li>
                <li> <a href = "/authors"><c:out value = "${auths}"/></a></li>
                <li> <a href = "#"><c:out value = "${delivery}"/></a></li>
                <li> <a href = "#"><c:out value = "${contacts}"/></a></li>
            </ul>
        </nav>

        <div class="cart-wl">
            <a href = "/cart"><i class="fas fa-shopping-cart fa-2x"></i></a>
            <c:choose>
                <c:when test="${not empty user}">
                    <a href = "/wish-list"><i class="fab fa-gratipay fa-2x"></i></a>
                </c:when>
                <c:otherwise>
                    <button class = "fa-btn" onclick="openForm('login-form')"> <i class="fab fa-gratipay fa-2x"></i></button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
</body>
</html>