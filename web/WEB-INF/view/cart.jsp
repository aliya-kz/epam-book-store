
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/script.js"> </script>
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
<fmt:message bundle = "${content}" key="PUBLISHER" var="publisher"/>
<jsp:include page="/header"/>

<%  int [] numbers = new int []{1,2,3,4,5,6,7,8,9,10};
    request.setAttribute("numbers", numbers);
 %>

    <form id = "cart-form" action = "<%=request.getContextPath()%>/controller " method = "post">
        <table>
            <c:forEach var="entry" items="${cart.cartItems}">
                <c:set var="qty" value="${entry.value}"/>
                <c:set var="cartBook" value="${entry.key}"/>
                <c:forEach var="book" items="${books}">
                    <c:if test="${book == cartBook}">
                    <tr id = "book">
                        <td class = "book-cover">
                            <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" width="250px"/>
                            <input type="hidden" name="id" value="${book.id}"/>
                            <button id="add-to-wl" name="service_name"  value="add_to_wl" onclick="heartClicked()"/>
                        </td>
                        <td class = "book-info">
                            <h1><c:out value = "${book.title}"/></h1>
                            <c:forEach var="author_id" items="${book.authors}">
                                <c:forEach var="auth" items="${authors}">
                                    <c:if test="${author_id == auth.id}">
                                        <a href="/author?id=${author_id}"> <h2><c:out value="${auth.fullName}"/></h2></a>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>

                            <c:out value="${publisher}"/>: <c:out value="${book.publisher}"/><br>
                            <c:out value="${format}"/> <c:out value="${book.format}"/><br>
                            <h2> <c:out value="${book.price}"/> ₸</h2>
                        </td>
                        <td>
                            <c:out value="${quantity}"/>
                            <select name ="qty" onchange="selectQuantity(id)">
                                <c:forEach var="number" items="${numbers}">
                                    <c:choose>
                                        <c:when test="${number == qty}">
                                            <option value="${number}"><c:out value="${number}"/> </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option id="to-be-selected" value="${number}"><c:out value="${number}"/> </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>

                        <td id = "cost">
                            <c:set var="cost" value="${book.price * number}"/>
                            <c:out value="${cost}"></c:out>
                        </td>
                    </tr>
                </c:if>
                </c:forEach>
                </c:forEach>

                <tr>
                    <td colspan="2">Total cost:</td>
                    <td> total cost</td>
                </tr>

        </table>
    </form>
</body>
</html>

