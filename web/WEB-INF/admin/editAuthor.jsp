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
    <title>edit-author</title>
</head>
<body>
<% String id = request.getParameter("author_id");
    request.setAttribute("id", id);
%>

<jsp:useBean id="author" class="entity.Author" scope="request"/>
<c:forEach var="auth" items="${authors}">
    <c:if test="${auth.id == id}">
        <jsp:setProperty name="author" property="id" value="${auth.id}"/>
        <jsp:setProperty name="author" property="name" value="${auth.name}"/>
        <jsp:setProperty name="author" property="surname" value="${auth.surname}"/>
        <jsp:setProperty name="author" property="biography" value="${auth.biography}"/>
        <jsp:setProperty name="author" property="lang" value="${auth.lang}"/>
    </c:if>
</c:forEach>

<c:set var="uri" value="${pageContext.request.requestURI}"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="CURRENT_VALUE" var="current_value"/>
<fmt:message bundle="${content}" key="NEW_VALUE" var="new_value"/>
<fmt:message bundle="${content}" key="LANGUAGE" var="language"/>
<fmt:message bundle="${content}" key="NAME" var="name"/>
<fmt:message bundle="${content}" key="SURNAME" var="surname"/>
<fmt:message bundle="${content}" key="BIOGRAPHY" var="biography"/>
<fmt:message bundle="${content}" key="CANT_CHANGE" var="cant_change"/>
<fmt:message bundle="${content}" key="SAVE" var="save"/>
<fmt:message bundle="${content}" key="DELETE_IN_ALL_LANG" var="delete_in_all"/>
<fmt:message bundle="${content}" key="DELETE" var="delete"/>
<fmt:message bundle="${content}" key="BACK" var="back"/>

<div class="back">
    <a href="/admin-authors">
        <button class="btn checkout"><c:out value="${back}"/></button>
    </a>
</div>
<main class="edit-main">
    <section class="edit-info">
        <form action="<%= request.getContextPath()%>/controller?id=${author.id}&lang=${author.lang}" method="post"
              id="edit-author" onsubmit="return editAuthor(this)">
            <table class="admin-table" id="edit-category">
                <th></th>
                <th><c:out value="${current_value}"/></th>
                <th><c:out value="${new_value}"/></th>

                <tr>
                    <td><c:out value="${language}"/></td>
                    <td><c:out value="${author.lang}"/></td>
                    <td><c:out value="${cant_change}"/></td>
                </tr>

                <tr>
                    <td> ID</td>
                    <td><c:out value="${author.id}"/></td>
                    <td><c:out value="${cant_change}"/></td>
                </tr>

                <tr>
                    <td><c:out value="${name}"/></td>
                    <td><c:out value="${author.name}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="name" type="text" name="new_name">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small></div>
                    </td>
                </tr>

                <tr>
                    <td><c:out value="${surname}"/></td>
                    <td><c:out value="${author.surname}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="surname" type="text" name="new_surname">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small></div>
                    </td>
                </tr>

                <tr>
                    <td><c:out value="${biography}"/></td>
                    <td><c:out value="${author.biography}"/></td>
                    <td>
                        <div class="form-control">
                            <input id="biography" type="text" name="new_biography">
                            <i class="fas-fa-check-circle"></i>
                            <i class="fas-fa-check-exclamation-circle"></i>
                            <small>Error message</small></div>
                    </td>
                </tr>
            </table>
            <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
            <input type="hidden" name="service_name" value="edit_author">
            <input class="btn accept" type="submit" onclick="editAuthor()" name="save" value="${save}">
        </form>
        <br>

        <form action="<%= request.getContextPath()%>/controller?id=${author.id}&table=authors&lang=${author.lang}"
              method="post">
            <input type="hidden" name="service_name" value="delete_entity_admin"/>
            <c:out value="${delete}"/>
            <input class="btn decline" type="submit" value="${delete}"/>
        </form>
        <br>
        <form action="<%= request.getContextPath()%>/controller?id=${author.id}&table=authors" method="post">
            <input type="hidden" name="service_name" value="delete_entity_admin"/>
            <c:out value="${delete_in_all}"/>
            <input class="btn decline" type="submit" value="${delete}"/>
        </form>
    </section>

    <section class="edit-image">
        <form action="/image-servlet?uri=<%=request.getRequestURI()%>&id=${author.id}&table=authors&lang=${author.lang}"
              method="post" enctype="multipart/form-data">
            <img src="/image-servlet?image_id=${author.id}&table=authors" alt="author" width="220px"/>

            <c:out value="${update_image}"/>
            <input type="hidden" name="uri" value="${uri}">
            <input type="hidden" name="service_name" value="edit_image">
            <input id="file" type="file" name="file">
            <input class="btn accept" type="submit" class="submit-btn" value="${save}"/>
        </form>
    </section>
</main>
</body>
</html>