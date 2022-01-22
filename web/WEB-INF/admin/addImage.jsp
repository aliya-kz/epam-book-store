<%--
  Created by IntelliJ IDEA.
  User: zhuma_rprmwfo
  Date: 22.01.2022
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action = "<%= request.getContextPath()%>/controller?table=authors" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type = "submit" value = "upload">
</form>

</body>
</html>
