<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/validation.js"> </script>
    <script src="/js/script.js"> </script>
    <title>Book</title>
</head>
<body>

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
<fmt:message bundle = "${content}" key="FORMAT" var="format"/>
<fmt:message bundle = "${content}" key="OUT_OF_STOCK" var="out_of_stock"/>
<fmt:message bundle = "${content}" key="ADD_TO_WL" var="add_wl"/>
<jsp:include page="/header"/>

<% int [] numbers = new int []{1,2,3,4,5,6,7,8};
    request.setAttribute("numbers", numbers);
    request.setAttribute("id", request.getParameter("id"));%>

<c:forEach var="book" items="${books}">
    <c:if test = "${book.id == id}">

            <main class = "main">
                <section id = "book">
                    <div class = "book-cover">
                        <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" width="250px"/>

                       <c:if test="${empty user}">
                           <button class="add-to-wl" onclick="openForm('login-form')"></button>
                       </c:if>
                        <c:if test="${not empty user}">
                        <c:choose>
                            <c:when test="${fn:contains(wishList.books,book)}" >
                        <form action = "<%=request.getContextPath()%>/controller?id=${book.id}" method = "post">
                            <input type="hidden" name ="table" value="wish_lists">
                            <input type = "hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                            <button id="delete-from-wl" name="service_name" value="delete_entity"></button>
                        </form>
                            </c:when>
                            <c:otherwise>
                        <form action = "<%=request.getContextPath()%>/controller?id=${book.id}" method = "post">
                            <input type="hidden" name ="table" value="wish_lists">
                            <input type = "hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                                <button class="add-to-wl" name="service_name" value="add_to_wl"></button>
                        </form>
                            </c:otherwise>
                        </c:choose>
                        </c:if>
                    </div>

                    <div class = "book-info">
                        <h1><c:out value = "${book.title}"/></h1>
                        <c:forEach var="author_id" items="${book.authors}">
                            <c:forEach var="auth" items="${authors}">
                                <c:if test="${author_id == auth.id}">
                                    <a href="/author?id=${author_id}"> <h2><c:out value="${auth.fullName}"/></h2></a>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                        <section class = "book-info-table">
                            <c:out value="${cat}"/>: <c:out value="${book.category}"/><br>
                            <c:out value="${publisher}"/>: <c:out value="${book.publisher}"/><br>
                            <c:out value="${format}"/> <c:out value="${book.format}"/><br>
                            <h3> ISBN: <c:out value="${book.isbn}"/> </h3><br>
                            <h2> <c:out value="${book.price}"/> ₸</h2>
                            <c:out value="${quantity}"/>
                            <form action = "<%=request.getContextPath()%>/controller?id=${book.id}" method = "post">

                                <c:choose>
                                    <c:when test="${book.quantity < 1}">
                                       <h1> <c:out value="${out_of_stock}"></c:out></h1>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${book.quantity > 0 && book.quantity < 8}">
                                            <h2> <c:out value="${book.quantity}"/> <c:out value="${available}"/></h2>
                                            <select name ="qty">
                                                <c:forEach var="number" items="${numbers}" >
                                                    <c:if test="${number <= book.quantity}">
                                                        <option value="${number}"><c:out value="${number}"/> </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                        <c:if test="${book.quantity > 7}">
                                            <select name ="qty">
                                                <c:forEach var="number" items="${numbers}" >
                                                    <option value="${number}"><c:out value="${number}"/> </option>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                        <input type = "hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                                        <button class="add-book-btn" name="service_name"  value="add_to_cart"><c:out value="${add_to_cart}"/>
                                        </button><br>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                            <% request.setAttribute("msg", request.getParameter("msg"));
                                request.setAttribute("added", "added");%>
                            <c:if test="${msg != null && msg == added}">
                                <div class="btns">
                                    <a href="/cart"><div id="view-cart" class="btn"><c:out value="${view_cart}"/></div></a>
                                    <a href="/checkout"><div id="checkout" class="btn"><c:out value="${checkout}"/></div></a>
                                </div>
                            </c:if>
                        </section>

                    </div>
                </section>
                <div class="book-desc"><c:out value="${book.description}"/> </div>
            </main>
    </c:if>
</c:forEach>


<jsp:include page="/footer"/>
</body>
</html>
