<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>wish-list</title>
</head>
<body>
<jsp:include page ="/header"/>
<fmt:setLocale value = "${sessionScope.locale}" />
<fmt:setBundle basename = "content" var = "content" scope = "session"/>
<fmt:message bundle="${content}" key="ADD_TO_CART" var="add"/>
<fmt:message bundle="${content}" key="ADDED" var="added"/>
<fmt:message bundle="${content}" key="DELETE" var="delete"/>
<fmt:message bundle="${content}" key="CONTINUE_SHOPPING" var="continue_shop"/>
<fmt:message bundle="${content}" key="OUT_OF_STOCK" var="out_of_stock"/>

<section id = "index-books">
    <c:forEach var="wl_book" items="${wishList.books}">
        <c:forEach var="book" items="${books}">
            <c:if test="${book.id == wl_book.id}">
                <div class = "index-book">
                    <img src = "/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" style="height: 230px; max-width: 180px"><br>
                    <a href = "/book?id=${book.id}" class="index-book-name"><c:out value = "${book.title}"/></a>
                    <c:set var = "authorIds" value = "${book.authors}"/>
                    <c:forEach var = "authorId" items = "${authorIds}">
                        <c:forEach var ="auth" items = "${authors}">
                            <c:if test = "${authorId == auth.id}">
                                <h4><a href = "/author?id=${auth.id}" class="index-author-name">${auth.fullName}</a></h4>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                    <c:forEach var="format" items="${formats}">
                        <c:if test="${format.id == book.formatId}">
                            <c:out value="${format.formatName}"/>
                        </c:if>
                    </c:forEach>
                    <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
                    <c:if test="${book.quantity < 1}">
                        <div id = "book-add${book.id}" class="btn checkout" style="margin-top: 5px; width: 170px; pointer-events: none">
                            <c:out value="${out_of_stock}"/></div>
                    </c:if>
                    <c:if test="${book.quantity > 0}">
                        <form id = "book-add${book.id}" action = "<%=request.getContextPath()%>/controller?id=${book.id}&quantity=1"
                              method = "post">
                        <input type="hidden" name="service_name" value="add_to_cart">
                        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                        <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: #24575c" value="${add}"/>
                    </form>
                    </c:if>
                    <form action = "<%=request.getContextPath()%>/controller?id=${book.id}&table=wish_lists" method = "post">
                        <input type="hidden" name="service_name" value="delete_entity">
                        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                        <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: darkred" value="${delete}"/>
                    </form>
                </div>
            </c:if>
        </c:forEach>

    </c:forEach>
</section>
<div class="btn" id="view-cart"><a href="/index"><c:out value="${continue_shop}"/></a></div>
</body>
</html>
