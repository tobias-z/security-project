<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mt-4">
    <c:if test="${requestScope.noTopics != null}">
        <h4>Oh no!</h4>
        <h5>${requestScope.noTopics}</h5>
    </c:if>

    <c:if test="${requestScope.topics != null}">
        <ul class="list-group">
            <c:forEach var="topic" items="${requestScope.topics}">
                <li class="list-group-item">
                    <strong><p>${topic.user.username} - ${topic.createdAt}</p></strong>
                    <p>${topic.message}</p>

                    <hr/>
                    <div class="d-flex justify-content-end">
                        <a href="${pageContext.request.contextPath}/forum/${topic.id}" class="text-secondary">
                            ${topic.comments.size()} Comments
                        </a>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>