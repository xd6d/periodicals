<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="publications">
    <c:if test="${sessionScope.user.role.role.equals('ADMIN')}">
        <div class="add">
            <a href="add">
                <button>+</button>
            </a>
        </div>
    </c:if>
    <c:forEach items="${requestScope.publications}" var="publication" begin="${(requestScope.page-1)*5}" end="${requestScope.page*5-1}">
        <div class="publication">
            <div class="publication-main">
                <div class="publication-wrapper">
                    <h2>${publication.title}</h2>
                    <div class="topics">
                        <c:if test="${publication.topics.size() != 0}">
                            <c:forEach items="${publication.topics}" var="topic">
                                <h4><c:out value="${topic.name}"/></h4>
                            </c:forEach>
                        </c:if>
                        <c:if test="${publication.topics.size() == 0}">
                            <h4>No associated topics</h4>
                        </c:if>
                    </div>
                </div>
                <form action="subscribe" method="post">
                    <label>
                        <input type="submit"
                               value="<c:out value="${publication.price} $"/>"
                               <c:if test="${empty sessionScope.user || sessionScope.user.balance - publication.price < 0}">disabled</c:if>
                               <c:if test="${!empty sessionScope.user && sessionScope.user.publications.contains(publication)}">hidden</c:if>
                        >
                    </label>
                    <input type="hidden" value="${publication.id}" name="publicationId">
                </form>
            </div>
            <c:if test="${sessionScope.user.role.role.equals('ADMIN')}">
                <div class="form-edit">
                    <form action="edit">
                        <input type="submit" value="Edit">
                        <input type="hidden" name="publicationId" value="${publication.id}">
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>