<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="/css/style.css"/>
    <link rel="stylesheet" href="/css/formStyle.css"/>
    <script src="/js/validation.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300&display=swap" rel="stylesheet">
    <script src="/js/script.js"></script>
    <script src="https://kit.fontawesome.com/d45bb5fe4a.js" crossorigin="anonymous"></script>
    <title>Profile</title>
</head>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content" var="content" scope="session"/>
<fmt:message bundle="${content}" key="PERSONAL" var="pers"/>
<fmt:message bundle="${content}" key="ORDERS" var="ords"/>
<fmt:message bundle="${content}" key="CARDS" var="cards"/>
<fmt:message bundle="${content}" key="CART" var="cart"/>
<fmt:message bundle="${content}" key="WISH_LIST" var="wl"/>
<fmt:message bundle="${content}" key="CHANGE_PASSWORD" var="change_pass"/>
<fmt:message bundle="${content}" key="MESSAGES" var="messages"/>
<fmt:message bundle="${content}" key="PASSWORD_UPDATED" var="pas_upd"/>
<fmt:message bundle="${content}" key="EMAIL" var="email"/>
<fmt:message bundle="${content}" key="PASSWORD" var="password"/>
<fmt:message bundle="${content}" key="CUR_PAS" var="cur_pas"/>
<fmt:message bundle="${content}" key="NEW_PAS" var="new_pas"/>
<fmt:message bundle="${content}" key="NAME" var="name"/>
<fmt:message bundle="${content}" key="SURNAME" var="surname"/>
<fmt:message bundle="${content}" key="ADDRESS" var="address" scope="session"/>
<fmt:message bundle="${content}" key="PHONE_NUMBER" var="phone"/>
<fmt:message bundle="${content}" key="CARD_NUMBER" var="card_number"/>
<fmt:message bundle="${content}" key="CONFIRM_PASSWORD" var="confirm_password"/>
<fmt:message bundle="${content}" key="EDIT" var="edit"/>
<fmt:message bundle="${content}" key="SAVE" var="save"/>
<fmt:message bundle="${content}" key="DELETE" var="delete"/>
<fmt:message bundle="${content}" key="ADD" var="add"/>
<fmt:message bundle="${content}" key="ADDRESS" var="address"/>
<fmt:message bundle="${content}" key="STATUS" var="status"/>
<fmt:message bundle="${content}" key="COST" var="cost"/>
<fmt:message bundle="${content}" key="CANCEL" var="cancel"/>
<fmt:message bundle="${content}" key="PUBLISHER" var="publisher"/>
<fmt:message bundle="${content}" key="REFUND" var="refund"/>
<fmt:message bundle="${content}" key="CURRENT_PAS_WRONG" var="wrong"/>
<fmt:message bundle="${content}" key="ORDER_DATE" var="order_date"/>

<body>
<header class="profile-header">
    <section class="profile-bar">
        <div><a href="/index"><i class="fas fa-home fa-2x"></i></a></div>

        <div>
            <a href="/messages"><i class="far fa-envelope fa-2x"></i></a>
        </div>

        <div class="bar">
            <ul class="bar-list">
                <li>
                    <form action="<%= request.getContextPath()%>/controller" method="post">
                        <input type="hidden" name="locale" value="en_US"/>
                        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                        <input class="lang" id="eng" type="submit" name="service_name" value="change_language"/></form>
                </li>
                <li>
                    <form action="<%= request.getContextPath()%>/controller" method="post">
                        <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                        <input type="hidden" name="locale" value="ru_RU"/>
                        <input class="lang" id="rus" type="submit" name="service_name" value="change_language"/></form>
                </li>
                <li>
                    <form action="<%= request.getContextPath()%>/controller" method="post">
                        <input type="hidden" name="service_name" value="log_out"/>
                        <button class="fa-btn"><i class="fas fa-sign-out-alt fa-2x"></i></button>
                    </form>
                </li>
            </ul>
        </div>
    </section>

    <div class="nav-container">
        <nav class="nav-menu" id="header_nav">
            <ul>
                <li><a href="/profile#prof-personal"><c:out value="${pers}"/> </a></li>
                <li><a href="/profile#prof-cards"><c:out value="${cards}"/></a></li>
                <li><a href="/profile#prof-address"><c:out value="${address}"/></a></li>
                <li><a href="/profile#prof-change-password"><c:out value="${change_pass}"/></a></li>
                <li><a href="/profile#prof-orders"><c:out value="${ords}"/></a></li>
                <li><a href="/cart"><c:out value="${cart}"/></a></li>
                <li><a href="/wish-list"><c:out value="${wl}"/></a></li>
            </ul>
        </nav>
    </div>
