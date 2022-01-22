<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: zhuma_rprmwfo
  Date: 04.01.2022
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error: user exists</title>
</head>
<body>
<% ResourceBundle bundle = ResourceBundle.getBundle("content", Locale.forLanguageTag("ru"));
%>
<h1> <%=bundle.getString("ERROR_EMAIL_EXISTS")%></h1>

<a href="/login"><%=bundle.getString("LOG_IN")%></a>
<a href="/signup"><%=bundle.getString("SIGN_UP")%></a>

</body>
</html>
