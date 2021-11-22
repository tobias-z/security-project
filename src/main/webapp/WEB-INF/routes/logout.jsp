<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<section class="container d-flex justify-content-center align-items-center"
         style="min-height: 90vh; flex-direction: column">
    <h1>Logout ${sessionScope.userName} ?</h1>
<form class="row g-3" method="post">

    <div class="col-12">
        <button type="submit" class="btn btn-primary">logout</button>
    </div>


</form>
</section>