</header>

<main class="profile-main">
    <section id="prof-personal">
        <h1><c:out value="${pers}"/>
            <button class="fa-btn" id="edit-profile-btn" onclick="editProfile()"
                    style="background: transparent; color:darkblue">
                <i class="fas fa-user-edit fa-lg"></i></button>
        </h1>
        <div class="profile-info">

            <form action="<%= request.getContextPath()%>/controller?id=${user.id}" method="post"
                  onsubmit="return checkPersonal(this)">
                <button class="btn" id="save-profile" type="submit" name="service_name" value="edit_profile"
                        onclick="checkPersonal()">${save}</button>
                <br>

                <h2><c:out value="${email}: ${user.email}"/></h2>
                <br>

                <div class="form-control">
                    <label for="surname"><c:out value="${surname}: ${user.surname}"/></label>
                    <input class="to-be-amended" style="display: none" id="surname" type="text" placeholder="${surname}"
                           name="surname">
                    <i class="fas-fa-check-circle"></i>
                    <i class="fas-fa-check-exclamation-circle"></i>
                    <small>Error message</small>
                </div>

                <div class="form-control">
                    <label for="name"><c:out value="${name}: ${user.name}"/></label>
                    <input class="to-be-amended" style="display: none" id="name" type="text" placeholder="${name}"
                           name="name">
                    <i class="fas-fa-check-circle"></i>
                    <i class="fas-fa-check-exclamation-circle"></i>
                    <small>Error message</small>
                </div>

                <div class="form-control">
                    <label for="phone"> <c:out value="${phone}: ${user.phone}"/> </label>
                    <input class="to-be-amended" style="display: none" id="phone" type="text" placeholder="${phone}"
                           name="phone">
                    <i class="fas-fa-check-circle"></i>
                    <i class="fas-fa-check-exclamation-circle"></i>
                    <small>Error message</small>
                </div>
            </form>
        </div>

    </section>

    <section id="prof-cards">
        <h1><c:out value="${cards}"/></h1>
        <div class="profile-info">
            <c:forEach var="card" items="${user.cards}">
            <form action="<%= request.getContextPath()%>/controller?id=${card.id}&table=cards" method="post">
                <i class="fas fa-credit-card fa-lg"></i> <c:out value=" ${card.cardNumber}"/>
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <input type="hidden" name="service_name" value="delete_entity">
                <button class="fa-btn"><i class="fas fa-trash-alt fa-lg"></i></button>
            </form>
            </c:forEach><br>

            <form action="<%= request.getContextPath()%>/controller" method="post" onsubmit="return checkCard(this)">
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <div class="form-control">
                    <label for="prof-card"></label>
                    <input id="prof-card" type="text" name="prof-card" style="float: left" required/>
                    <button name="service_name" class="fa-btn" value="add_card" onclick="checkCard()">
                        <i class="fas fa-plus-square fa-lg"></i></button>
                    <small>Error message</small>
                </div>
            </form>
    </section>

    <section id="prof-address">
        <h1><c:out value="${address}"/></h1>
        <div class="profile-info">
            <c:forEach var="addr" items="${user.addresses}">
                <form action="<%= request.getContextPath()%>/controller?id=${addr.id}&table=addresses" method="post">
                    <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                    <input type="hidden" name="service_name" value="delete_entity">
                    <h2><i class="fas fa-map-marker-alt fa-lg"> </i><c:out value="${addr.address}"/>
                        <button class="fa-btn"><i class="fas fa-trash-alt fa-lg"></i></button>
                    </h2>
                </form>
            </c:forEach><br>
            <form action="<%= request.getContextPath()%>/controller" method="post">
                <input type="text" name="address"/>
                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <button class="fa-btn" name="service_name" value="add_address"><i class="fas fa-plus-square fa-lg"></i>
                </button>
            </form>
        </div>
    </section>

    <section id="prof-change-password">
        <h1><c:out value="${change_pass}"/></h1>

        <div class="profile-info">
            <div class="msg-div">
                <%request.setAttribute("pasMsg", request.getParameter("pass-msg"));%>
                <c:if test="${not empty pasMsg}">
                    <c:if test="${msg eq 'success'}">
                        <c:out value="${pas_upd}"/>
                    </c:if>
                    <c:if test="${pasMsg eq 'error'}">
                        <c:out value="${wrong}"/>
                    </c:if>
                </c:if>
            </div>

            <form id="change-pass" action="<%= request.getContextPath()%>/controller?id=${user.id}" method="post">
                <div class="form-control">
                    <label for="password"><c:out value="${cur_pas}"/> </label>
                    <input id="password" type="password" name="password" required/>
                    <i class="fas-fa-check-circle"></i>
                    <i class="fas-fa-check-exclamation-circle"></i>
                    <small>Error message</small>
                </div>

                <div class="form-control">
                    <label for="new-password"><c:out value="${password}"/> </label>
                    <input id="new-password" type="password" name="new_password" required>
                    <i class="fas-fa-check-circle"></i>
                    <i class="fas-fa-check-exclamation-circle"></i>
                    <small>Error message</small>
                </div>

                <div class="form-control">
                    <label for="new-password1"><c:out value="${confirm_password}"/> </label>
                    <input id="new-password1" type="password" name="new_password_repeat" required>
                    <i class="fas-fa-check-circle"></i>
                    <i class="fas-fa-check-exclamation-circle"></i>
                    <small>Error message</small>
                </div>

                <input type="hidden" name="uri" value="<%=request.getRequestURI()%>"/>
                <button class="btn" type="submit" id="save-pass" name="service_name" value="change_password">
                    <c:out value="${save}"/></button>
            </form>

            <div class="msg-div">
                <%
                    String changePassMessage = (String) request.getAttribute("change_password_message");
                    request.setAttribute("changePassMessage", changePassMessage);
                %>
                <c:out value="${changePassMessage}"></c:out>
            </div>
        </div>
    </section>

    <section id="prof-orders">
        <h1><c:out value="${ords}"/></h1>
        <table class="admin-table">
            <tr>
                <th> #</th>
                <th><c:out value="${order_date}"/></th>
                <th><c:out value="${address}"/></th>
                <th><c:out value="${status}"/></th>
                <th><c:out value="${card_number}"/></th>
                <th><c:out value="${cost}"/>, ₸</th>
                <th></th>
            </tr>
            <c:forEach var="order" items="${myOrders}">
                <tr>
                    <td><c:out value="${order.id}"/></td>
                    <td><c:out value="${order.date}"/></td>
                    <td><c:out value="${order.address.address}"/></td>
                    <td>
                        <c:forEach var="stat" items="${statuses}">
                            <c:if test="${order.statusId == stat.id}">
                                <c:out value="${stat.statusName}"/>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td> <c:out value="${order.cardNumber}"/>
                    </td>
                    <td><c:out value="${order.cost}"/></td>
                    <td>
                        <form action="<%= request.getContextPath()%>/controller?order=${order.id}" method="post">
                            <input type="hidden" name="service_name" value="update_status">
                            <c:if test="${order.statusId == 4}">
                                <button class="btn accept" name="status" value="6"><c:out
                                        value="${refund}"></c:out></button>
                            </c:if>

                            <c:if test="${order.statusId < 3}">
                                <button class="btn decline" name="status" value="5"><c:out
                                        value="${cancel}"></c:out></button>
                            </c:if>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td colspan="6">
                        <c:forEach var="entry" items="${order.orderItems}">
                            <div class="profile-order-items">
                                <c:set var="book1" value="${entry.key}"/>
                                <c:set var="qty" value="${entry.value}"/>
                                <c:forEach var="book" items="${books}">
                                    <c:if test="${book.id == book1.id}">
                                        <div><img src="/image-servlet?image_id=${book.id}&table=book_covers"
                                                  alt="${book.title}" width="70px"/>
                                        </div>
                                        <div>
                                            <a href="/book?id=${book.id}" class="index-book-name"><c:out
                                                    value="${book.title}"/></a>
                                            <c:forEach var="author_id" items="${book.authors}">
                                                <c:forEach var="auth" items="${authors}">
                                                    <c:if test="${author_id == auth.id}">
                                                        <a href="/author?id=${author_id}"><h2><c:out
                                                                value="${auth.fullName}"/></h2></a>
                                                    </c:if>
                                                </c:forEach>
                                            </c:forEach>
                                            <c:out value="${publisher}"/>: <c:out value="${book.publisher}"/><br>
                                            <c:out value="${format}"/> <c:out value="${book.format}"/><br>
                                            <h2><c:out value="${qty} x ${book.price}"/> ₸</h2>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </section>
</main>
</body>
</html>
