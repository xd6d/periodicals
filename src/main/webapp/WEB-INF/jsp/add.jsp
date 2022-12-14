<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
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
    <script type="text/javascript">
        let autofill = true

        function fillTitleUK() {
            if (autofill) {
                let inputEN = document.getElementById("inputEN")
                let inputUK = document.getElementById("inputUK")
                inputUK.value = inputEN.value
            }
        }

        function disableAutofill() {
            autofill = false
        }
    </script>
</head>
<body>
<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="new">
        <form action="/admin/new" method="post">
            <h3><fmt:message key="new_publication" bundle="${lang}"/></h3>
            <label>
                <input type="text" placeholder="<fmt:message key="title" bundle="${lang}"/>" name="title" required
                       value="${param.title}" oninput="fillTitleUK()" id="inputEN">
            </label>
            <label>
                <input type="text" placeholder="<fmt:message key="titleUK" bundle="${lang}"/>" name="titleUK" required
                       value="${param.titleUK}" id="inputUK" oninput="disableAutofill()">
            </label>
            <c:forEach items="${requestScope.topics}" var="topic">
                <label>
                    <input type="checkbox" name="topic" value="${topic.id}">
                    <c:out value="${topic.name}"/>
                </label>
            </c:forEach>
            <label>
                <input type="number" placeholder="<fmt:message key="price" bundle="${lang}"/> ($)" name="price"
                       value="${param.price}" step="0.01" min="0.01" required>
            </label>
            <input type="submit" value="<fmt:message key="add_publication" bundle="${lang}"/>">
            <input type="hidden" value="${param.publicationId}" name="publicationId">
        </form>
    </div>
</div>
<%@include file="fragment/footer.jspf" %>
</body>
</html>
