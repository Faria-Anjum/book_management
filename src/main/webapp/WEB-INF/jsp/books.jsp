<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/2.3.4/css/dataTables.dataTables.min.css"></script>
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
                    <a class="nav-link active" aria-current="page" href="/books">Books</a>
                    <a class="nav-link" href="/authors">Authors</a>
                </div>
            </div>
        </div>
    </nav>
    <div class="container text-center py-2">
        <h1>Books</h1>
    </div>
    <form action="/books/delete/bulk" method="post">
        <div class="container text-center my-2 d-flex justify-content-end">
            <a class="btn btn-dark mr-3" href="/books/new" role="button">Add New</a>
            <button type="submit" class="btn btn-dark"
            onclick="return confirm('Are you sure you want to delete the selected books?')">Bulk Delete</button>
        </div>
        <div class="container">
            <table class="table" id="bookTableRows">
            <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">ID</th>
                    <th scope="col">Title</th>
                    <th scope="col">Publication Date</th>
                    <th scope="col">Author</th>
                    <th scope="col">Edit Book</th>
                    <th scope="col">Delete Book</th>
                    <th scope="col">Cover</th>
                </tr>
            </thead>

            </table>
        </form>
    </div>
    <script>
        $(document).ready(function () {
            const table = $('#bookTableRows').DataTable({
                order: [[3, 'desc']],
                serverSide: true,   
                ajax: {
                    url: '/api/books',
                    dataSrc: 'data'
                },
                columns: [
                    {
                        data: 'id',
                        render: function (data) {
                            checkbox = '<input type="checkbox" name="bookIds" value="'+data+'">';
                            return checkbox;
                        },
                        orderable: false
                    },
                    { data: 'id'},
                    { data: 'title'},
                    { data: 'publicationDate'},
                    { data: 'author.name'},
                    {
                        data: 'id',
                        render: function (data) {
                            editButton = '<a class="btn btn-dark btn-sm" role="button" href="/books/new?id='+data+'">Edit</a>';
                            return editButton;
                        },
                        orderable: false
                    },
                    {
                        data: 'id',
                        render: function (data) {
                            deleteButton = '<a class="btn btn-dark btn-sm" role="button" href="/books/delete/'+data+'">Delete</a>';
                            return deleteButton;
                        },
                        orderable: false
                    },
                    {
                        data: 'imagePath',
                        render: function (data, type, row) {
                            if(data){
                                imgTag = '<img class="img-fluid" src="/images/'+data+'" alt="'+row.title+'" width="50" />';
                                return imgTag;
                            } else {
                                return '';
                            }
                        },
                        orderable: false,
                        searchable: false
                    }
                ],
                layout: {
                    topStart: {
                        pageLength: {
                            menu: [5, 10, 20, 50]
                        }
                    }
                },
                paging: true,
                pageLength: 5,
                searching: true
            });

            // $('#bookTableRows').on('click', .)
        });


    </script>
    <script src="https://cdn.datatables.net/2.3.4/js/dataTables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>