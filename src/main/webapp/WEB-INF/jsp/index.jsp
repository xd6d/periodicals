<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="m" uri="http://example.com" %>
<%@ taglib prefix="n" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<%--<%@ taglib uri="/WEB-INF/tag-descriptor.tld" prefix="d" %>--%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>Periodicals</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
    <style>
        <%@include file="../../css/styles.css" %>
    </style>
</head>
<body>

<%@include file="fragment/header.jspf" %>
<div class="container-fluid">
    <div class="topics-sorts">
        <%@include file="fragment/topics.jspf" %>
        <%@include file="fragment/sorts.jspf" %>
    </div>

    <%@include file="fragment/search.jspf" %>
    <%@include file="fragment/publications.jspf" %>

</div>
<%@include file="fragment/pagination.jspf" %>
<%@include file="fragment/footer.jspf" %>
</body>
</html>