<%@ page import="com.insession.securityproject.domain.user.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ADMIN" value="<%= UserRole.ADMIN %>"/>

<section class="d-flex justify-content-center align-items-center" style="flex-direction: column">
    <h1><c:out value="${requestScope.topic.user.username} - ${requestScope.topic.createdAt}" /></h1>
    <p><c:out value="${requestScope.topic.message}" /></p>

    <div style="width: 700px">
        <c:if test="${sessionScope.commentError != null}" var="error">
            <div class="col-12 alert alert-danger" role="alert">${sessionScope.commentError}</div>
        </c:if>

        <form method="post" class="mb-4" action="${pageContext.request.contextPath}/actions/create-comment">
            <div class="input-group">
                <label for="comment" class="form-label"></label>
                <textarea class="form-control" id="comment" name="comment" placeholder="Write a comment"
                          rows="1"></textarea>
                <input type="hidden" name="topicId" value="${requestScope.topic.id}" />
                <button type="submit" class="btn btn-secondary">Submit comment</button>
            </div>
        </form>

        <c:forEach var="comment" items="${requestScope.topic.comments}">
            <div class="d-flex justify-content-between">
                <p><c:out value="${comment.createdAt} - ${comment.user.username}: ${comment.message}" /></p>
                <c:if test="${comment.user.username.equals(sessionScope.userName) || sessionScope.role.equals(ADMIN)}">
                    <form method="post"
                          action="${pageContext.request.contextPath}/actions/delete-comment">
                        <input type="hidden" name="commentId" value="${comment.id}"/>
                        <input type="hidden" name="topicId" value="${requestScope.topic.id}"/>
                        <a href="#" onclick="this.parentNode.submit()"
                           class="text-danger">Delete</a>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
</section>