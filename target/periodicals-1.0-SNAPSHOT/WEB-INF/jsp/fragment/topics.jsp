<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="topics">
    <a href="?sort=${param.sort}&reversed=${param.reversed}&topic=all&search=${param.search}">All</a>
    <c:forEach items="${requestScope.topics}" var="topic">
        <a href="?sort=${param.sort}&reversed=${param.reversed}&topic=${topic.id}&search=${param.search}">
            <c:out value="${topic.name}"/>
        </a>
    </c:forEach>
</div>