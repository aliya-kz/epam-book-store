<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <script src="https://kit.fontawesome.com/d45bb5fe4a.js" crossorigin="anonymous"></script>
    <title>Authors</title>
</head>
<body>
<jsp:include page="/header"/>
<fmt:setLocale value = "${sessionScope.locale}" />
<fmt:setBundle basename = "content" var = "content" scope = "session"/>
<fmt:message bundle="${content}" key="NOTHING" var="nothing"/>
<fmt:message bundle="${content}" key="OTHER_AUTHORS" var="other"/>

<main class="main">
<form action = "<%= request.getContextPath()%>/controller" method = "post">
<input type="search" name="search">
    <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
    <input type="hidden" name="service_name" value="search">
    <input type="hidden" name="table" value="authors">
    <button class="fa-btn"><i class="fas fa-search fa-lg"></i></button>
</form>

    <c:if test="${found ne null}">

    <div class="authors-search">
            <c:if test="${found.size() > 0}">
            <div class = "authors-item">
                <c:forEach var="author" items="${authors}">
                    <c:forEach var="authId" items="${found}">
                        <c:if test="${author.id == authId}">
                            <div class = "authors-item">
                                <img src = "/image-servlet?image_id=${author.id}&table=authors" alt = "${author.fullName}"
                                     style="max-height: 230px; max-width: 180px"><br>
                                <a href = "/author?id=${author.id}" class="index-book-name"><h2><c:out value = "${author.fullName}"/></h2></a>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${found.size() == 0}">
            <h1><c:out value="${nothing}"/> </h1><br>
            <div class="title"><c:out value="${other}"/></div>
        </c:if>
    </div>
    </c:if>

<section id = "authors">
    <c:forEach var="author" items="${authors}">
        <div class = "authors-item">
            <img src = "/image-servlet?image_id=${author.id}&table=authors" alt = "${author.fullName}"
                 style="max-height: 230px; max-width: 180px"><br>
            <a href = "/author?id=${author.id}" class="index-book-name"><h2><c:out value = "${author.fullName}"/></h2></a>
        </div>
    </c:forEach>
</section

</main>
<jsp:include page="/footer"/>
</body>
</html>
