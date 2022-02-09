<%@ page import="entity.Book" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel = "stylesheet" href="/css/style.css"/>
    <script src="/js/script.js"> </script>
    <title>Books</title>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle = "${content}" key="ADD_TO_CART" var="add_to_cart"/>
<fmt:message bundle = "${content}" key="ALL" var="all"/>
<fmt:message bundle = "${content}" key="CATEGORY" var="cat"/>
<fmt:message bundle = "${content}" key="FORMAT" var="formt"/>
<jsp:include page="/header"/>

<main class = "books-main">
    <section class="books-filter">
        <h1><c:out value="${cat}"/> </h1>
        <form action = "<%=request.getContextPath()%>/controller?service_name=filter_books" method = "post">
        <input type="hidden" name="paramName" value="category" >
            <select name="id" onchange="this.form.submit()" multiple="multiple">
            <c:forEach var="category" items="${categories}">
                <option class="option-cat" value="${category.id}" onselect="selectOption('option-cat')">
                    <c:out value="${category.categoryName}"/></option>
            </c:forEach>
        </select>
        </form>

        <form action = "<%=request.getContextPath()%>/controller?service_name=filter_books" method = "post">
           <h1> <c:out value="${formt}"/> </h1>
            <input type="hidden" name="paramName" value="format">
        <select name="id" onchange="this.form.submit()" multiple="multiple">
            <c:forEach var="format" items="${formats}">
                <option class="format" id="format${format.id}" value="${format.id}" onselect="selectStyle('format', 'format${format.id}')">
                    <c:out value="${format.formatName}"/> </option>
            </c:forEach>
        </select>
        </form>
        <button>
      </button>
    </section>

<% List<Book> filteredBooks = (List<Book>) session.getAttribute("filteredBooks");
if (filteredBooks == null) {
    filteredBooks = (List<Book>) session.getAttribute("books");
}
request.setAttribute("filteredBooks", filteredBooks);%>
<div>
    <section id = "filtered-books">
        <c:forEach var="book" items="${filteredBooks}">
            <div class = "index-book" onmouseover="showEl('book-add${book.id}')" onmouseleave="hideEl('book-add${book.id}')">
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
                <form id = "book-add${book.id}" style="display:none" onsubmit="showEl('grey-background${book.id}')"
                      action = "<%=request.getContextPath()%>/controller?id=${book.id}&qty=1" method = "post">
                    <input type="hidden" name="service_name" value="add_to_cart">
                    <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                    <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: #24575c" value="${add_to_cart}"/>
                </form>
            </div>
        </c:forEach>
    </section>

    <section>
        <c:forEach var="category" items="${categories}">
        <div class="title" id="cat${category.id}"><c:out value="${category.categoryName}"></c:out></div>
            <div id="index-books">
                <c:forEach var="book" items="${books}">
                    <c:if test="${book.categoryId == category.id}">
                    <div class = "index-book" onmouseover="showEl('book-add${book.id}')" onmouseleave="hideEl('book-add${book.id}')">
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
                        <form id = "book-add${book.id}" style="display:none" onsubmit="showEl('grey-background${book.id}')"
                              action = "<%=request.getContextPath()%>/controller?id=${book.id}&qty=1" method = "post">
                            <input type="hidden" name="service_name" value="add_to_cart">
                            <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                            <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: #24575c" value="${add_to_cart}"/>
                        </form>
                    </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:forEach>
    </section>
</div>
</main>
<jsp:include page="/footer"/>
</body>
</html>
