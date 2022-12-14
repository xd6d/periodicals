<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="users" bundle="${lang}"/></title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/users.css" %>
    </style>
</head>
<body>
<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="users">
        <table>
            <tr>
                <th>ID</th>
                <th><fmt:message key="username" bundle="${lang}"/></th>
                <th><fmt:message key="email" bundle="${lang}"/></th>
                <th><fmt:message key="role" bundle="${lang}"/></th>
                <th><fmt:message key="balance" bundle="${lang}"/></th>
                <th><fmt:message key="blocked" bundle="${lang}"/></th>
            </tr>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.role.name}"/></td>
                    <td><c:out value="${user.balance} $"/></td>
                    <c:if test="${user.blocked}">
                        <td><fmt:message key="yes" bundle="${lang}"/></td>
                        <td>
                            <c:if test="${!user.role.name.equals('ADMIN')}">
                                <form action="/admin/unblock" method="post">
                                    <input type="submit" value="<fmt:message key="unblock" bundle="${lang}"/>">
                                    <input type="hidden" value="${user.username}" name="username">
                                </form>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${!user.blocked}">
                        <td><fmt:message key="no" bundle="${lang}"/></td>
                        <td>
                            <c:if test="${!user.role.name.equals('ADMIN')}">
                                <form action="/admin/block" method="post">
                                    <input type="submit" value="<fmt:message key="block" bundle="${lang}"/>">
                                    <input type="hidden" value="${user.username}" name="username">
                                </form>
                            </c:if>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<%@include file="fragment/footer.jspf" %>
</body>
</html>
