<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Category" %>
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
        <title>admin-categories</title>
    </head>
<body>
<jsp:include page="/admin/adminHeader"/>

<% Locale locale = (Locale) session.getAttribute("locale");
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
    String msg = request.getParameter("msg");
    List<Category> categories = (List<Category>) session.getAttribute("categories");
%>
<%if (msg != null && msg.equals("error")) {%>
<div class="error-msg"><%=bundle.getString("ERROR_GEN")%></div>
<%}%>

<main class="admin-main">

    <section class="admin-filter">
        <h1><%=bundle.getString("SEARCH")%></h1>
        <input class="search-input" type="text" id="search-text" onkeyup="tableSearch('admin-categories')">
    </section>

    <section>
        <form action = "<%= request.getContextPath()%>/controller" method = "post">
            <h3> <%=bundle.getString("ADD_NEW_CATEGORY")%> </h3>
            <table class="admin-table">
                <th><%=bundle.getString("ID")%></th>
                <th><%=bundle.getString("CATEGORY")%></th>
                <th><%=bundle.getString("LANGUAGE")%></th>
                <th> </th>
                <tr>
                    <td> <input type="text" placeholder="<%=bundle.getString("INSERT_CATEGORY_ID")%>" name="new_id"/></td>
                    <td> <input type="text" placeholder="<%=bundle.getString("INSERT_CATEGORY_NAME")%>" name="new_category"/></td>
                    <td> <select name = "cat_lang">
                        <%List<Lang> langs = (List<Lang>) session.getAttribute("langs");
                            for (Lang lang: langs) {%>
                        <option value ="<%=lang.getTitle()%>"><%=lang.getTitle()%></option>
                        <%}%>
                    </select></td>
                    <td><input type="hidden" name="service_name" value="add_new_category">
                        <input type="submit" class="submit-btn" value = "<%=bundle.getString("ADD_CATEGORY")%>"> </td>
                </tr>
            </table>
        </form>

        <form action = "<%= request.getContextPath()%>/controller" method = "post">
            <h3> <%=bundle.getString("ALL_CATEGORIES")%>  </h3>
            <table class="admin-table" id="admin-categories">
                <th><%=bundle.getString("ID")%></th>
                <th><%=bundle.getString("CATEGORY")%></th>
                <th><%=bundle.getString("LANGUAGE")%></th>
                <th><%=bundle.getString("EDIT")%></th>
                <% for (Category category: categories) {
                        int id = category.getId();
                        String title = category.getCategoryName();
                        String language = category.getLang();
                %>
                <tr>
                    <td><%=id%></td>
                    <td><%=title%></td>
                    <td><%=language%></td>
                    <td><a href ="/editCategory?id=<%=String.valueOf(id)%>&categoryName=<%=title%>&lang=<%=language%>">
                        <%=bundle.getString("EDIT")%></a>
                    </td>
                </tr>
                <%}%>
            </table>
        </form>
    </section>
</main>
<jsp:include page="/WEB-INF/view/footer.jsp"/>
</body>
</html>
