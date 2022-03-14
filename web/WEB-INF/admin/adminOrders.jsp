<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/js/script.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <title>admin-orders</title>
</head>
<body>
<jsp:include page="/admin/adminHeader"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>

<fmt:message bundle="${content}" key="ORDERS" var="ords"/>
<fmt:message bundle="${content}" key="CARD_NUMBER" var="card_number"/>
<fmt:message bundle="${content}" key="QUANTITY" var="quantity"/>
<fmt:message bundle="${content}" key="ORDER_NUM" var="order_num"/>
<fmt:message bundle="${content}" key="USER_ID" var="user_id"/>
<fmt:message bundle="${content}" key="ADDRESS" var="address"/>
<fmt:message bundle="${content}" key="DATE" var="date"/>
<fmt:message bundle="${content}" key="COST" var="cost"/>
<fmt:message bundle="${content}" key="STATUS" var="status"/>
<fmt:message bundle="${content}" key="DETAILS" var="details"/>
<fmt:message bundle="${content}" key="SAVE" var="save"/>
<fmt:message bundle="${content}" key="CLOSE" var="close"/>
<fmt:message bundle="${content}" key="EDIT" var="edit"/>
<fmt:message bundle="${content}" key="SEARCH" var="search"/>


<section class="admin-filter">
    <input class="search-input" type="text" id="search-text" placeholder="${search}"
           onkeyup="tableSearch('admin-orders')">
</section>

<section>
    <table class="admin-table" id="admin-orders">
        <tr>
            <th><c:out value="${order_num}"/></th>
            <th><c:out value="${user_id}"/></th>
            <th><c:out value="${date}"/></th>
            <th><c:out value="${address}"/></th>
            <th><c:out value="${cost}"/></th>
            <th><c:out value="${card_number}"/></th>
            <th><c:out value="${status}"/></th>
            <th><c:out value="${edit}"/></th>
            <th>Details</th>
        </tr>

        <c:forEach var="order" items="${orders}">
            <tr>
                <td><c:out value="${order.id}"/></td>
                <td><a href="/admin-users#user${order.userId}"><c:out value="${order.userId}"/></a></td>
                <td><c:out value="${order.date}"/></td>
                <td><c:out value="${order.address.address}"/></td>
                <td><c:out value="${order.cost}"/></td>
                <td><c:out value="${order.cardNumber}"/></td>
                <td>
                    <c:forEach var="stat" items="${statuses}">
                        <c:if test="${order.statusId == stat.id}">
                            <c:out value="${stat.statusName}"/>
                        </c:if>
                    </c:forEach>
                </td>
                <td>
                    <c:if test="${order.statusId < 5}">
                        <form action="<%=request.getContextPath()%>/controller?order=${order.id}" method="post">
                            <select name="status">
                                <c:forEach var="stat" items="${statuses}">
                                    <c:choose>
                                        <c:when test="${stat.id == order.statusId}">
                                            <option value="${stat.id}" selected><c:out
                                                    value="${stat.statusName}"></c:out></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${stat.id}"><c:out
                                                    value="${stat.statusName}"></c:out></option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="service_name" value="update_status"/>
                            <input type="submit" class="btn" id="accept" value="${save}"/>
                        </form>
                    </c:if>
                </td>
                <td>
                    <button class="btn" id="view-cart" onclick="openForm('admin-order-books${order.id}')"><c:out
                            value="${details}"/></button>
                </td>
            </tr>

            <tr id="admin-order-books${order.id}" style="display: none">
                <td colspan="7">
                    <div id="order-details">
                        <c:forEach var="entry" items="${order.orderItems}">
                            <c:set var="book1" value="${entry.key}"/>
                            <c:set var="qty" value="${entry.value}"/>
                            <c:forEach var="book" items="${books}">
                                <c:if test="${book.id == book1.id}">
                                    <div class="index-book">
                                        <img src="/image-servlet?image_id=${book.id}&table=book_covers"
                                             alt="${book.title}" width="80px"><br>
                                        <a href="/book?id=${book.id}" class="index-book-name"><c:out
                                                value="${book.title}"/></a>
                                        <c:set var="authorIds" value="${book.authors}"/>
                                        <c:forEach var="authorId" items="${authorIds}">
                                            <c:forEach var="auth" items="${authors}">
                                                <c:if test="${authorId == auth.id}">
                                                    <h4><a href="/author?id=${auth.id}"
                                                           class="index-author-name">${auth.fullName}</a></h4>
                                                </c:if>
                                            </c:forEach>
                                        </c:forEach>
                                        <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
                                        <p><c:out value="${quantity}:${qty}"/></p>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </td>
                <td>
                    <button class="btn" id="decline" onclick="closeForm('admin-order-books${order.id}')"><c:out
                            value="${close}"/></button>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
<jsp:include page="/footer"/>
</html>
