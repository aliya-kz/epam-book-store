<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <title>admin-users</title>
</head>
<body>
<jsp:include page="/WEB-INF/admin/adminHeader.jsp"/>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="NAME" var="name"/>
<fmt:message bundle="${content}" key="SURNAME" var="surname"/>
<fmt:message bundle="${content}" key="PHONE_NUMBER" var="phone_number"/>
<fmt:message bundle="${content}" key="BLOCK_USER" var="block_user"/>
<fmt:message bundle="${content}" key="UNBLOCK_USER" var="unblock_user"/>

<main class="main">
    <table class="admin-table">
        <th class="admin-th">ID</th>
        <th class="admin-th">Email</th>
        <th class="admin-th"><c:out value="${name}"/></th>
        <th class="admin-th"><c:out value="${surname}"/></th>
        <th class="admin-th"><c:out value="${phone_number}"/></th>
        <th class="admin-th"></th>
        <c:forEach var="user" items="${users}">
            <tr class="book-row" id="user${user.id}">
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.name}"/></td>
                <td><c:out value="${user.surname}"/></td>
                <td><c:out value="${user.phone}"/></td>
                <td>
                    <c:choose>
                        <c:when test="${!user.isBlocked()}">
                            <form action="<%= request.getContextPath()%>/controller" method="post">
                                <input type="hidden" name="user_id" value="${user.id}"/>
                                <input type="hidden" name="blocked_status" value="true"/>
                                <input type="hidden" name="service_name" value="block_user"/>
                                <input class="btn decline" type="submit" value="${block_user}">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="<%= request.getContextPath()%>/controller" method="post">
                                <input type="hidden" name="user_id" value="${user.id}"/>
                                <input type="hidden" name="blocked_status" value="false"/>
                                <input type="hidden" name="service_name" value="block_user"/>
                                <input class="btn accept" type="submit" value="${unblock_user}">
                            </form>
                        </c:otherwise>
                    </c:choose>

                </td>
            </tr>
        </c:forEach>>
    </table>
</main>
</body>
</html>