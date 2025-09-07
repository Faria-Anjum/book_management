<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Author</title>
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
                <a class="nav-link" href="/books">Books</a>
                <a class="nav-link" href="/authors">Authors</a>
            </div>
        </div>
    </div>
    </nav>
    <div class="container text-center py-2">
        <h1>
            <c:choose>
            <c:when test="${author.id != null}">
                Update Author
            </c:when>
            <c:otherwise>
                New Author
            </c:otherwise>
        </c:choose>
        </h1>
    </div>
    <div class="container text-center">
        <form action="/authors" method="post">
            <input type="hidden" name="id" value="${author.id}"/>
            <div class="form-group row">
                <label for="authorName" class="col-sm-2 col-form-label">Name</label>
                <div class="col-sm-10">
                <input type="text" class="form-control" id="authorName" placeholder="Name" name="name" value="${author.name}" required>
                </div>
            </div>
            <button type="submit" class="btn btn-dark">
                <!-- Add -->
                <c:choose>
                    <c:when test="${author.id != null}">Update</c:when>
                    <c:otherwise>Add</c:otherwise>
                </c:choose>
            </button>
        </form>
    </div>
    
</body>
</html>