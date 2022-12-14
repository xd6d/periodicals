<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="edit_publication" bundle="${lang}"/></title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
        <%@include file="../../css/edit.css" %>
    </style>
    <script>
        function confirmLocale() {
            let locale = document.getElementById("locale").value
            if (locale === "uk")
                return confirm('Ви точно хочете видалити цю публікацію?')
            return confirm('Are you sure want to delete this publication?')
        }
    </script>
</head>
<body>
<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="edit">
        <form action="/admin/edit-publication" method="post">
            <h3><fmt:message key="edit_publication" bundle="${lang}"/></h3>
            <label>
                <input type="text" placeholder="<fmt:message key="title" bundle="${lang}"/>" name="title" required
                       value="${requestScope.publication.title}">
            </label>
            <label>
                <input type="text" placeholder="<fmt:message key="titleUK" bundle="${lang}"/>" name="titleUK" required
                       value="${requestScope.publication.titleUK}">
            </label>
            <c:forEach items="${requestScope.topics}" var="topic">
                <label>
                    <input type="checkbox" name="topic" value="${topic.id}"
                           <c:if test="${requestScope.publication.topics.contains(topic)}">checked</c:if>/>
                    <c:out value="${topic.name}"/>
                </label>
            </c:forEach>
            <label>
                <input type="number" placeholder="<fmt:message key="price" bundle="${lang}"/> ($)" name="price"
                       value="${requestScope.publication.price}" step="0.01" min="0.01"
                       required>
            </label>
            <input type="hidden" name="publicationId" value="${requestScope.publication.id}">
            <input type="submit" value="<fmt:message key="edit_publication" bundle="${lang}"/>">
        </form>
    </div>
    <div class="delete" onsubmit="return confirmLocale();">
        <form action="/admin/delete" method="post">
            <input type="submit" value="<fmt:message key="delete" bundle="${lang}"/>">
            <input type="hidden" value="${requestScope.publication.id}" name="publicationId">
            <input type="hidden" value="${cookie.locale.value}" name="locale" id="locale">
        </form>
    </div>
</div>
<%@include file="fragment/footer.jspf" %>
</body>
</html>
