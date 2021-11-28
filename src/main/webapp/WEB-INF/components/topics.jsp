<%@ page import="com.insession.securityproject.domain.user.UserRole" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ADMIN" value="<%= UserRole.ADMIN %>"/>

<div class="mt-4">
    <c:if test="${requestScope.noTopics != null}">
        <h4>Oh no!</h4>
        <h5>${requestScope.noTopics}</h5>
    </c:if>

    <c:if test="${requestScope.topics != null}">
        <ul class="list-group">
            <c:forEach var="topic" items="${requestScope.topics}">
                <li class="list-group-item">
                    <div class="d-flex justify-content-between">
                        <strong><p><c:out value="${topic.user.username} - ${topic.createdAt}" /></p></strong>
                        <c:if test="${topic.user.username.equals(sessionScope.userName) || sessionScope.role.equals(ADMIN)}">
                            <form id="deleteTopic" method="post"
                                  action="${pageContext.request.contextPath}/actions/delete-topic">
                                <input type="hidden" name="topicId" value="${topic.id}"/>
                                <a href="#" onclick="this.parentNode.submit()"
                                   class="text-danger">Delete</a>
                            </form>
                        </c:if>
                    </div>
                    <p><c:out value="${topic.message}" /></p>

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