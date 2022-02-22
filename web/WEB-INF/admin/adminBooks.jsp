<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <script src="/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>admin-books</title>
    </head>
<body>
<jsp:include page="/admin/adminHeader"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="FORMAT" var="format" />
<fmt:message bundle="${content}" key="QUANTITY" var="quantity"/>
<fmt:message bundle="${content}" key="PRICE" var="price" />
<fmt:message bundle="${content}" key="EDIT" var="edit" />
<fmt:message bundle="${content}" key="SEARCH" var="search" />
<fmt:message bundle="${content}" key="LANGUAGE" var="language" />
<fmt:message bundle="${content}" key="AUTHORS" var="auths" />
<fmt:message bundle="${content}" key="TITLE" var="title" />
<fmt:message bundle="${content}" key="CATEGORY" var="cat" />
<fmt:message bundle="${content}" key="IMAGE" var="image" />
<fmt:message bundle="${content}" key="ADD_BOOK" var="add_book" />


<section class="admin-filter">
    <input class="search-input" type="text" id="search-text" placeholder="${search}"  onkeyup="tableSearch('admin-books')">
</section>

<section class="add-book">
<a href = "/add-book"><div class = "btn" id="checkout"><c:out value="${add_book}"/></div></a>
</section>

<section>
    <form action = "<%= request.getContextPath()%>/controller" method = "post">
        <table class="admin-table" id="admin-books">
            <th class = "admin-th"><c:out value="${image}"/></th>
            <th class = "admin-th">ID</th>
            <th class = "admin-th"><c:out value="${title}"/></th>
            <th class = "admin-th"><c:out value="${auths}"/></th>
            <th class = "admin-th"><c:out value="${cat}"/></th>
            <th class = "admin-th"><c:out value="${format}"/></th>
            <th class = "admin-th"><c:out value="${quantity}"/></th>
            <th class = "admin-th"><c:out value="${language}"/></th>
            <th class = "admin-th"><c:out value="${price}"/></th>
                        <th class = "admin-th"><c:out value="${edit}"/></th>
                <c:forEach items="${books}" var="book">
                    <tr>
                        <td class="td-image">
                            <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt="author" width="90px"/></td>
                        <td><c:out value="${book.id}"/></td>
                        <td><c:out value="${book.title}"/></td>
                        <td>
                        <c:forEach var="author_id" items="${book.authors}">
                            <c:forEach var="auth" items="${authors}">
                                <c:if test="${author_id == auth.id}">
                                    <a href="/admin-authors#author${author_id}"> <c:out value="${auth.fullName}"/></a><br>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                        </td>
                        <td><c:out value="${book.category}"/></td>
                        <td><c:out value="${book.format}"/></td>
                        <td><c:out value="${book.quantity}"/></td>
                        <td><c:out value="${book.language}"/></td>
                        <td><c:out value="${book.price}"/></td>
                        <td><a href="/edit-book?id=${book.id}" class="edit">${edit}</a></td>
                    </tr>
                </c:forEach>
    </table>
    </form>
</section>

</body>
<jsp:include page="/footer"/>
</html>
