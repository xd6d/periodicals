<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Subscribed</title>
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
    <div class="topics-sorts">
        <jsp:include page="fragment/topics.jsp"/>
        <jsp:include page="fragment/sorts.jsp"/>
    </div>
    <jsp:include page="fragment/search.jsp"/>
    <jsp:include page="fragment/publications.jsp"/>
<%--    <div class="publications">--%>
<%--        <c:forEach items="${publications}" var="publication">--%>
<%--            <div class="publication">--%>
<%--                <div class="publication-wrapper">--%>
<%--                    <h2>${publication.title}</h2>--%>
<%--                    <div class="topics">--%>
<%--                        <c:forEach items="${publication.topics}" var="topic">--%>
<%--                            <h4><c:out value="${topic.name}"/></h4>--%>
<%--                        </c:forEach>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>
</div>
<jsp:include page="fragment/pagination.jsp"/>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
