
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/validation.js"> </script>
    <title>Books</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>
<jsp:include page="/header"/>

<main class = "books-main">
    <section class="filter">
        filter
    </section>

    <section id = "index-books">
        <c:forEach var="book" items="${books}">
            <div class = "index-book">
                <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" width="170px"/>
                <a href="#" class="index-book-name">${book.title}</a>
                <c:set var="authorIds" value="${book.authors}"/>
                <c:forEach var="authorId" items="${authorIds}">
                    <c:forEach var="auth" items="${authors}">
                        <c:if test = "${authorId == auth.id}">
                            <a href="/book?id=${book.id}" class="index-author-name">${auth.fullName}</a>
                        </c:if>
                    </c:forEach>
                </c:forEach>
                <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
            </div>
        </c:forEach>
    </section>
</main>
</body>
</html>
</body>
</html>
