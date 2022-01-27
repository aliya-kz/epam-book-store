
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/validation.js"> </script>
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
<jsp:include page="/header"/>

<% int [] numbers = new int []{1,2,3,4,5,6,7,8,9,10};
    request.setAttribute("numbers", numbers);
request.setAttribute("id", request.getParameter("id"));%>

<c:forEach var="list_book" items="${books}">
        <c:if test = "${list_book.id == id}">
            <jsp:useBean id="book" class="entity.Book" scope="request"/>
            <jsp:setProperty name="book" property="id" value="${id}"/>
            <jsp:setProperty name="book" property="title" value="#{list_book.title}"/>
            <jsp:setProperty name="book" property="authors" value="#{list_book.authors}"/>
            <jsp:setProperty name="book" property="category" value="#{list_book.category}"/>
            <jsp:setProperty name="book" property="price" value="#{list_book.price}"/>
            <jsp:setProperty name="book" property="description" value="#{list_book.description}"/>
            <jsp:setProperty name="book" property="format" value="#{list_book.format}"/>
            <jsp:setProperty name="book" property="isbn" value="#{list_book.isbn}"/>
            <jsp:setProperty name="book" property="quantity" value="#{list_book.quantity}"/>
            <jsp:setProperty name="book" property="publisher" value="#{list_book.publisher}"/>
            <jsp:setProperty name="book" property="language" value="#{list_book.language}"/>
        </c:if>
</c:forEach>


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
            <form action = "<%=request.getContextPath()%>/controller?uri=<%=request.getRequestURI()%>&id=${book.id}" method = "post">
            <table class = "book-info-table">
                <tr>
                <div class = "book-info-table-left">
                    <c:out value="${cat}"/>: <c:out value="${book.category}"/><br>
                    <c:out value="${book.format}"/><br>
                    ISBN: <c:out value="${book.isbn}"/>
                </div>
                    <div class="book-info-table-right">
                        <c:out value="${book.price}"/>
                    </div>
                </tr>
                <tr>
                    <div class="book-info-table-left">
                        <c:out value="${quantity}"/>
                        <select name ="qty">
                            <c:forEach var="number" items="${numbers}" >
                                <option value="${number}"><c:out value="${number}"/> </option>
                                    </c:forEach>
                        </select>

                    </div>
                    <div class="book-info-table-right">
                        <button class="add-book-btn" name="service_name"  value="add_to_cart"><c:out value="${add_to_cart}"/>
                        </button>
                    </div>

                    <% request.setAttribute("msg", request.getParameter("msg"));
                    request.setAttribute("added", "added");%>
                    <c:if test="${msg!=null && msg==added}">
                        <p id="added"><c:out value="${added}"/> </p>
                        <a href="/cart"><c:out value="${view_cart}"/></a>
                        <a href="/checkout"><c:out value="${checkout}"/></a>
                    </c:if>
                </tr>

            </table>
            </form>
            </div>
    </section>
<div class="book-desc"><c:out value="${book.description}"/> </div>
<jsp:include page="/footer"/>
</body>
</html>

