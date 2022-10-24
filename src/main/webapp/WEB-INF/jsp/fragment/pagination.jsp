<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<ul class="pagination justify-content-center">
    <c:forEach items="${requestScope.publications}" var="publication" step="5">
        <li class="page-item">
            <a class="page-link"
               href="?sort=${param.sort}&reversed=${param.reversed}&topic=${param.topic}&search=${param.search}&page=<fmt:formatNumber value="${(requestScope.publications.indexOf(publication) / 5 + 1)}" maxFractionDigits="0"/>"
            >
                <fmt:formatNumber value="${(requestScope.publications.indexOf(publication) / 5 + 1)}"
                                  maxFractionDigits="0"/>
            </a>
        </li>
    </c:forEach>
</ul>