<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<section class="container d-flex justify-content-center align-items-center" style="min-height: 90vh; flex-direction: column">
    <h1>Pin code - Email</h1>
    <h4>Please check your email for the pin code to use</h4>

    <form class="row mt-3" method="post" style="width: 500px">
        <div class="col-12">
            <label for="pin-code" class="form-label">Pin Code</label>
            <input type="text" class="form-control form-control-lg" id="pin-code" placeholder="Enter Pin Code">
        </div>
        <div class="col-12 d-grid gap-2 mt-3">
            <button type="submit" class="btn btn-primary btn-lg">Submit</button>
        </div>
    </form>
</section>


