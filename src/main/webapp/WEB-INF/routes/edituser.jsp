<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section class="container d-flex justify-content-center align-items-center"
         style="min-height: 90vh; flex-direction: column">
    <h1>Edit user</h1>

    <form class="row mt-3" method="post" style="width: 500px">
        <c:if test="${sessionScope.EditUserError != null}" var="error">
            <div class="col-12 alert alert-danger" role="alert">${sessionScope.EditUserError}</div>
        </c:if>
        <div class="col-12">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control form-control-lg" id="username" name="username" value="${requestScope.usertoedit.username}" disabled>
        </div>
        <div class="col-12 mt-3">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control form-control-lg" id="email" name="email" value="${requestScope.usertoedit.userEmail}">
        </div>
        <div class="col-12 mt-3">
            <label for="phone" class="form-label">Phone Number</label>
            <input type="number" class="form-control form-control-lg" id="phone" name="phone" value="${requestScope.usertoedit.phone}">
        </div>
        <div class="col-12 mt-3">
            <label for="role" class="form-label">Role</label>
            <select class="form-select" aria-label="select role" name="role" id="role" value="${requestScope.usertoedit.userRole}" >
                <c:if test="${requestScope.usertoedit.userRole =='USER'}">
                     <option >User</option>
                </c:if>
                <option>Admin</option>
            </select>
        </div>

        <div class="col-12 d-grid gap-2 mt-3">
            <button type="submit" class="btn btn-primary btn-lg">Edit</button>
        </div>
    </form>
</section>
