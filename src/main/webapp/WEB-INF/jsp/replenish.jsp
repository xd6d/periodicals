<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Replenish</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/replenish.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="replenish">
        <form action="replenish" method="post">
            <label>
                Replenish by: <input type="number" step="0.01" name="replenish" min="0.01"> $
            </label>
            <br>
            <label>
                <input type="submit" value="Replenish">
            </label>
        </form>
        <c:if test="${!empty requestScope.error}">
            <h3 class="error"><c:out value="${requestScope.error}"/></h3>
        </c:if>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
