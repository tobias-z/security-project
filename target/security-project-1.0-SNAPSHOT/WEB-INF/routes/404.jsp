<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Error ${requestScope.errorCode}</title>
    <meta name="description" content="An error happened"  />
</head>
<body>

<main role="main">
    <h1>Error</h1>
    <p>${requestScope.errorMessage}</p>
</main>

</body>
</html>