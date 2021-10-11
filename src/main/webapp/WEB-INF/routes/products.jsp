<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section class="container">
    <h1 class="text-center">All Products</h1>

    <table class="table">
        <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${requestScope.products}">
            <tr>
                <th scope="row">${product.name}</th>
                <th>${product.description}</th>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>