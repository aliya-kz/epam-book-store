<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="/css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>admin-books</title>
    </head>
<body>
<jsp:include page="/admin/adminHeader"/>

<% Locale locale = (Locale) session.getAttribute("locale");
ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
%>
<main class="main">
    <table class="admin-table">
        <th class = "admin-th"><%=bundle.getString("ID")%></th>
        <th class = "admin-th"><%=bundle.getString("EMAIL")%></th>
        <th class = "admin-th"><%=bundle.getString("NAME")%></th>
        <th class = "admin-th"><%=bundle.getString("SURNAME")%></th>
        <th class = "admin-th"><%=bundle.getString("PHONE_NUMBER")%></th>
        <th class = "admin-th"> </th>
        <th class = "admin-th"> </th>
        <% List<User> list = (ArrayList<User>) session.getAttribute("users");
            for (User user : list) {
        %>
            <tr class="book-row">
                <td><%=user.getId()%></td>
                <td><%=user.getEmail()%></td>
                <td><%=user.getName()%></td>
                <td><%=user.getSurname()%></td>
                <td><%=user.getPhone()%></td>
                <td><%if (user.isBlocked() == false) {%>
                    <form action="<%= request.getContextPath()%>/controller" method="post">
                        <input type="hidden" name="user_id" value="<%=user.getId()%>"/>
                        <input type = "hidden" name = "blocked_status" value = "true"/>
                        <input type = "hidden" name="service_name" value="block_user"/>
                        <input class= "submit-btn" id ="decline" type = "submit" value ="<%=bundle.getString("BLOCK_USER")%>">
                    </form>
                    <% } else {%>
                    <form action="<%= request.getContextPath()%>/controller" method="post">
                        <input type="hidden" name="user_id" value="<%=user.getId()%>"/>
                        <input type = "hidden" name = "blocked_status" value = "false"/>
                        <input type = "hidden" name="service_name" value="block_user"/>
                        <input class= "submit-btn" id = "accept" type = "submit" value ="<%=bundle.getString("UNBLOCK_USER")%>">
                    </form>
                    <%}%>
                </td>
                <td> <form action="<%= request.getContextPath()%>/controller" method="post">
                    <button name="service_name" value="view_orders"><%=bundle.getString("VIEW_ORDERS")%></button>
                </form></td>
            </tr>
        <%}%>
    </table>

</main>
</form>
</body>
</html>

