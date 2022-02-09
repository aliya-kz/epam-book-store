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
    <fmt:message bundle = "${content}" key="DELETE" var="delete"/>
    <fmt:message bundle = "${content}" key="CONTINUE_SHOPPING" var="continue_shop"/>
    <fmt:message bundle = "${content}" key="CART_EMPTY" var="cart_empty"/>
    <jsp:include page="/header"/>

            <% int [] numbers = new int []{1,2,3,4,5,6,7,8,9,10};
            request.setAttribute("numbers", numbers);
%>

    <c:set var="total" value="0"/>
    <table id="cart-table">
        <thead>
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        </thead>
        <c:forEach var="entry" items="${cart.cartItems}">
            <c:set var="qty" value="${entry.value}"/>
            <c:set var="cartBook" value="${entry.key}"/>
            <c:forEach var="book" items="${books}">
                <c:if test="${book == cartBook}">
                    <tr id = "book">

                        <td class = "book-cover">
                                <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" width="140px"/>
                        </td>
                        <td class = "book-info">
                            <a href = "/book?id=${book.id}" class="index-book-name"><c:out value = "${book.title}"/></a>
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
                            <form action = "<%=request.getContextPath()%>/controller?id=${book.id}" method = "post">
                                <select name ="qty" onchange="this.form.submit()">
                                    <c:forEach var="number" items="${numbers}">
                                        <c:choose>
                                            <c:when test="${number == qty}">
                                                <option value="${number}" selected><c:out value="${number}"/> </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${number}"><c:out value="${number}"/> </option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                                <input type="hidden" name="service_name" value="update_quantity"/>
                                <input class="invisible-input" type="submit" />
                            </form>
                            <form action = "<%=request.getContextPath()%>/controller?id=${book.id}&table=carts" method = "post">
                                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                                <button class="fa-btn" type="submit" name="service_name" value="delete_entity" style="margin-top: 5px"><i class="fas fa-minus-circle fa-lg"></i></button>
                            </form>
                        </td>

                        <td id = "cost">
                            <c:set var="book_cost" value="${book.price * qty}"/>
                            <c:set var="total" value="${total + book_cost}"/>
                            <c:out value="${book_cost}"/> ₸
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </c:forEach>

        <tr id="total-cost">
            <c:if test="${cart.cartItems.size() > 0}">
                <td colspan="2">Total cost:</td>
                <td><c:out value="${total}"/> ₸ </td>
                <c:set target="${cart}" property="cost" value="${total}"/>
            </c:if>
            <c:if test="${cart.cartItems.size() == 0}">
                <h1><c:out value="${cart_empty}"/> </h1>
            </c:if>
        </tr>

    </table>

    <div class = "btn-container">
        <div class="btn" id="view-cart"><a href="/index"><c:out value="${continue_shop}"/></a></div><br>

        <c:choose>
            <c:when test="${empty user}">
                <div class="btn" id="checkout" onclick="openForm('login-form')"><c:out value="${checkout}"/></div>
            </c:when>
            <c:otherwise>
                <c:if test="${cart.cartItems.size() > 0}">
                    <div class="btn" id="checkout"><a href="/checkout"><c:out value="${checkout}"/></a></div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="/footer"/>
</body>
</html>

