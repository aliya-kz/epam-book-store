<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
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

<jsp:useBean id="category" class="entity.Category" scope="request"></jsp:useBean>
<jsp:setProperty name="category" property="*"></jsp:setProperty>

<% Locale locale = (Locale) session.getAttribute("locale");
  ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
    System.out.println("uri" + request.getRequestURI());
  String msg = request.getParameter("msg");%>

<form action = "<%= request.getContextPath()%>/controller?category_id=${category.id}&lang=${category.lang}" method = "post">
<table class="admin-table" id="edit-category">
    <th> </th>
    <th><%=bundle.getString("CURRENT_VALUE")%></th>
    <th><%=bundle.getString("NEW_VALUE")%></th>
    <th> </th>

    <tr>
      <td> ID </td>
        <td>${category.id}</td>
        <td><%=bundle.getString("CANT_CHANGE")%></td>
        <td></td>
    </tr>

    <tr>
      <td> <%=bundle.getString("TITLE")%></td>
        <td>${category.categoryName}</td>
        <td><input type="text" name ="new_value" required/></td>
        <td><input type = "hidden" name="service_name" value="edit_category">
        <input type="submit" value = "<%=bundle.getString("SAVE")%>"></td>
    </tr>

    <tr>
      <td><%=bundle.getString("LANGUAGE")%>  </td>
      <td>${category.lang}</td>
      <td><%=bundle.getString("CANT_CHANGE")%></td>
      <td></td>
    </tr>
  </table>
  </form>

  <form action = "<%= request.getContextPath()%>/controller?id=${category.id}&table=categories_lang&lang=${category.lang}" method = "post">
    <input type="hidden" name="service_name" value="delete_entity"/>
      <%=bundle.getString("DELETE")%>
    <input id = "decline" type="submit" value="<%=bundle.getString("DELETE")%>"/>
  </form>


<br>

  <form action = "<%= request.getContextPath()%>/controller?id=${category.id}&table=categories" method = "post">
  <input type="hidden" name="service_name" value="delete_entity"/>
  <%=bundle.getString("DELETE_IN_ALL_LANG")%>
  <input id = "decline" type="submit" value="<%=bundle.getString("DELETE")%>"/>
</form>
</main>
</body>
</html>