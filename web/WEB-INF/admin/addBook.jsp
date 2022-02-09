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
    <title>edit-book</title>
  </head>
<body>

<jsp:useBean id="book" class="entity.Book" scope="request"/>

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
<fmt:message bundle="${content}" key="ADD_BOOK" var="add_book"/>

 <a href = "/admin-books"><div class = "btn" id="view-cart"><c:out value="${back}"/></div></a>

<main class="edit-main">
  <section class="edit-info">
    <form action = "/image-servlet?uri=<%=request.getRequestURI()%>" method="post" enctype="multipart/form-data">
      <table class="admin-table" id="edit-book">
        <tr>
        <th> </th>
        <th><c:out value="${new_value}"/></th>
        </tr>
        <tr>
          <td><c:out value="${title}"/></td>
          <td><input type="text" name ="title" required/></td>
        </tr>

        <tr>
          <td><c:out value="${auths}"/></td>
          <td>
            <select name="author_ids" multiple="multiple">
              <c:forEach var="auth" items="${authors}">
                <option value="${auth.id}"><c:out value="${auth.fullName}"/> </option>
              </c:forEach>
            </select>
          </td>
        </tr>

        <tr>
          <td> <c:out value="${language}"/> </td>
          <td>
            <select name="language" >
              <c:forEach var="lang" items="${langs}">
                <option value="${lang.title}"><c:out value="${lang.title}"/> </option>
              </c:forEach>
            </select>
          </td>
        </tr>

        <tr>
          <td><c:out value="${publisher}"/></td>
          <td><input type="text" name ="publisher" required/></td>
        </tr>

        <tr>
          <td> ISBN </td>
          <td><input type="text" name ="isbn" required/></td>
        </tr>

        <tr>
          <td><c:out value="${cat}"/></td>
          <td>
            <select name="category">
              <c:forEach var="categ" items="${categories}">
                <option value="${categ.id}"><c:out value="${categ.categoryName}"/> </option>
              </c:forEach>
            </select>
          </td>
        </tr>

        <tr>
          <td> <c:out value="${format}"/> </td>
          <td><select name="format">
            <c:forEach var="form" items="${formats}">
              <option value="${form.id}"><c:out value="${form.formatName}"/> </option>
            </c:forEach>
          </select></td>
        </tr>

        <tr>
          <td><c:out value="${price}"/></td>
          <td><input type="text" name ="price" required/></td>
        </tr>

        <tr>
          <td><c:out value="${qty}"/></td>
          <td><input type="text" name ="quantity" required/></td>
        </tr>

        <tr>
          <td><c:out value="${descr}"/> </td>
          <td><input type="text" name ="description" required/></td>
        </tr>
        <tr>
        <td><input type="file" name="file" required/></td>
        </tr>

      </table>
      <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>">
      <input type = "hidden" name = "service_name" value = "add_book">
      <input class = "btn accept" type = "submit" value = "${add_book}">
    </form>
    <br>
  </section>

</main>
</body>
</html>
