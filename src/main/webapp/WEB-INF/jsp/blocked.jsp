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
    </style>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>
<div class="container-fluid">
    <div class="blocked">
        <h1>Your account has been blocked.</h1>
        <form action="/logout"><input type="submit" value="Log out"></form>
    </div>
</div>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
