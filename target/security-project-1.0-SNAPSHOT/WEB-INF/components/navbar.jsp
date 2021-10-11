<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul>
    <c:forEach var="item" items="${requestScope.navbarItems}">
        <li>
            <a href="<c:url value="${item.url}" />">${item.name}</a>
        </li>
    </c:forEach>
</ul>

<hr />