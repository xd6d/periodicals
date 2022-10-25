<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Periodicals</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/add.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="new">
        <form action="/admin/new" method="post">
            <h3>New publication</h3>
            <label>
                <input type="text" placeholder="Title" name="title" required value="${param.title}">
            </label>
            <c:forEach items="${requestScope.topics}" var="topic">
                <label>
                    <input type="checkbox" name="topic" value="${topic.id}">
                    <c:out value="${topic.name}"/>
                </label>
            </c:forEach>
            <label>
                <input type="number" placeholder="Price ($)" name="price" value="${param.price}" step="0.01" min="0.01" required>
            </label>
            <input type="submit" value="Add publication">
            <input type="hidden" value="${param.publicationId}" name="publicationId">
        </form>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
