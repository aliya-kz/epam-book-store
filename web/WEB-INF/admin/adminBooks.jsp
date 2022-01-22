<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="entity.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Author" %>
<%@ page import="entity.Category" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <script src="/js/script.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
        <title>admin-books</title>
    </head>
<body>
<jsp:include page="/admin/adminHeader"/>

<% Locale locale = (Locale) session.getAttribute("locale");
    ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
    List <Book> books = (List<Book>) session.getAttribute("books");
    List <Author> authors = (List<Author>) session.getAttribute("authors");
    List <Category> categories = (List<Category>) session.getAttribute("categories");
%>


<section class="admin-filter">
    <h1><%=bundle.getString("SEARCH")%></h1>
    <input class="search-input" type="text" id="search-text" onkeyup="tableSearch('admin-books')">
</section>

<section class="add-book">


</section>

<section>
    <form action = "<%= request.getContextPath()%>/controller" method = "post">
        <table class="admin-table" id="admin-books">
        <th class = "admin-th"><%=bundle.getString("ID")%></th>
        <th class = "admin-th"><%=bundle.getString("TITLE")%></th>
        <th class = "admin-th"><%=bundle.getString("AUTHORS")%></th>
        <th class = "admin-th"><%=bundle.getString("CATEGORY")%></th>
        <th class = "admin-th"><%=bundle.getString("FORMAT")%></th>
        <th class = "admin-th"><%=bundle.getString("QUANTITY")%></th>
        <th class = "admin-th"><%=bundle.getString("LANGUAGE")%></th>
        <th class = "admin-th"><%=bundle.getString("PRICE")%></th>
        <th class = "admin-th"><%=bundle.getString("IMAGE")%></th>
        <th class = "admin-th"><%=bundle.getString("EDIT")%></th>
        <%for (Book book: books) {
        %>
        <tr>
            <td><%=book.getId()%></td>
            <td><%=book.getTitle()%></td>
            <td><%=book.getAuthors().toString().substring(1, book.getAuthors().toString().length()-1)%></td>
            <td><%=book.getCategoryId()%></td>
            <td><%=book.getFormatId()%></td>
            <td><%=book.getQuantity()%></td>
            <td><%=book.getLanguage()%></td>
            <td><%=book.getPrice()%></td>
            <td><a id = "a-img" href="#"><img src="/imageServlet?image_id=<%=book.getId()%>&table=book_covers" alt ="<%=book.getTitle()%>"></a></td>
            <td><a href="/editBook?id=<%=book.getId()%>" class="edit"><%=bundle.getString("EDIT")%></a></td>
        </tr>
        <%}%>
    </table>
    </form>
</section>

</form>
</body>
<jsp:include page="/WEB-INF/view/footer.jsp"/>
</html>
