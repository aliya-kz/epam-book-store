<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>checkout</title>
</head>
<jsp:include page="/header"/>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle = "${content}" key="CATEGORY" var="cat"/>
<fmt:message bundle = "${content}" key="ADDRESS" var="address"/>
<fmt:message bundle = "${content}" key="QUANTITY" var="quantity"/>
<fmt:message bundle = "${content}" key="BACK" var="back"/>
<fmt:message bundle = "${content}" key="VIEW_CART" var="view_cart"/>
<fmt:message bundle = "${content}" key="CHECKOUT" var="checkout"/>
<fmt:message bundle = "${content}" key="PUBLISHER" var="publisher"/>
<fmt:message bundle = "${content}" key="DELETE" var="delete"/>
<fmt:message bundle = "${content}" key="CONTINUE_SHOPPING" var="continue"/>
<fmt:message bundle = "${content}" key="PAY" var="pay"/>
<fmt:message bundle = "${content}" key="PAYMENT_METHOD" var="payment_method"/>

<body>
<main class="checkout-main">
    <form id="checkout-form" action = "<%= request.getContextPath()%>/controller" method = "post">
        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
        <section id="delivery">
            <h1> <c:out value = "${address}"/></h1>
            <c:forEach var="addr" items="${user.addresses}">
                <input type="radio" name="address" value="${addr.id}"><c:out value="${addr.address}"/>
            </c:forEach>
            <a href="/profile#prof-address"><c:out value="${add}"/>
    </section>

    <table id="cart-table">
        <th></th>
        <th></th>
        <th></th>
        <th></th>
        <c:forEach var="entry" items="${cart.cartItems}">
            <c:set var="qty" value="${entry.value}"/>
            <c:set var="cartBook" value="${entry.key}"/>
            <c:forEach var="book" items="${books}">
                <c:if test="${book == cartBook}">
                    <tr id = "book">
                        <td class = "book-cover">
                                <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" width="120px"/>
                                <input type="hidden" name="id" value="${book.id}"/>
                                <button id="add-to-wl" name="service_name" value="add_to_wl" onclick="heartClicked()"></button>
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
                            <c:out value="${qty}"/>
                        <td id = "cost">
                            <c:set var="cost" value="${book.price * qty}"/>
                            <c:set var ="total" value="${total + cost}" scope="session"/>
                            <c:out value="${cost}"></c:out>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </c:forEach>

        <tr>
            <td colspan="3">Total cost:</td>
            <td><c:out value="${total}"/></td>
        </tr>
    </table>
         <button class="btn" id="card-btn" id="view-cart" onclick="openForm('card-form')"></button>
            <div class="btn"><a href="/cart"><c:out value="${back}"/> </a></div>


   <h1><c:out value="${payment_method}"/> </h1>
    <c:forEach var="card" items="${user.cards}">
        <input type="radio" name="card" value="${card.id}"><c:out value="${card.cardNumber}"/>
    </c:forEach>
    <a href="/profile#prof-address"><c:out value="${add}"/>
        <input type="submit" name="service_name" value="create_order"/>
    </form>


</main>
</body>
</html>
