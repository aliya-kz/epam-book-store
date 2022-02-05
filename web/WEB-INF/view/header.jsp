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
<fmt:message bundle="${content}" key="SALE" var="sale" />
<fmt:message bundle="${content}" key="DELIVERY" var="delivery" />
<fmt:message bundle="${content}" key="CONTACTS" var="contacts" />
<fmt:message bundle="${content}" key="SIGN_UP" var="sign_up"/>
<fmt:message bundle="${content}" key="FOR_NEW_USER" var="for_new_user"/>
<fmt:message bundle="${content}" key="USER_IS_BLOCKED" var="is_blocked"/>
<fmt:message bundle="${content}" key="ADMIN" var="admin"/>
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen"/>
<fmt:message bundle="${content}" key="FORGOT_PASSWORD" var="forgot_pass"/>


<header class = "header">
    <jsp:include page="/lang-bar"></jsp:include>
</header>

    <div class = "logo">
        <p><a href="/index">The Reader</a></p>
    </div>

    <nav class = "nav" id = "header_nav">
        <ul class = "nav-menu">
            <li>
                <div onmouseover="showEl('drop-down-categories')" onmouseleave="hideEl('drop-down-categories')" width="120px">
                    <a href = "#"><c:out value = "${cat}"/></a>
                    <div id="drop-down-categories">
                        <ul>
                            <c:forEach var="category" items="${categories}">
                                <li><a href="/books/${categoryId}"><c:out value="${category.categoryName}"/></a></li>                  </a>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

            </li>
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

        <a href = "/cart"><div class = "cart"></div></a>
    </nav>
    </form>

</header>
</body>
</html>