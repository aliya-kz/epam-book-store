<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <script src="/js/validation.js"></script>
    <title>Registration</title>
</head>
<body>
<% Locale locale = (Locale) session.getAttribute("locale");
    if (locale == null) {
        locale = Locale.ENGLISH;
        session.setAttribute("locale", locale);
    }
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
%>

<header class = "header">
    <div class="bar">
        <ul class="bar-list">
            <li><form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "lang" value="en"/>
                <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/> </form></li>
            <li> <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type = "hidden" name = "uri" value = "<%=request.getRequestURI()%>"/>
                <input type = "hidden" name = "lang" value="ru"/>
                <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form></li>
        </ul>
    </div>
</header>
<main class = "main-container">
<div class = "signup-container">
<h1> <%=bundle.getString("REGISTRATION")%> </h1>
        <form action = "<%= request.getContextPath()%>/controller" method = "post">
            <table style ="width: 80%">
                <tr>
                   <td> <%=bundle.getString("NAME")%> </td>
                    <td> <input type = "text" name = "name" required/></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("SURNAME")%> </td>
                    <td> <input type = "text" name = "surname" required /></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("DATE_OF_BIRTH")%> </td>
                    <td> <input type = "date" name = "date_of_birth" required/></td>
                </tr>
                <tr>
                    <td> Email </td>
                    <td> <input type = "email" name = "email" required/></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("PHONE_NUMBER")%>  </td>
                    <td> <input type = "text" name = "phone" required/></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("ADDRESS")%>  </td>
                    <td> <input type = "text" name = "address" required/></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("CARD_NUMBER")%>  </td>
                    <td> <input type = "text" name = "card" placeholder="This can be added later"/></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("PASSWORD")%>  </td>
                    <td> <input type = "password" name = "password" required/></td>
                </tr>
                <tr>
                    <td> <%=bundle.getString("CONFIRM_PASSWORD")%>  </td>
                    <td> <input type = "password" name = "password2" required/></td>
                </tr>
                <tr>
            </table>
            <input type = "hidden" name="service_name" value="sign_up" />
            <input type = "submit" value="<%=bundle.getString("REGISTER_BUTTON")%> " />
        </form>
    </div>
    <div class = "msg-div">
    <% String msg = request.getParameter("msg");
        if (msg != null) {
            switch (msg) {
                case "user_exists":%> <h1><%=bundle.getString("ERROR_EMAIL_EXISTS")%></h1><%;
                break;
                case "success": %> <h1><%=bundle.getString("SIGN_UP_COMPLETED")%></h1>
        <h2> <a href="/login"> <%=bundle.getString("LOG_IN")%> </h2><%;
                break;
                default: %><h1><%=bundle.getString("ERROR_GEN")%></h1><%;
            }
        }%>
</div>
</main>
</body>
</html>

