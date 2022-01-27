<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <script src="/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
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
<fmt:message bundle="${content}" key="LANGUAGE" var="lang" />


<section class="admin-filter">
    <h1><c:out value="${search}"/> </h1>
    <input class="search-input" type="text" id="search-text" onkeyup="tableSearch('admin-books')">
</section>

<section class="add-book">


</section>

<section>
    <form action = "<%= request.getContextPath()%>/controller" method = "post">
        <table class="admin-table" id="admin-books">
        <th class = "admin-th">ID</th>
        <th class = "admin-th"><c:out value="${title}"/></th>
            <th class = "admin-th"><c:out value="${authors}"/></th>
            <th class = "admin-th"><c:out value="${cat}"/></th>
            <th class = "admin-th"><c:out value="${format}"/></th>
            <th class = "admin-th"><c:out value="${quantity}"/></th>
            <th class = "admin-th"><c:out value="${language}"/></th>
            <th class = "admin-th"><c:out value="${price}"/></th>
            <th class = "admin-th"><c:out value="${image}"/></th>
            <th class = "admin-th"><c:out value="${edit}"/></th>
                <c:forEach items="${books_list}" var="book">
                    <tr>
                    <td><c:out value="${book.id}"/></td>
                    <td><c:out value="${book.title}"/></td>
                    <td><c:out value="${book.authors}"/></td>
                    <td><c:out value="${book.categoryId}"/></td>
                    <td><c:out value="${book.formatId}"/></td>
                    <td><c:out value="${book.quantity}"/></td>
                    <td><c:out value="${book.lang}"/></td>
                    <td><c:out value="${book.price}"/></td>
                    <td><a id = "a-img" href="#"><img src="/imageServlet?image_id=${book.id}%>&table=book_covers" alt ="${book.title}"></a></td>
                    <td><a href="/editBook?id=${book.id}" class="edit">${edit}</a></td>
                    </tr>
                </c:forEach>
    </table>
    </form>
</section>

</body>
<jsp:include page="/WEB-INF/view/footer.jsp"/>
</html>
