<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Login</h1>


<form class="row g-3">
    <div class="col-md-6">
        <label for="userName" class="form-label">Email/Username</label>
        <input type="email" class="form-control" id="userName">
    </div>
    <div class="col-md-6">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password">
    </div>
    <div class="col-12">
        <button type="submit" class="btn btn-primary">Sign in</button>
    </div>
</form>
