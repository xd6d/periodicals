<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <a href="/"><h1>Periodicals</h1></a>
    <div class="menu">
        <c:if test="${!empty sessionScope.user && sessionScope.user.role.name.equals('ADMIN')}">
            <a href="/admin/users">
                <h2>
                    Users
                </h2>
            </a>
        </c:if>
        <c:if test="${!empty sessionScope.user && sessionScope.user.role.name.equals('READER')}">
            <a href="/user/subscribed">
                <h2>
                    My publications
                </h2>
            </a>
        </c:if>
        <a href="/user/account">
            <h2>
                <c:if test="${!empty sessionScope.user}">
                    <c:out value="${sessionScope.user.username}"/>
                </c:if>
                <c:if test="${empty sessionScope.user}">
                    Account
                </c:if>
            </h2>
        </a>
    </div>
</header>