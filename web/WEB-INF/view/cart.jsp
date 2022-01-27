
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/validation.js"> </script>
    <title>Cart</title>
</head>
<body>
<form action = "<%=request.getContextPath()%>/controller?uri=<%=request.getRequestURI()%>" method = "post">

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle = "${content}" key="CATEGORY" var="cat"/>
<fmt:message bundle = "${content}" key="QUANTITY" var="quantity"/>
<fmt:message bundle = "${content}" key="ADD_TO_CART" var="add_to_cart"/>
<fmt:message bundle = "${content}" key="AVAILABLE" var="available"/>
<fmt:message bundle = "${content}" key="OUT_OF_STOCK" var="out_of_stock"/>
<fmt:message bundle = "${content}" key="ITEMS_LEFT" var="left"/>
<fmt:message bundle = "${content}" key="VIEW_CART" var="view_cart"/>
<fmt:message bundle = "${content}" key="CHECKOUT" var="checkout"/>
<jsp:include page="/header"/>

<%  int [] numbers = new int []{1,2,3,4,5,6,7,8,9,10};
    request.setAttribute("numbers", numbers);
 %>

<jsp:useBean id="cart" class="entity.Cart" scope="session"/>
<table>
<c:forEach var="entry" items="${cart.cartItems}">
<c:set var = "book" value="${entry.key}"/>
    <c:set var="bookQty" value="${entry.value}"/>

<section id = "book">
    <div class = "book-cover">
        <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}"/>
    </div>
    <div class = "book-info">
        <h1><c:out value = "${book.title}"/></h1>
        <c:forEach var="author_id" items="${book.authors}">
            <c:forEach var="auth" items="${authors}">
                <c:if test="${author_id == auth.id}">
                    <a href="/author?id=${author_id}"> <c:out value="${auth.fullName}"/></a>
                </c:if>
            </c:forEach>
        </c:forEach>
        <c:out value="${book.format}"/>
        <c:out value="${book.language}"/>
                </div>



</section>
</c:forEach>
</table>

</form>
</body>
</html>
