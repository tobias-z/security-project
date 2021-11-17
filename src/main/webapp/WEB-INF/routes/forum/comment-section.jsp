<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="d-flex justify-content-center align-items-center" style="flex-direction: column">
    <h1>${requestScope.topic.user.username} - ${requestScope.topic.createdAt}</h1>
    <p>${requestScope.topic.message}</p>

    <div style="width: 700px">
        <c:if test="${sessionScope.commentError != null}" var="error">
            <div class="col-12 alert alert-danger" role="alert">${sessionScope.commentError}</div>
        </c:if>

        <form method="post" class="mb-4">
            <div class="input-group">
                <label for="comment" class="form-label"></label>
                <textarea class="form-control" id="comment" name="comment" placeholder="Write a comment"
                          rows="1"></textarea>
                <button type="submit" class="btn btn-secondary">Submit comment</button>
            </div>
        </form>

        <c:forEach var="comment" items="${requestScope.topic.comments}">
            <p>${comment.createdAt} - ${comment.user.username}: ${comment.message}</p>
        </c:forEach>
    </div>
</section>