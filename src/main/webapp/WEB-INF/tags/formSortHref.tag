<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="by" required="true" type="java.lang.String" %>
?sort=${by}
&reversed=
<c:if test="${param.reversed.equals('true') || (!empty param.reversed && !param.sort.equals(by))}">false</c:if>
<c:if test="${param.reversed.equals('false') && param.sort.equals(by)}">true</c:if>
<c:if test="${!param.reversed.equals('false') && !param.reversed.equals('true')}">false</c:if>
&topic=${param.topic}
&search=${param.search}
&page=${param.page}