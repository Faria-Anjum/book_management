
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"> -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Author List</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Book Management</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-link" href="/books">Books</a>
                    <a class="nav-link active" aria-current="page" href="#" href="/authors">Authors</a>
                </div>
            </div>
        </div>
        <div class="container-fluid">
            <form class="form-inline" method="get">
                <input name="keyword" class="form-control mr-sm-2" type="search" placeholder="Search by Author Name" aria-label="Search">
                <button class="btn btn-light my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>   
    </nav>
    <div class="container text-center py-2">
        <h1>Authors</h1>
    </div>
    <div class="container text-center d-flex justify-content-end">
        <a class="btn btn-dark my-2" href="/authors/new" role="button">Add New Author</a>
    </div>
    <div class="container text-center">
        <table class="table">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <th scope="col">Edit Author</th>
                <th scope="col">Delete Author</th>
            </tr>
            <c:forEach var="auth" items="${authors}">
                <tr>
                    <td>${auth.id}</td>
                    <td>${auth.name}</td>
                    <td><a class="btn btn-dark btn-sm" role="button" href="/authors/edit/${auth.id}">Edit</a></td>
                    <td><a class="btn btn-dark btn-sm" role="button" href="/authors/delete/${auth.id}" onclick="return confirm('Are you sure you want to delete this author?')">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>