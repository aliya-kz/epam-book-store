<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Cutive+Mono&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
    <title>edit-category</title>
  </head>
<body>

<jsp:useBean id="category" class="entity.Category" scope="request"></jsp:useBean>
<jsp:setProperty name="category" property="*"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="CURRENT_VALUE" var="current_value"/>
<fmt:message bundle="${content}" key="NEW_VALUE" var="new_value"/>
<fmt:message bundle="${content}" key="CANT_CHANGE" var="cant_change"/>
<fmt:message bundle="${content}" key="TITLE" var="title"/>
<fmt:message bundle="${content}" key="SAVE" var="save"/>
<fmt:message bundle="${content}" key="DELETE_IN_ALL_LANG" var="delete_in_all"/>
<fmt:message bundle="${content}" key="DELETE" var="delete"/>

<a href = "/admin-categories"><div class = "back"><p><c:out value="${back}"/></p></div></a>

<form action = "<%= request.getContextPath()%>/controller?category_id=${category.id}&lang=${category.lang}" method = "post">
<table class="admin-table" id="edit-category">
    <th> </th>
    <th><c:out value="${current_value}"/></th>
    <th><c:out value="${new_value}"/></th>
    <th> </th>

    <tr>
        <td> ID </td>
        <td><c:out value="${category.id}"/></td>
        <td><c:out value="${cant_change}"/></td>
        <td></td>
    </tr>

    <tr>
      <td><c:out value="${title}"/></td>
        <td><c:out value="${category.categoryName}"/></td>
        <td><input type="text" name ="new_value" required/></td>
        <td><input type = "hidden" name="service_name" value="edit_category">
        <input class="btn" id="accept" type="submit" value = "${save}"></td>
    </tr>

    <tr>
      <td><c:out value="${language}"/> </td>
      <td><c:out value="${category.lang}"/></td>
      <td><c:out value="${cant_change}"/></td>
      <td></td>
    </tr>
  </table>
  </form>

  <form action = "<%= request.getContextPath()%>/controller?id=${category.id}&table=categories_lang&lang=${category.lang}" method = "post">
    <input type="hidden" name="service_name" value="delete_entity_admin"/>
      <c:out value="${delete}"/>
    <input class="btn" id = "decline" type="submit" value="${delete}"/>
  </form>
<br>
  <form action = "<%= request.getContextPath()%>/controller?id=${category.id}&table=categories" method = "post">
  <input type="hidden" name="service_name" value="delete_entity_admin"/>
      <c:out value="${delete_in_all}"/>
  <input class="btn" id = "decline" type="submit" value="${delete}"/>
</form>
</html>