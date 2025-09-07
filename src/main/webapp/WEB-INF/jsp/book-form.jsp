
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Book</title>
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
            <c:when test="${book.id != null}">
                Update Book
            </c:when>
            <c:otherwise>
                New Book
            </c:otherwise>
        </c:choose>
        </h1>
    </div>
    <div class="container">
        <form action="/books" method="post">
            <input type="hidden" name="id" value="${book.id}"/>
            <div class="form-group row">
                <label for="booktitle" class="col-sm-2 col-form-label">Title</label>
                <div class="col-sm-10">
                <input type="text" class="form-control" id="booktitle" placeholder="Title" name="title" value="${book.title}" required>
                </div>
            </div>
            <div class="form-group row">
                <label for="pubDate" class="col-sm-2 col-form-label">Published on</label>
                <div class="col-sm-10">
                <input type="date" max="${today}" name="publicationDate" class="form-control" id="pubDate" placeholder="Publication Date" value="${book.publicationDate}" required>
                </div>
            </div>
            <div class="form-group row">
                <label for="authorId" class="col-sm-2 col-form-label">Author</label>
                <div class="col-sm-10">
                <select class="custom-select mr-sm-2" id="authorId" name="authorId" required>
                    <c:forEach var="auth" items="${authors}">
                        <option value="${auth.id}">
                            <!-- ${auth.name} -->
                                <c:if test="${book.author != null && book.author.id == auth.id}">(Current) </c:if>${auth.name}
                        </option>
                    </c:forEach>
                </select>
                </div>
            </div>
            <!-- <button type="submit">Add</button> -->
            <div class="form-group row d-flex justify-content-center">
                <button type="submit" class="btn btn-dark">
                    <c:choose>
                        <c:when test="${book.id != null}">Update</c:when>
                        <c:otherwise>Add</c:otherwise>
                    </c:choose>
                </button>
            </div>
        </form>
    </div>
</body>
</html>