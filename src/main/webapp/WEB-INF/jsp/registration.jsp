<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="sign_up" bundle="${lang}"/></title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/signup.css" %>
    </style>
</head>
<body>
<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="signup">
        <form action="create-user" method="post">
            <h3><fmt:message key="registration" bundle="${lang}"/></h3>
            <label>
                <input type="text" placeholder="<fmt:message key="username" bundle="${lang}"/>" name="username" required
                       value="${param.username}">
            </label>
            <label>
                <input type="email" placeholder="<fmt:message key="email" bundle="${lang}"/>" name="email" required
                       value="${param.email}">
            </label>
            <label>
                <input type="password" placeholder="<fmt:message key="password" bundle="${lang}"/>" name="password"
                       value="${param.password}">
            </label>
            <label>
                <input type="password" placeholder="<fmt:message key="confirm_password" bundle="${lang}"/>"
                       name="confirm" value="${param.confirm}">
            </label>
            <input type="submit" value="<fmt:message key="create_account" bundle="${lang}"/>">
        </form>
        <p><fmt:message key="already_reader" bundle="${lang}"/>? <a href="login"><fmt:message key="login"
                                                                                              bundle="${lang}"/>.</a>
        </p>
        <c:if test="${!empty requestScope.error}">
            <h3 class="error"><fmt:message key="${requestScope.error}" bundle="${lang}"/>.</h3>
        </c:if>
    </div>
</div>
<%@include file="fragment/footer.jspf" %>
</body>
</html>
