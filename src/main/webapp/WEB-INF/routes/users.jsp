<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section class="container d-flex justify-content-center align-items-center"
         style="min-height: 90vh; flex-direction: column">
    <h1>Users</h1>
    <h1 class="text-center">AllUsers</h1>

    <table class="table">
    <thead>
    <tr>
        <th scope="col">Name</th>
        <th scope="col">Mail</th>
        <th scope="col">Role</th>
        <th scope="col">Delete</th>
        <th scope="col">Edit</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
    <tr>
        <th scope="row">${user.username}</th>
        <th>${user.userEmail}</th>
        <th>${user.userRole}</th>
        <th>
            <form  method="post" >
                <button type="submit" class="btn btn-primary btn-lg" name="usertodelete" value=${user.username}>Delete</button>
            </form>
        </th>
        <th>
            <form  method="post" >
                <button type="submit" class="btn btn-primary btn-lg" name="usertoedit" value=${user.username}>Edit</button>
            </form>
        </th>
    </tr>
    </c:forEach>
    </tbody>
    </table>






</section>
