<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sorts">
    Sort by:
    <div class="method">
        <a href="?sort=title&reversed=<c:if test="${param.reversed.equals('true') || (!empty param.reversed && !param.sort.equals('title'))}">false</c:if><c:if test="${param.reversed.equals('false') && param.sort.equals('title')}">true</c:if><c:if test="${!param.reversed.equals('false') && !param.reversed.equals('true')}">false</c:if>&topic=${param.topic}&search=${param.search}">
            Title
            <c:if test="${param.sort.equals('title') && param.reversed.equals('false')}">
            <span class="triangle">
                ▼
            </span>
            </c:if>
            <c:if test="${param.sort.equals('title') && param.reversed.equals('true')}">
            <span class="triangle">
                ▲
            </span>
            </c:if>
        </a>
    </div>
    <div class="method">
        <a href="?sort=price&reversed=<c:if test="${param.reversed.equals('true') || (!empty param.reversed && !param.sort.equals('price'))}">false</c:if><c:if test="${param.reversed.equals('false') && param.sort.equals('price')}">true</c:if><c:if test="${!param.reversed.equals('false') && !param.reversed.equals('true')}">false</c:if>&topic=${param.topic}&search=${param.search}">
            Price
            <c:if test="${param.sort.equals('price') && param.reversed.equals('false')}">
            <span class="triangle">
                ▼
            </span>
            </c:if>
            <c:if test="${param.sort.equals('price') && param.reversed.equals('true')}">
            <span class="triangle">
                ▲
            </span>
            </c:if>
        </a>
    </div>
    <div class="method">
        <a href="?sort=&reversed=<c:if test="${param.reversed.equals('true') || (!empty param.reversed && !param.sort.equals(''))}">false</c:if><c:if test="${param.reversed.equals('false') && param.sort.equals('')}">true</c:if><c:if test="${!param.reversed.equals('false') && !param.reversed.equals('true')}">false</c:if>&topic=${param.topic}&search=${param.search}">
            Date
            <c:if test="${param.sort.equals('') && param.reversed.equals('false')}">
            <span class="triangle">
                ▼
            </span>
            </c:if>
            <c:if test="${param.sort.equals('') && param.reversed.equals('true')}">
            <span class="triangle">
                ▲
            </span>
            </c:if>
        </a>
    </div>
</div>