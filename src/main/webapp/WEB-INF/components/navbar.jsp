<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Some Name</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <c:forEach var="item" items="${requestScope.navbarItems}">
                    <a class="nav-link ${item.active ? "active" : ""}"
                       href="<c:url value="${item.url}" />">${item.name}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</nav>

<ul>
</ul>

<hr/>