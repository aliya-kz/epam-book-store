<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<% ResourceBundle bundle = ResourceBundle.getBundle("content", Locale.forLanguageTag("ru"));
%>
<h3> <%=bundle.getString("ERROR_GEN")%></h3>
</body>
</html>
