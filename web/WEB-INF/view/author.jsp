<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <title>Author</title>
</head>
<body>
<% request.setAttribute("id", request.getParameter("id"));
request.setAttribute("uri", request.getRequestURI());%>
<jsp:include page="/header"/>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle = "${content}" key="BOOKS_BY_AUTHOR" var="books_by_author"/>
<fmt:message bundle = "${content}" key="QUANTITY" var="quantity"/>
<fmt:message bundle = "${content}" key="ADD_TO_CART" var="add_to_cart"/>
<fmt:message bundle = "${content}" key="AVAILABLE" var="available"/>
<fmt:message bundle = "${content}" key="OUT_OF_STOCK" var="out_of_stock"/>
<fmt:message bundle = "${content}" key="ITEMS_LEFT" var="left"/>

<c:forEach var="auth" items="${authors}">
    <c:if test = "${auth.id == id}">
        <jsp:useBean id = "author" scope="request" class="entity.Author"/>
        <jsp:setProperty name="author" property="id" value="${auth.id}"/>
        <jsp:setProperty name="author" property="fullName" value="${auth.fullName}"/>
        <jsp:setProperty name="author" property="biography" value="${auth.biography}"/>
        <jsp:setProperty name="author" property="image" value="${auth.image}"/>
    </c:if>
</c:forEach>

        <main class = "main">
            <section id = "author">
                <img src="/image-servlet?image_id=${author.id}&table=authors" alt = "${author.fullName}" width="250px"/>
                <div class = "author-bio">
                    <div class="title"> <c:out value="${author.fullName}"/> </div>
                    <p class = "author-bio">
                        <c:out value="${author.biography}"/>
                    </p>
                </div>
            </section>

            <h2> <c:out value="${books_by_author}"/> </h2>

            <section class="book-desc" id = "index-books">
                <c:forEach var="book" items="${books}">
                    <c:set var = "bookAuthIds" value="${book.authors}"/>
                    <c:forEach var="bookAuthId" items="${bookAuthIds}">
                        <c:if test = "${bookAuthId == author.id}">
                            <div class = "index-book">
                                <img src="/image-servlet?image_id=${book.id}&table=book_covers" width = "200px" alt = "${book.title}"><br>
                                <a href="/book?id=${book.id}" class="index-book-name">${book.title}</a><br>
                                <a href="/author?id=${author.id}" class="index-author-name">${author.fullName}</a><br>
                                <c:forEach var="format" items="${formats}">
                                    <c:if test="${format.id == book.formatId}">
                                        <c:out value="${format.formatName}"/>
                                    </c:if>
                                </c:forEach>
                                <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </section>
</main>
</body>
</html>

