<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Index</title>
  </head>
  <body>
  <jsp:include page ="/WEB-INF/view/header.jsp"/>
  <fmt:setLocale value = "${sessionScope.locale}" />
  <fmt:setBundle basename = "content" var = "content" scope = "session"/>
  <fmt:message bundle="${content}" key="SEE_MORE" var="see_more"/>
  <jsp:useBean id = "cart" scope = "session" class = "entity.Cart"/>

  <main class = "index-main">
      <section id = "ads">
      </section>
      <section id = "index-books">
          <c:forEach var="book" items="${books}" begin="1" end="12">
              <div class = "index-book">
                  <img src = "/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}"><br>
                  <h3><a href = "/WEB-INF/view/book.jsp?id=${book.id}" class="index-book-name"><c:out value = "${book.title}"/></a></h3>
                  <c:set var = "authorIds" value = "${book.authors}"/>
              <c:forEach var = "authorId" items = "${authorIds}">
                  <c:forEach var ="auth" items = "${authors}">
                      <c:if test = "${authorId == auth.id}">
                          <h4><a href = "/WEB-INF/view/author.jsp?id=${auth.id}" class="index-author-name">${auth.fullName}</a></h4>
                      </c:if>
                  </c:forEach>
              </c:forEach>
                 <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
              </div>
          </c:forEach>
      </section>
      <button class = "see-more"><a href="/WEB-INF/view/books.jsp"><c:out value="${see_more}"/></a></button>
  </main>
  <jsp:include page ="/WEB-INF/view/footer.jsp"/>
  </body>
</html>
