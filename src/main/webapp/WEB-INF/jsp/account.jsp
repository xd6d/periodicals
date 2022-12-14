<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="account" bundle="${lang}"/></title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/account.css" %>
    </style>
</head>
<body>
<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="account">
        <table>
            <tr>
                <td><h3><fmt:message key="username" bundle="${lang}"/>:</h3></td>
                <td><h3><c:out value="${sessionScope.user.username}"/></h3></td>
            </tr>
            <tr>
                <td><h3><fmt:message key="email" bundle="${lang}"/>:</h3></td>
                <td><h3><c:out value="${sessionScope.user.email}"/></h3></td>
            </tr>
            <c:if test="${!sessionScope.user.role.name.equals('ADMIN')}">
                <tr>
                    <td><h3><fmt:message key="balance" bundle="${lang}"/>:</h3></td>
                    <td><h3><c:out value="${sessionScope.user.balance}"/> $</h3></td>
                </tr>
            </c:if>
        </table>
        <div class="forms">
            <c:if test="${!sessionScope.user.role.name.equals('ADMIN')}">
                <form action="/user/replenish"><input type="submit"
                                                      value="<fmt:message key="replenish" bundle="${lang}"/>"></form>
            </c:if>
            <form action="/logout"><input type="submit" value="<fmt:message key="logout" bundle="${lang}"/>"></form>
        </div>
    </div>
</div>
<%@include file="fragment/footer.jspf" %>
</body>
</html>
