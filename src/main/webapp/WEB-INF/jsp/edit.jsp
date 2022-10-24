<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit publication</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/edit.css" %>
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="edit">
        <form action="edit-publication" method="post">
            <h3>Edit publication</h3>
            <label>
                <input type="text" placeholder="Title" name="title" required value="${requestScope.publication.title}">
            </label>
            <c:forEach items="${requestScope.topics}" var="topic">
                <label>
                    <input type="checkbox" name="topic" value="${topic.id}" <c:if test="${requestScope.publication.topics.contains(topic)}">checked</c:if>/>
                    <c:out value="${topic.name}"/>
                </label>
            </c:forEach>
            <label>
                <input type="number" placeholder="Price ($)" name="price" value="${requestScope.publication.price}" step="0.01" min="0.01"
                       required>
            </label>
            <input type="hidden" name="publicationId" value="${requestScope.publication.id}">
            <input type="submit" value="Edit publication">
        </form>
    </div>
    <div class="delete" onsubmit="return confirm('Are you sure want to delete this publication?');">
        <form action="delete" method="post">
            <input type="submit" value="Delete">
            <input type="hidden" value="${requestScope.publication.id}" name="publicationId">
        </form>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
