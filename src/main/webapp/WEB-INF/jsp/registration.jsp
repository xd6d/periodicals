<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/signup.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="signup">
        <form action="create-user" method="post">
            <h3>Registration</h3>
            <label>
                <input type="text" placeholder="Username" name="username" required value="${param.username}">
            </label>
            <label>
                <input type="email" placeholder="Email" name="email" required value="${param.email}">
            </label>
            <label>
                <input type="password" placeholder="Password" name="password" value="${param.password}">
            </label>
            <label>
                <input type="password" placeholder="Confirm password" name="confirm" value="${param.confirm}">
            </label>
            <input type="submit" value="Create account">
        </form>
        <p>Already a reader? <a href="login">Log in.</a></p>
        <c:if test="${!empty requestScope.error}">
            <h3 class="error"><c:out value="${requestScope.error}"/></h3>
        </c:if>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
