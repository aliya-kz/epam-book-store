<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Author" %>
<%@ page import="entity.Lang" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <head>
        <script src="/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>admin-authors</title>
    </head>
<body>
<jsp:include page="/admin/adminHeader"/>

<% Locale locale = (Locale) session.getAttribute("locale");
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
    List<Author> authors = (List<Author>) session.getAttribute("authors");
    String msg = request.getParameter("msg");
    if (msg != null && msg.equals("error")) {%>
<div class = "error"> <%=bundle.getString("ERROR_GEN")%></div>
    <%}%>
<main>
    <section class="add-new-entity">
           <h3> <%=bundle.getString("ADD_AUTHOR")%> </h3>
            <table class="admin-table">
                <th><%=bundle.getString("NAME")%></th>
                <th><%=bundle.getString("SURNAME")%></th>
                <th><%=bundle.getString("BIOGRAPHY")%></th>
                <th><%=bundle.getString("LANGUAGE")%></th>
                <th><%=bundle.getString("IMAGE")%></th>
                <th> </th>

                <tr>
                    <form action = "/imageServlet?uri=<%=request.getRequestURI()%>" method="post" enctype="multipart/form-data">
                    <td> <input type="text"  name="name" required/></td>
                    <td> <input type="text"  name="surname" required/></td>
                    <td> <input type="text"  name="biography" required/></td>
                    <td><select name = "lang">
                            <%List<Lang> langs = (List<Lang>) session.getAttribute("langs");
                                for (Lang lang: langs) {%>
                            <option value ="<%=lang.getTitle()%>"><%=lang.getTitle()%></option>
                            <%}%>
                    </select></td>
                    <td><input type="file" name="file" required/></td>
                    <td><input type="hidden" name="service_name" value="add_new_author">
                        <input type="submit" class="submit-btn" value = "<%=bundle.getString("ADD_AUTHOR")%>"> </td>
                    </form>
                </tr>
            </table>
    </section>

    <section class="add-translation">
        <h3> <%=bundle.getString("ADD_TRANSLATION")%> </h3>
        <table class="admin-table">
            <th><%=bundle.getString("ID")%></th>
            <th><%=bundle.getString("NAME")%></th>
            <th><%=bundle.getString("SURNAME")%></th>
            <th><%=bundle.getString("BIOGRAPHY")%></th>
            <th><%=bundle.getString("LANGUAGE")%></th>
            <th> </th>

            <tr>
                <form action = "/controller?uri=<%=request.getRequestURI()%>" method="post">
                    <td> <input type="text"  name="id" required/></td>
                    <td> <input type="text"  name="name" required/></td>
                    <td> <input type="text"  name="surname" required/></td>
                    <td> <input type="text"  name="biography" required/></td>
                    <td><select name = "lang">
                        <%for (Lang lang: langs) {%>
                        <option value ="<%=lang.getTitle()%>"><%=lang.getTitle()%></option>
                        <%}%>
                    </select></td>
                    <td><input type="hidden" name="service_name" value="add_new_author">
                        <input type="submit" class="submit-btn" value = "<%=bundle.getString("ADD_TRANSLATION")%>"> </td>
                </form>
            </tr>
        </table>
    </section>

    <section class="admin-filter">
        <h1><%=bundle.getString("SEARCH")%></h1>
        <input class="search-input" type="text" id="search-text" onkeyup="tableSearch('admin-authors')">
    </section>

    <section class="all-entities">
        <form action = "<%=request.getContextPath()%>/controller" method = "post">
            <h3> <%=bundle.getString("ALL_AUTHORS")%>  </h3>
            <table class="admin-table" id="admin-authors">
                <th><%=bundle.getString("ID")%></th>
                <th><%=bundle.getString("NAME")%></th>
                <th><%=bundle.getString("SURNAME")%></th>
                <th><%=bundle.getString("BIOGRAPHY")%></th>
                <th><%=bundle.getString("IMAGE")%></th>
                <th> </th>
                <% for (Author author: authors) {
                    int id = author.getId();
                %>
                <tr>
                    <td><%=id%></td>
                    <td><%=author.getName()%></td>
                    <td><%=author.getSurname()%></td>
                    <td><%=author.getBiography()%></td>
                    <td><a href="#" class = "admin-image">
                        <img src="/imageServlet?id=<%=id%>&table=authors" alt="author" width="150px"/></a></td>
                    <td><a href ="/edit-author?id=<%=String.valueOf(id)%>">
                    <%=bundle.getString("EDIT")%></a></td>
                </tr>
                <%}%>
            </table>
        </form>
    </section>
</main>
<jsp:include page="/WEB-INF/view/footer.jsp"/>
</body>
</html>
