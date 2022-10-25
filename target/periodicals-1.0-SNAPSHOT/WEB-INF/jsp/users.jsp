<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/users.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="users">
        <table>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Balance</th>
                <th>Blocked</th>
            </tr>
            <c:forEach items="${requestScope.users}" var="user">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.role.name}"/></td>
                    <td><c:out value="${user.balance} $"/></td>
                    <c:if test="${user.blocked}">
                        <td>Yes</td>
                        <td>
                            <c:if test="${!user.role.name.equals('ADMIN')}">
                                <form action="/admin/unblock" method="post">
                                    <input type="submit" value="Unblock">
                                    <input type="hidden" value="${user.username}" name="username">
                                </form>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${!user.blocked}">
                        <td>No</td>
                        <td>
                            <c:if test="${!user.role.name.equals('ADMIN')}">
                                <form action="/admin/block" method="post">
                                    <input type="submit" value="Block">
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
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
