<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: zhuma_rprmwfo
  Date: 04.01.2022
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% ResourceBundle bundle = ResourceBundle.getBundle("content", Locale.forLanguageTag("ru"));
%>
<h1> <%=bundle.getString("WELCOME")%>, <%=session.getAttribute("user_name")%></h1>
</body>
</html>
