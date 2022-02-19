<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <script src="/js/script.js"></script>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/formStyle.css"/>
    <script src="/js/validation.js"> </script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <title>admin-authors</title>
</head>

<body>
<jsp:include page="/WEB-INF/admin/adminHeader.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="NAME" var="name" />
<fmt:message bundle="${content}" key="SURNAME" var="surname" />
<fmt:message bundle="${content}" key="BIOGRAPHY" var="biography" />
<fmt:message bundle="${content}" key="LANGUAGE" var="language" />
<fmt:message bundle="${content}" key="IMAGE" var="image" />
<fmt:message bundle="${content}" key="ADD_AUTHOR" var="add_author" />
<fmt:message bundle="${content}" key="ADD_TRANSLATION" var="add_translation" />
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen" />
<fmt:message bundle="${content}" key="SEARCH" var="search" />
<fmt:message bundle="${content}" key="ALL_AUTHORS" var="all_authors" />
<fmt:message bundle="${content}" key="EDIT" var="edit" />
<fmt:message bundle="${content}" key="INVALID_ID" var="invalid_id" />
<main>
    <section class="add-new-entity">
        <h3> <c:out value="${add_author}"/></h3>
        <form action = "/image-servlet?uri=<%=request.getRequestURI()%>" method="post" enctype="multipart/form-data"
              onsubmit="return checkAuthor(this)">
            <table class="admin-table">
                <th> <c:out value="${name}"/></th>
                <th> <c:out value="${surname}"/></th>
                <th> <c:out value="${biography}"/></th>
                <th> <c:out value="${language}"/></th>
                <th> <c:out value="${image}"/></th>
                <th> </th>
                <tr>
                    <td><div class = "form-control">
                        <input id = "name" type = "text" name = "name" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><div class = "form-control">
                        <input id = "surname" type = "text" name = "surname" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><div class = "form-control">
                        <input id = "biography" type = "text" name = "biography" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td> <select name = "lang">
                        <c:forEach var="lang" items="${langs}">
                            <option value ="${lang.title}"><c:out value="${lang.title}"/></option>
                        </c:forEach>
                    </select></td>
                    <td>
                        <input id = "file" type = "file" name = "file" required >
                    </td>
                    <td><input type="hidden" name="service_name" value="add_new_author">
                        <input type="submit" class="btn accept" value = "${add_author}" onclick="checkAuthor()"> </td>
                </tr>
            </table>
        </form>
    </section>

    <section class="add-translation">
        <h3> <c:out value="${add_translation}"/></h3>
        <form action = "/controller?uri=<%=request.getRequestURI()%>" method="post" onsubmit="return checkAuthorTranslation(this)">
            <table class="admin-table">
                <tr>
                    <th>ID</th>
                    <th><c:out value="${name}"/></th>
                    <th><c:out value="${surname}"/></th>
                    <th><c:out value="${biography}"/></th>
                    <th><c:out value="${language}"/></th>
                    <th> </th>
                </tr>
                <tr>
                    <td><div class = "form-control">
                        <input id = "transl-id" type = "text" name = "id" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><div class = "form-control">
                        <input id = "transl-name" type = "text" name = "name" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><div class = "form-control">
                        <input id = "transl-surname" type = "text" name = "surname" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><div class = "form-control">
                        <input id = "transl-biography" type = "text" name = "biography" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><select name = "lang">
                        <c:forEach var="lang"  items="${langs}">
                            <option value ="${lang.title}"><c:out value="${lang.title}"/></option>
                        </c:forEach>
                    </select></td>
                    <td><input type="hidden" name="service_name" value="add_new_author">
                        <input type="submit" class="btn accept" value = "${add_translation}" onclick="checkAuthorTranslation()"> </td>

                </tr>
            </table>
        </form>
        <%String msg = request.getParameter("msg");
            request.setAttribute("msg", msg);
        %>
        <c:if test="${msg eq 'error'}">
            <div class="msg-div">
                <c:out value="${invalid_id}"></c:out>
            </div>
        </c:if>
    </section>

    <section class="admin-filter">
        <input class="search-input" type="text" id="search-text" placeholder="${search}" onkeyup="tableSearch('admin-authors')">
    </section>

    <section class="all-entities">
        <form action = "<%=request.getContextPath()%>/controller" method = "post">
            <h3> <c:out value="${all_authors}"/> </h3>
            <table class="admin-table" id="admin-authors">
                <th><c:out value="${image}"/></th>
                <th>ID</th>
                <th><c:out value="${name}"/></th>
                <th><c:out value="${surname}"/></th>
                <th><c:out value="${biography}"/></th>
                <th><c:out value="${language}"/></th>
                <th> </th>
                <c:forEach var="author"  items="${authors}">
                    <tr id="author${author.id}">
                        <td class="td-image"><a href="#" class = "admin-image">
                            <img src="/image-servlet?image_id=${author.id}&table=authors" alt="author" width="90px"/></a></td>
                        <td><c:out value="${author.id}"/></td>
                        <td><c:out value="${author.name}"/></td>
                        <td><c:out value="${author.surname}"/></td>
                        <td><c:out value="${author.biography}"/></td>
                        <td><c:out value="${author.lang}"/></td>
                        <td><a href ="/edit-author?author_id=${author.id}">
                                ${edit}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </section>
    <jsp:include page="/WEB-INF/view/footer.jsp"/>
</main>
</body>
</html>