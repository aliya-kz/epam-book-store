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
<fmt:message bundle = "${content}" key="LANGUAGE" var="language"/>
<fmt:message bundle = "${content}" key="RESET" var="reset"/>
<fmt:message bundle = "${content}" key="APPLY" var="apply"/>
<fmt:message bundle = "${content}" key="NOTHING" var="nothing"/>
<fmt:message bundle = "${content}" key="OTHER_BOOKS" var="other_books"/>
<fmt:message bundle="${content}" key="OUT_OF_STOCK" var="out_of_stock"/>
<jsp:include page="/header"/>

<main class = "books-main">
    <section class="books-filter">
        <form action = "<%=request.getContextPath()%>/controller?service_name=filter_books" method = "post">
            <h1><c:out value="${cat}"/> </h1>
            <select name="category" multiple>
            <c:forEach var="category" items="${categories}">
                <option id = "cat${category.id}" value="${category.id}" onselect="selectStyle('cat${category.id}')">
                <c:out value="${category.categoryName}"/></option>
            </c:forEach>
        </select>

            <h1> <c:out value="${formt}"/> </h1>
        <select name="format" multiple>
            <c:forEach var="format" items="${formats}">
                <option id="format${format.id}" value="${format.id}" onselect="selectStyle('format${format.id}')">
                    <c:out value="${format.formatName}"/> </option>
            </c:forEach>
        </select>

            <h1> <c:out value="${language}"/> </h1>
            <select name="publang"  multiple>
                <c:forEach var="publang" items="${langs}">
                    <option id="lang${lang.id}" value="${publang.title}" onselect="selectStyle('publang${lang.id}')">
                        <c:out value="${publang.title}"/> </option>
                </c:forEach>
            </select>

            <br/>
            <button class="btn checkout"><c:out value="${apply}"/> </button>

            <button class="btn decline" name="reset" value="reset"><c:out value="${reset}"/> </button>
            </button>
        </form>
    </section>

<% List<Book> filteredBooks = (List<Book>) session.getAttribute("filteredBooks");
if (filteredBooks == null) {
    filteredBooks = (List<Book>) session.getAttribute("books");
}
request.setAttribute("filteredBooks", filteredBooks);%>
<div>
    <section id = "filtered-books">
        <c:if test="${filteredBooks.size() == 0}">
            <h1><c:out value="${nothing}"></c:out></h1>
        </c:if>
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
                <c:if test="${book.quantity < 1}">
                    <div id = "book-add${book.id}" type="submit" class="btn checkout" style="margin-top: 5px; width: 170px">
                        <c:out value="${out_of_stock}"/></div>
                </c:if>
                <c:if test="${book.quantity > 0}">
                    <form id = "book-add${book.id}" style="display:none" onsubmit="showEl('grey-background${book.id}')"
                          action = "<%=request.getContextPath()%>/controller?id=${book.id}&qty=1" method = "post">
                        <input type="hidden" name="service_name" value="add_to_cart">
                        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                        <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: #24575c" value="${add_to_cart}"/>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </section>

    <section>
        <div class="big-title"><c:out value="${other_books}"/> </div>
        <br/>
        <c:forEach var="category" items="${categories}">
        <div class="title" id="${category.id}"><c:out value="${category.categoryName}"></c:out></div>
            <div id="index-books">
                <c:forEach var="book" items="${books}">
                    <c:if test="${book.categoryId == category.id}">
                    <div class = "index-book" onmouseover="showEl('book${book.id}')" onmouseleave="hideEl('book${book.id}')">
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
                            <div id = "book${book.id}" type="submit" class="btn checkout" style="margin-top: 5px; width: 170px">
                                <c:out value="${out_of_stock}"/></div>
                        </c:if>
                        <c:if test="${book.quantity > 0}">
                            <form id = "book${book.id}" style="display:none" onsubmit="showEl('grey-background${book.id}')"
                                  action = "<%=request.getContextPath()%>/controller?id=${book.id}&qty=1" method = "post">
                                <input type="hidden" name="service_name" value="add_to_cart">
                                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                                <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: #24575c" value="${add_to_cart}"/>
                            </form>
                        </c:if>
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
