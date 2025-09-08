
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book List</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Book Management</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-link active" aria-current="page" href="#" href="/books">Books</a>
                <a class="nav-link" href="/authors">Authors</a>
            </div>
        </div>
    </div>
    </nav>
    <div class="container text-center py-2">
        <h1>Books</h1>
    </div>
    <div class="container text-center my-2  d-flex justify-content-end">
        <a class="btn btn-dark" href="/books/new" role="button">Add New Book</a>
    </div>
    <div class="container text-center">
        <table class="table">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Title</th>
                <th scope="col">Publication Date</th>
                <th scope="col">Author</th>
                <th scope="col">Edit Book</th>
                <th scope="col">Delete Book</th>
                <th scope="col">Cover</th>
            </tr>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.id}</td>
                    <td>${book.title}</td>
                    <td>${book.publicationDate}</td>
                    <td>${book.author.name}</td>
                    <td><a class="btn btn-dark btn-sm" role="button" href="/books/edit/${book.id}">Edit</a></td>
                    <td><a class="btn btn-dark btn-sm" role="button" href="/books/delete/${book.id}" onclick="return confirm('Are you sure you want to delete this book?')">Delete</a></td>
                    <td>
                        <c:if test="${not empty book.imagePath}">
                            <img class="img-fluid" src="/images/${book.imagePath}" alt="${book.title}" width="50" />
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>