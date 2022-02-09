<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
  <head>
    <title>Index</title>
  </head>
  <body>
  <jsp:include page ="/header"/>
  <fmt:setLocale value = "${sessionScope.locale}" />
  <fmt:setBundle basename = "content" var = "content" scope = "session"/>
  <fmt:message bundle="${content}" key="SEE_MORE" var="see_more"/>
  <fmt:message bundle="${content}" key="ADD_TO_CART" var="add"/>
  <fmt:message bundle="${content}" key="ADDED" var="added"/>
  <fmt:message bundle="${content}" key="SHOP_NOW" var="shop_now"/>
  <jsp:useBean id = "cart" scope = "session" class = "entity.Cart"/>

  <main class = "index-main">
      <section id = "ads">
          <a href="/books?id=23"><button class="btn checkout"><c:out value="${shop_now}"/> </button> </a>
      </section>
      <section id = "index-books">
          <c:forEach var="book" items="${books}" begin="1" end="12">
              <div class = "index-book" onmouseover="showEl('book-add${book.id}')" onmouseleave="hideEl('book-add${book.id}')">
                  <img src = "/image-servlet?image_id=${book.id}&table=book_covers" alt = "${book.title}" style="height: 230px; max-width: 180px"><br>
                  <a href = "/book?id=${book.id}" class="index-book-name"><c:out value = "${book.title}"/></a>
                  <c:set var = "authorIds" value = "${book.authors}"/>
              <c:forEach var = "authorId" items = "${authorIds}">
                  <c:forEach var ="auth" items = "${authors}">
                      <c:if test = "${authorId == auth.id}">
                          <h4><a href = "/author?id=${auth.id}" class="index-author-name">${auth.fullName}</a></h4>
                      </c:if>
                  </c:forEach>
              </c:forEach>
                  <c:forEach var="format" items="${formats}">
                      <c:if test="${format.id == book.formatId}">
                          <c:out value="${format.formatName}"/>
                      </c:if>
                  </c:forEach>
                 <p class="index-book-price"><c:out value="${book.price}"/> ₸ </p>
                  <form id = "book-add${book.id}" style="display:none" onsubmit="showEl('grey-background${book.id}')"
                        action = "<%=request.getContextPath()%>/controller?id=${book.id}&qty=1" method = "post">
                  <input type="hidden" name="service_name" value="add_to_cart">
                      <input type="hidden" name="uri" value="<%=request.getRequestURI()%>">
                  <input type="submit" class="btn" style="margin-top: 5px; width: 170px; background-color: #24575c" value="${add}"/>
              </form>
              </div>
                  </c:forEach>
      </section>
      <button class = "btn checkout" style="margin: 0 20px 0 auto"><a href="/books"><c:out value="${see_more}"/></a></button>
  </main>
  <jsp:include page ="/footer"/>
  </body>
</html>
