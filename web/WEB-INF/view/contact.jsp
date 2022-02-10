<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact</title>
</head>
<body>
<jsp:include page="/header"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="EMAIL" var="email" />
<fmt:message bundle="${content}" key="NAME" var="name" />
<fmt:message bundle="${content}" key="PHONE_NUMBER" var="phone" />
<fmt:message bundle="${content}" key="MESSAGE" var="message" />

<main id="contact-main">
  <form id="contact-form" action="mailto:funnybox@list.ru" method="get" enctype="text/plain">
    <div>
      <label for="name"><c:out value="${name}"/></label>
        <input type="text" name="name" id="name" />
    </div>
    <div>
      <label for="email"><c:out value="${email}"/></label>
        <input type="text" name="email" id="email" />
    </div>
    <div>
      <label><c:out value="${message}"/></label>
      <br />
      <textarea name="comments" rows="12" cols="35"></textarea>
    </div>
    <div>
      <input class="btn accept" type="submit" name="submit" value="Send" />
      <input class="btn decline" type="reset" name="reset" value="Clear Form" />
    </div>
  </form>
  </main>
</body>
</html>