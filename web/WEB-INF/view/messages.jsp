
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Messages</title>
</head>
<jsp:include page="/header"></jsp:include>
<body>
<div id="central">
    <div class="content">
        <h1>Contact Form</h1>
        <p>Send your comments through this form and we will get back to
            you.</p>
                  <form id="contact-form"  action = "<%=request.getContextPath()%>/controller" method="POST"
                  novalidate="novalidate">
                <div class="label">Name:</div>
                <div class="field">
                    <input type="text" id="pp-name" name="name"
                           placeholder="enter your name here" title="Please enter your name"
                           class="required" aria-required="true" required>
                </div>
                <div class="label">Email:</div>
                <div class="field">
                    <input type="text" id="pp-email" name="email"
                           placeholder="enter your email address here"
                           title="Please enter your email address" class="required email"
                           aria-required="true" required>
                </div>
                <div class="label">Phone Number:</div>
                <div class="field">
                    <input type="text" id="pp-phone" name="phone"
                           placeholder="enter your phone number here"
                           title="Please enter your phone number" class="required phone"
                           aria-required="true" required>
                </div>
                <div class="label">Message:</div>
                <div class="field">
						<textarea id="about-project" name="message"
                                  placeholder="enter your message here"></textarea>
                </div>
                <div id="mail-status"></div>
                <input type="submit" name="submit" value="Send Message"
                       id="send-message" style="clear: both;">
                <%
                    if (null != message) {
                        out.println("<div class='" + status + "'>"
                                + message + "</div>");
                    }
                %>
            </form>
        </div>
    </div>
    <!-- content -->
<!-- central -->
</body>
</html>
