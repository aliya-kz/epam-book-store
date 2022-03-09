<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <script src="/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="/css/formStyle.css"/>
        <script src="/js/validation.js"> </script>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>admin-categories</title>
    </head>
<body>
<jsp:include page="/WEB-INF/admin/adminHeader.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="SEARCH" var="search"/>
<fmt:message bundle="${content}" key="CATEGORY" var="cat" />
<fmt:message bundle="${content}" key="LANGUAGE" var="language" />
<fmt:message bundle="${content}" key="ADD_CATEGORY" var="add_cat" />
<fmt:message bundle="${content}" key="ADD_NEW_CATEGORY" var="add_new_cat" />
<fmt:message bundle="${content}" key="INSERT_CATEGORY_ID" var="insert_cat_id" />
<fmt:message bundle="${content}" key="INSERT_CATEGORY_NAME" var="insert_cat_name" />
<fmt:message bundle="${content}" key="ALL_CATEGORIES" var="all_cat" />
<fmt:message bundle="${content}" key="EDIT" var="edit" />
<fmt:message bundle="${content}" key="CAT_EXISTS" var="cat_exists" />

<main class="admin-main">

    <section class="admin-filter">
        <input class="search-input" type="text" id="search-text" placeholder="${search}"  onkeyup="tableSearch('admin-categories')"/>
    </section>

    <section>
        <h1> <c:out value = "${add_new_cat}"/> </h1>
            <form action = "<%= request.getContextPath()%>/controller" method = "post" onsubmit="return checkCategory(this)">
            <table class="admin-table">
                <th>ID</th>
                <th><c:out value = "${cat}"/></th>
                <th><c:out value = "${language}"/></th>
                <th> </th>
                <tr>
                    <td><div class = "form-control">
                        <input id = "id" type = "text" placeholder="${insert_cat_id}" name = "new_id" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><div class = "form-control">
                        <input id = "category" type = "text" placeholder="${insert_cat_name}" name = "new_value" required >
                        <i class = "fas-fa-check-circle"></i>
                        <i class = "fas-fa-check-exclamation-circle"></i>
                        <small>Error message</small></div></td>
                    <td><select name = "category_language">
                         <c:forEach var="lang" items="${langs}">
                        <option value ="${lang.title}"><c:out value="${lang.title}"/></option>
                         </c:forEach>
                    </select></td>
                    <td><input type="hidden" name="service_name" value="add_new_category">
                        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                        <input type="submit" class="btn" id="accept" value = "${add_cat}" onclick="checkCategory()"> </td>
                </tr>
            </table>
            </form>

                <%String msg = request.getParameter("msg");
                    request.setAttribute("msg", msg);
                %>
                <c:if test="${msg eq 'error'}">
                    <div class="msg-div">
                        <c:out value="${cat_exists}"/>
                    </div>
                </c:if>

        <h3> <c:out value = "${all_cat}"/> </h3>
        <form action = "<%= request.getContextPath()%>/controller" method = "post">
            <table class="admin-table" id="admin-categories">
                <th>ID</th>
                <th><c:out value = "${cat}"/></th>
                <th><c:out value = "${language}"/></th>
                <th><c:out value = "${edit}"/></th>
                    <c:forEach var="category" items="${categories}">
                <tr>
                    <td><c:out value ="${category.id}"/></td>
                    <td><c:out value = "${category.categoryName}"/></td>
                    <td><c:out value="${category.lang}"/></td>
                    <td><a href ="/editCategory?id=${category.id}&categoryName=${category.categoryName}&lang=${category.lang}">
                        ${edit}</a>
                    </td>
                </tr>
                    </c:forEach>
            </table>
        </form>
    </section>
</main>
<jsp:include page="/WEB-INF/view/footer.jsp"/>
</body>
</html>
