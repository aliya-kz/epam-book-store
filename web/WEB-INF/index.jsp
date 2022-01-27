<%@ page import="entity.Cart" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <link rel = "stylesheet" href="/css/style.css"/>
      <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400&display=swap" rel="stylesheet">
      <script src="/js/validation.js"> </script>
    <title>Index</title>
  </head>
  <body>
  <jsp:include page="/header"/>
  <fmt:setLocale value="${sessionScope.locale}" />
  <fmt:setBundle basename="content" var="content" scope="session"/>
  <jsp:useBean id="cart" scope="session" class="entity.Cart"/>
  <main class = "index-main">
      <section id = "bestsellers">
wide photo
      </section>
      <h3> Books </h3>
      <section id = "index-books">
          <c:forEach var="book" items="${books}" begin="1" end="12">
              <div class = "index-book">
                  <img src="/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}"><br>
                  <a href="/book?id=${book.id}" class="index-book-name">${book.title}</a><br>
                  <c:set var="authorIds" value="${book.authors}"/>
              <c:forEach var="authorId" items="${authorIds}">
                  <c:forEach var="auth" items="${authors}">
                      <c:if test = "${authorId == auth.id}">
                          <a href="/author?id=${auth.id}" class="index-author-name">${auth.fullName}</a><br>
                      </c:if>
                  </c:forEach>
              </c:forEach>
                 <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
              </div>
          </c:forEach>
      </section>
      <div class = "see-more"><a href="/books">see-more</a></div>
  </main>
  <jsp:include page="/footer"/>
  </body>
</html>
