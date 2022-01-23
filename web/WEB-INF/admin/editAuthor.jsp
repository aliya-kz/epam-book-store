<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <head>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>edit-category</title>
    </head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="SEARCH" var="search"/>
<fmt:message bundle="${content}" key="CATEGORY" var="cat" />
<fmt:message bundle="${content}" key="LANGUAGE" var="lang" />

 <form action = "/controller?uri=<%=request.getRequestURI()%>" method="post">
    <td> <input type="text"  name="service-name" required/></td>
 </form>
</body>
</html>