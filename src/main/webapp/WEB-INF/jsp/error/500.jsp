<%@ page contentType="text/html; charset=UTF-16BE" pageEncoding="UTF-16BE" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <title>Periodicals</title>
    <meta name="author" content="Artem Kurkin">
    <meta name="viewpoint" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../../../css/styles.css" %>
        <%@include file="../../../css/errors.css" %>
    </style>
</head>
<body>
<div class="container">
    <div class="error-page">
        <h1><fmt:message key="500.h1" bundle="${lang}"/></h1>
        <h3><fmt:message key="500.h3" bundle="${lang}"/></h3>
    </div>
</div>
</body>
</html>
