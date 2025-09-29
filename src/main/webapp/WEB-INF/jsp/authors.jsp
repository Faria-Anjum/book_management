
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"> -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Author List</title>
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
                    <a class="nav-link" href="/books">Books</a>
                    <a class="nav-link active" aria-current="page" href="#" href="/authors">Authors</a>
                </div>
            </div>
        </div>
        <!-- <div class="container-fluid">
            <form id=searchBar class="form-inline">
                <input id="keyword" name="keyword" class="form-control mr-sm-2" type="search" placeholder="Search by Author Name" aria-label="Search">
                <button class="btn btn-light my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>    -->
    </nav>
    <div class="container text-center py-2">
        <h1>Authors</h1>
    </div>
    <div class="container text-center my-2 d-flex justify-content-end">
        <a class="btn btn-dark mr-3" href="/authors/new" role="button">Add New</a>
        <button id="bulkDeleteButton" class="btn btn-dark">Bulk Delete</button>
        <!-- onclick="return confirm('Are you sure you want to delete the selected authors?' -->
    </div>
    <div class="container">
        <table class="table" id="authorTableRows">
            <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Edit Author</th>
                    <th scope="col">Delete Author</th>
                </tr>
            </thead>
             
        </table>
    </div>
    <div class="pagination justify-content-center mt-3" id="pagination">

    </div>
    
    <script>
        $(document).ready(function () {
            const table = $('#authorTableRows').DataTable({
                order: [[3, 'desc']],
                serverSide: true,   
                ajax: {
                    url: '/api/authors',
                    dataSrc: 'data'
                },
                columns: [
                    {
                        data: 'id',
                        render: function (data) {
                            checkbox = '<input type="checkbox" class="id-checkbox" value="'+data+'">';
                            return checkbox;
                        },
                        orderable: false
                    },
                    { data: 'id'},
                    { data: 'name'},
                    {
                        data: 'id',
                        render: function (data) {
                            editButton = '<a class="btn btn-dark btn-sm edit-btn text-white" data-id="'+data+'">Edit</a>';
                            return editButton;
                        },
                        orderable: false
                    },
                    {
                        data: 'id',
                        render: function (data, type, row) {
                        // console.log('Row object:', row, type);
                        // console.log('Data for id:', data);
                            deleteButton = '<a class="btn btn-dark btn-sm delete-btn text-white" data-id="'+data+'">Delete</a>';
                            return deleteButton;
                        },
                        orderable: false
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

            $('#authorTableRows').on('click', '.delete-btn', function () {
                let id = $(this).data('id');
                if (confirm('Are you sure you want to delete this author?')) {
                    $.ajax({
                        url: '/api/authors/delete/'+id,
                        type: 'GET',
                        success: function () {
                            table.ajax.reload();
                        }
                    });
                }
            });

            $('#authorTableRows').on('click', '.edit-btn', function(){
                let id = $(this).data('id');
                window.location.href = '/authors/new?id='+id;
            });

            $('#bulkDeleteButton').on('click', function(){
                let ids = [];
                $('.id-checkbox:checked').each(function(){
                    ids.push($(this).val());
                });

                if (ids.length==0){
                    alert('Please select authors to bulk delete');
                    return;
                }
                if(confirm('Are you sure you want to delete the selected authors?')){
                    $.ajax({
                        url: '/api/authors/bulk',
                        data: JSON.stringify(ids),
                        contentType: "application/json",
                        type: 'POST',
                        success: function () {
                            table.ajax.reload();
                        }
                    });
                }
            })
            

            // $('#bulkDeleteButton').on('click', function () {
            //     const ids = [];
            //     $('.id-checkbox:checked').each(function () {
            //         ids.push($(this).val());
            //     });

            //     if (ids.length === 0) {
            //         alert('No authors selected.');
            //         return;
            //     }

            //     if (confirm('Delete selected authors?')) {
            //         $.delete('/api/authors/bulk', { authorIds: ids }, function () {
            //             table.ajax.reload();
            //         });
            //     }
            // });
        });
    </script>

    <script src="https://cdn.datatables.net/2.3.4/js/dataTables.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>