<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Book</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
        <h1 id="pageTitle">
            New Book
        </h1>
    </div>
    <div class="container">
        <form id="newBookForm" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${book.id}" id="bookId"/>
            <div class="form-group row">
                <label for="booktitle" class="col-sm-2 col-form-label">Title</label>
                <div class="col-sm-10">
                <input type="text" class="form-control" id="bookTitle" placeholder="Title" name="title" value="${book.title}" required>
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
                    <!-- <option value="">-- Select Author --</option> -->
                </select>
                </div>
            </div>
            <div class="input-group mb-3">
                <div class="custom-file">
                    <input type="file" value="${book.imagePath}" class="custom-file-input btn btn-sm btn-dark" role="button" id="bookImage" name="bookImage" accept="image/*">
                    <label class="custom-file-label" for="bookImage">Choose file</label>
                </div>
            </div>
            <div class="form-group row d-flex justify-content-center">
                <button type="submit" id="submitButton" class="btn btn-dark">
                    Add
                </button>
            </div>
        </form>
    </div>
    
    <script>
        $(function(){
            const params = new URLSearchParams(window.location.search);
            const id = params.get("id");

            if (id){
                $("#pageTitle").text("Update Book");

                $("#submitButton").text("Update");

                $.get('/api/books/'+id, function(book){
                    $('#bookId').val(book.id);
                    $('#bookTitle').val(book.title);
                    $('#pubDate').val(book.publicationDate);
                    $('#authorId').val(book.author.id);
                    console.log("B",book.author.id, book.author.name)
                    })
            }

            $.get('/api/authors/list', function(authors){
                    authors.forEach(function(auth){
                        $('#authorId').append(new Option(auth.name, auth.id));
                    })
                })

            $("#newBookForm").submit(function (e){
                e.preventDefault();

                let book = {
                    id: $("#bookId").val() || null,
                    title: $("#bookTitle").val(),
                    publicationDate: $('#pubDate').val()
                };

                let formData = new FormData();
                formData.append("book", new Blob([JSON.stringify(book)], { type: "application/json" }));
                formData.append("authorId", $("#authorId").val());

                let file = $("#bookImage")[0].files[0];
                if (file) {
                    formData.append("bookImage", file);
                }

                $.ajax({
                    url: "/api/books",
                    type: "POST",
                    processData: false,
                    contentType: false,
                    data: formData,
                    success: function () {
                        window.location.href = "/books";
                    },
                    error: function (xhr) {
                        alert("Error saving author: " + xhr.responseText);
                    }
                });
            });
        });
        document.querySelector('#bookImage').addEventListener('change', function(e){
            var fileName = e.target.files[0].name;
            e.target.nextElementSibling.innerText = fileName;
        });
    </script>
    
    <!-- <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script> -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    
    <!--<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script> -->
    
</body>
</html>