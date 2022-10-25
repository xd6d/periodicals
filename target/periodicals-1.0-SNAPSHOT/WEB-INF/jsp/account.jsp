<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Account</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/account.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="account">
        <table>
            <tr>
                <td><h3>Username:</h3></td>
                <td><h3><c:out value="${sessionScope.user.username}"/></h3></td>
            </tr>
            <tr>
                <td><h3>Email:</h3></td>
                <td><h3><c:out value="${sessionScope.user.email}"/></h3></td>
            </tr>
            <tr>
                <td><h3>Balance:</h3></td>
                <td><h3><c:out value="${sessionScope.user.balance}"/> $</h3></td>
            </tr>
        </table>
        <div class="forms">
            <c:if test="${!sessionScope.user.role.name.equals('ADMIN')}">
                <form action="/user/replenish"><input type="submit" value="Replenish"></form>
            </c:if>
            <form action="/logout"><input type="submit" value="Log out"></form>
        </div>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
