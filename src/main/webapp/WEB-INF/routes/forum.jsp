<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="d-flex justify-content-center align-items-center" style="flex-direction: column">
    <h1>Greetings ${sessionScope.userName}</h1>
    <h3>What do you have on your mind?</h3>

    <div class="col-4" style="width: 700px">
        <jsp:include page="../components/topic-form.jsp"/>
        <jsp:include page="../components/topics.jsp"/>
    </div>

</section>
