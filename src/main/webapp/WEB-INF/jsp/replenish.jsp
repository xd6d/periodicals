<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="replenish" bundle="${lang}"/></title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/replenish.css" %>
    </style>
</head>
<body>
<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="replenish">
        <form action="/user/add-balance" method="post">
            <label>
                <fmt:message key="replenish_by" bundle="${lang}"/>: <input type="number" step="0.01" name="replenish"
                                                                           min="0.01"> $
            </label>
            <br>
            <label>
                <input type="submit" value="<fmt:message key="replenish" bundle="${lang}"/>">
            </label>
        </form>
        <c:if test="${!empty requestScope.error}">
            <h3 class="error"><fmt:message key="${requestScope.error}" bundle="${lang}"/>.</h3>
        </c:if>
    </div>
</div>
<%@include file="fragment/footer.jspf" %>
</body>
</html>
