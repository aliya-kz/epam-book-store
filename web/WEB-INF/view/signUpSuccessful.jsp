<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: zhuma_rprmwfo
  Date: 24.12.2021
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Signed up</title>
</head>
<body>
<% ResourceBundle bundle = ResourceBundle.getBundle("content", Locale.forLanguageTag("ru"));
%>
<h1> <%=bundle.getString("SIGN_UP_COMPLETED")%> </h1>

<a href="/login"> <%=bundle.getString("LOG_IN")%> </a>
</body>
</html>
