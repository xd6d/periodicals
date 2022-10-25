<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Log in</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/login.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="login">
        <form action="enter-account" method="post">
            <h3>Log in</h3>
            <label>
                <input type="email" placeholder="Email" name="email" required>
            </label>
            <label>
                <input type="password" placeholder="Password" name="password">
            </label>
            <input type="submit" value="Log in">
        </form>
        <p>New reader? <a href="registration">Create account.</a></p>
        <c:if test="${!empty requestScope.error}">
            <h3 class="error"><c:out value="${requestScope.error}"/></h3>
        </c:if>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
