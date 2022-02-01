<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <script src="/js/script.js"></script>
    <title>Profile</title>
</head>

<jsp:include page="/lang-bar"/>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="PERSONAL" var="pers" />
<fmt:message bundle="${content}" key="ORDERS" var="orders" />
<fmt:message bundle="${content}" key="CARDS" var="cards" />
<fmt:message bundle="${content}" key="CART" var="cart" />
<fmt:message bundle="${content}" key="WISH_LIST" var="wl" />
<fmt:message bundle="${content}" key="CHANGE_PASSWORD" var="change_pass" />
<fmt:message bundle="${content}" key="MESSAGES" var="messages" />
<fmt:message bundle="${content}" key="PASSWORD_UPDATED" var="pas_upd" />
<fmt:message bundle="${content}" key="EMAIL" var="email" />
<fmt:message bundle="${content}" key="PASSWORD" var="password" />
<fmt:message bundle="${content}" key="NAME" var="name" />
<fmt:message bundle="${content}" key="SURNAME" var="surname" />
<fmt:message bundle="${content}" key="ADDRESS" var="address" />
<fmt:message bundle="${content}" key="PHONE_NUMBER" var="phone" />
<fmt:message bundle="${content}" key="DATE_OF_BIRTH" var="date" />
<fmt:message bundle="${content}" key="CARD_NUMBER" var="card"/>
<fmt:message bundle="${content}" key="CONFIRM_PASSWORD" var="confirm_password"/>
<fmt:message bundle="${content}" key="EDIT" var="edit"/>
<fmt:message bundle="${content}" key="SAVE" var="save"/>
<fmt:message bundle="${content}" key="DELETE" var="delete"/>
<fmt:message bundle="${content}" key="ERROR_GEN" var="error_gen"/>
<fmt:message bundle="${content}" key="ADD" var="add"/>
<jsp:useBean id="user" class="entity.User" scope="session"/>
<jsp:getProperty name="user" property="surname"/>

<body>
<header class="profile">
<button id="profile-msg">
    <a href="/messages"></a>
</button>
</header>
<main class="profile-main">
    <aside>
        <ul>
            <li><a href = "/profile#prof-personal"><c:out value="${pers}"/> </a></li>
            <li><a href = "/profile#prof-cards"><c:out value="${cards}"/></a></li>
            <li><a href = "/profile#prof-address"><c:out value="${address}"/></a></li>
            <li><a href = "/profile#prof-change-password"><c:out value="${change_pass}"/></a></li>
            <li><a href = "/profile#prof-orders"><c:out value="${orders}"/></a></li>
            <li><a href = "/cart"><c:out value="${cart}"/></a></li>
            <li><a href = "/wish-list"><c:out value="${wl}"/></a></li>
        </ul>
    </aside>

    <div class="prof-container">
    <section id="prof-personal">
        <h1><c:out value="${pers}"/></h1>

        <button class="btn" onclick="editProfile()">${edit}</button>
            <form action = "<%= request.getContextPath()%>/controller?id=${user.id}" method = "post">
        <button class="btn" name="service_name" value="edit_profile" onclick ="editProfile()">${save}</button><br>
        <div class="profile-info">
                <h2><c:out value = "${email}"/>: </h2> <c:out value="${user.email}"/>

                <h2><label for = "surname"><c:out value = "${surname}"/>: </label></h2>
                    <c:out value="${user.surname}"/>
                <input class = "to-be-amended" id="surname" type = "text" name = "surname">

                <h2><label for = "name"><c:out value = "${name}"/>: </label></h2> <c:out value="${user.name}"/>
                <input class = "to-be-amended" id = "name" type = "text" name = "name">

                <h2><label for = "phone"><c:out value = "${phone}"/> </label></h2>
                    <c:out value="${user.phone}"/>
                <input class = "to-be-amended" id = "phone" type = "text" name = "phone">
        </div>
        </form>
    </section>

        <section id="prof-cards">
            <h1> <c:out value = "${cards}"/></h1>
            <c:forEach var="card" items="${user.cards}">
                <form action = "<%= request.getContextPath()%>/controller?id=${card.id}&table=cards" method = "post">
                    <h2> <c:out value="${card.cardNumber}"/> </h2>
                    <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                    <button name="service_name" value="delete_entity">${delete}</button>
                </form>
            </c:forEach>
            <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type="text" name="card"/>
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <button name="service_name" value="add_card">${add}</button>
            </form>
        </section>

        <section id="prof-address">
            <h1> <c:out value = "${address}"/></h1>
            <c:forEach var="addr" items="${user.addresses}">
                <form action = "<%= request.getContextPath()%>/controller?id=${addr.id}&table=addresses" method = "post">
                    <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                    <h2> <c:out value="${addr.address}"/> </h2> <button name="service_name" value="delete_entity">${delete}</button>
                </form>
            </c:forEach>
            <form action = "<%= request.getContextPath()%>/controller" method = "post">
                <input type="text" name="address"/>
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <button name="service_name" value="add_address">${add}</button>
            </form>
        </section>

        <section id="prof-change-password">
            <form action = "<%= request.getContextPath()%>/controller?id=${user.id}" method = "post">
            <h1><c:out value="${change_pass}"/></h1>
            <input type="password" name="password"/>
            <input type="password" name="new_password"/>
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <button name="service_name" value="change_password">${change_pass}</button>
                <div id="pass-changed">
                    <c:choose>
                        <c:when test="${pass-msg == success}"> <c:out value="${pas_upd}"></c:out> </c:when>
                        <c:when test="${pass-msg == error}"> <c:out value="${error_gen}"></c:out> </c:when>
                    </c:choose>
                </div>
                <input class = "to-be-amended" id = "card" type = "text" name = "card"/>
            </form>
        </section>
    <section id="prof-orders">

    </section>




    </div>
</main>



</body>
</html>