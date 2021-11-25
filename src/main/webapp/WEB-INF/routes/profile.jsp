<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="container d-flex justify-content-center align-items-center"
         style="min-height: 90vh; flex-direction: column">

    <h1 class="text-center">Profile: ${sessionScope.userName}</h1>

    My page - My Rulez:

    <img src="${pageContext.request.contextPath}/images/${requestScope.imagefile}" alt="" height="200" width="200">

    <br/>
    <form enctype="multipart/form-data" method="post" style="width: 500px">
        <input type="file" name="file" alt=""/>
        <br/>
        <br/>
        Image types allowed: jpg, png, bmp, gif.
        <br/>
        Max file size: 2mb
        <br/>
        For best visual experience: image size: 200 x 200.
        <br/>
        <div class="col-12 d-grid gap-2 mt-3">
            <button type="submit" value="Upload" class="btn btn-primary btn-lg">upload</button>
            <button type="submit" class="btn btn-primary btn-md" name="imagetodelete" value=${sessionScope.userName} >Delete</button>
        </div>
           </form>
    <br/>

</section>

<!-- action="profile" id="UploadedPhoto" -->