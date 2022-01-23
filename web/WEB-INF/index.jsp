<%@ page import="java.util.Map" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="bundleToListConverter.BundleMap" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Index</title>
  </head>
  <body>
  <fmt:setLocale value="${sessionScope.locale}" />
  <fmt:setBundle basename="content" var="content" scope="session"/>

  <%ResourceBundle bundle = ResourceBundle.getBundle("content");
  List<String> list = BundleMap.getInstance().convertBundleToList(bundle);
  request.setAttribute("bundleKeys", list);%>


  <c:out value="${LOG_IN}"/>
  </body>
</html>
