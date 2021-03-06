<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/formStyle.css"/>
    <script src="/js/validation.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <title>edit-book</title>
</head>
<body>
<% String id = request.getParameter("id");
    request.setAttribute("id", id);
%>

<jsp:useBean id="book" class="entity.Book" scope="request"/>
<c:forEach var="b" items="${books}">
    <c:if test="${b.id == id}">
        <jsp:setProperty name="book" property="id" value="${id}"/>
        <jsp:setProperty name="book" property="title" value="${b.title}"/>
        <jsp:setProperty name="book" property="authors" value="${b.authors}"/>
        <jsp:setProperty name="book" property="isbn" value="${b.isbn}"/>
        <jsp:setProperty name="book" property="format" value="${b.format}"/>
        <jsp:setProperty name="book" property="formatId" value="${b.formatId}"/>
        <jsp:setProperty name="book" property="category" value="${b.category}"/>
        <jsp:setProperty name="book" property="categoryId" value="${b.categoryId}"/>
        <jsp:setProperty name="book" property="language" value="${b.language}"/>
        <jsp:setProperty name="book" property="publisher" value="${b.publisher}"/>
        <jsp:setProperty name="book" property="quantity" value="${b.quantity}"/>
        <jsp:setProperty name="book" property="price" value="${b.price}"/>
        <jsp:setProperty name="book" property="description" value="${b.description}"/>
    </c:if>
</c:forEach>

<c:set var="uri" value="${pageContext.request.requestURI}"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="CURRENT_VALUE" var="current_value"/>
<fmt:message bundle="${content}" key="NEW_VALUE" var="new_value"/>
<fmt:message bundle="${content}" key="LANGUAGE" var="language"/>
<fmt:message bundle="${content}" key="TITLE" var="title"/>
<fmt:message bundle="${content}" key="AUTHORS" var="auths"/>
<fmt:message bundle="${content}" key="CATEGORY" var="cat"/>
<fmt:message bundle="${content}" key="FORMAT" var="format"/>
<fmt:message bundle="${content}" key="QUANTITY" var="qty"/>
<fmt:message bundle="${content}" key="PRICE" var="price"/>
<fmt:message bundle="${content}" key="PUBLISHER" var="publisher"/>
<fmt:message bundle="${content}" key="DESCRIPTION" var="descr"/>
<fmt:message bundle="${content}" key="SAVE" var="save"/>
<fmt:message bundle="${content}" key="CANT_CHANGE" var="cant_change"/>
<fmt:message bundle="${content}" key="BACK" var="back"/>

<a href="/admin-books">
    <div class="btn checkout"><p><c:out value="${back}"/></p></div>
</a>

<main class="edit-main">
    <section class="edit-info">
        <form action="<%= request.getContextPath()%>/controller?id=${book.id}" method="post" id="edit-author"
              onsubmit="return checkBook(this)">
            <table class="admin-table" id="edit-book">
                <thead>
                <th></th>
                <th><c:out value="${current_value}"/></th>
                <th><c:out value="${new_value}"/></th>
                </thead>
                <tr>
                    <td> ID</td>
                    <td><c:out value="${book.id}"/></td>
                    <td><c:out value="${cant_change}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${title}"/></td>
                    <td><c:out value="${book.title}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="title" type="text" name="new_title">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${auths}"/></td>
                    <td>
                        <c:forEach var="author_id" items="${book.authors}">
                            <c:forEach var="auth" items="${authors}">
                                <c:if test="${author_id == auth.id}">
                                    <a href="/admin-authors#author${author_id}"> <c:out
                                            value="${auth.fullName}"/></a><br>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </td>
                    <td>
                        <select name="new_authors" multiple="multiple">
                            <c:forEach var="auth" items="${authors}">
                                <option value="${auth.id}"><c:out value="${auth.fullName}"/></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${language}"/></td>
                    <td><c:out value="${book.language}"/></td>
                    <td><c:out value="${cant_change}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${publisher}"/></td>
                    <td><c:out value="${book.publisher}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="publisher" type="text" name="new_publisher">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td> ISBN</td>
                    <td><c:out value="${book.isbn}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="isbn" type="text" name="new_isbn">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${cat}"/></td>
                    <td><c:out value="${book.category}"/></td>
                    <td>
                        <select name="new_category">
                            <c:forEach var="categ" items="${categories}">
                                <option value="${categ.id}"><c:out value="${categ.categoryName}"/></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${format}"/></td>
                    <td><c:out value="${book.format}"/></td>
                    <td><c:out value="${cant_change}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${price}"/></td>
                    <td><c:out value="${book.price}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="price" type="text" name="new_price">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${qty}"/></td>
                    <td><c:out value="${book.quantity}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="quantity" type="text" name="new_quantity">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><c:out value="${descr}"/></td>
                    <td><c:out value="${book.description}"/></td>
                    <td>
                        <div class="form-control">
                            <textarea id="description" name="new_description" placeholder="" rows="20"></textarea>
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small>
                        </div>
                    </td>
                </tr>

            </table>
            <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
            <input type="hidden" name="service_name" value="edit_book">
            <input class="btn accept" type="submit" name="save" value="${save}">
        </form>
        <br>
    </section>

    <section class="edit-image">
        <form action="/image-servlet?uri=<%=request.getRequestURI()%>&id=${book.id}&table=book_covers"
              method="post" enctype="multipart/form-data">
            <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt="book cover" width="220px"/>
            <c:out value="${update_image}"/>
            <input type="hidden" name="service_name" value="edit_image">
            <input type="hidden" name="uri" value="${uri}">
            <input id="file" type="file" name="file" required>
            <input class="btn accept" type="submit" class="submit-btn" value="${save}"/>
        </form>
    </section>
</main>
</body>
</html>
