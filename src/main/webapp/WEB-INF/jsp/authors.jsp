
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
            <form id=searchBar class="form-inline">
                <input id="keyword" name="keyword" class="form-control mr-sm-2" type="search" placeholder="Search by Author Name" aria-label="Search">
                <button class="btn btn-light my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>   
    </nav>
    <div class="container text-center py-2">
        <h1>Authors</h1>
    </div>
    <div class="container text-center my-2 d-flex justify-content-end">
        <a class="btn btn-dark mr-3" href="/authors/new" role="button">Add New</a>
        <button id="bulkDeleteButton" class="btn btn-dark"
        onclick="return confirm('Are you sure you want to delete the selected authors?')">Bulk Delete</button>
    </div>
    <div class="container text-center">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">ID</th>
                    <th scope="col">Name</th>
                    <th scope="col">Edit Author</th>
                    <th scope="col">Delete Author</th>
                </tr>
            </thead>
            <tbody id="authorTableRows">
                <!-- <c:forEach var="auth" items="${authorlistPage.content}">
                    <tr>
                        <td>
                            <input type="checkbox" name="authorIds" value="${auth.id}">
                        </td>
                        <td>${auth.id}</td>
                        <td>${auth.name}</td>
                        <td><a class="btn btn-dark btn-sm" role="button" href="/authors/edit/${auth.id}">Edit</a></td>
                        <td><a class="btn btn-dark btn-sm" role="button" href="/authors/delete/${auth.id}" onclick="return confirm('Are you sure you want to delete this author?')">Delete</a></td>
                    </tr>
                </c:forEach> -->
            </tbody>
        </table>
    </div>
    <div class="pagination justify-content-center mt-3" id="pagination">
        <!-- <c:if test="${!authorlistPage.first}">
            <a href="?page=${authorlistPage.number - 1}&size=${authorlistPage.size}&keyword=${keyword}" class="btn btn-dark btn-sm mr-3">Previous</a>
        </c:if>
            Page ${authorlistPage.number+1} of ${authorlistPage.totalPages}
        <c:if test="${!authorlistPage.last}">
            <a href="?page=${authorlistPage.number + 1}&size=${authorlistPage.size}&keyword=${keyword}" class="btn btn-dark btn-sm ml-3">Next</a>
        </c:if> -->
    </div>
    
    <script>
        let currentPage = 0;
        let pageSize = 5;
        let keyword = "";

        function loadAuthors(page = 0, keyword = '') {
            $.ajax({
                url: "/api/authors",
                type: "GET",
                data: { page, size: pageSize, keyword},
                success: function (data){
                    const tbody = document.getElementById('authorTableRows');
                    tbody.innerHTML = '';
                    console.log(data);
                    data.content.forEach(author => {
                        const row = `
                            <tr>
                                <td><input type="checkbox" name="authorIds" value="\${author.id}"></td>
                                <td>\${author.id}</td>
                                <td>\${author.name}</td>
                                <td><a class="btn btn-dark btn-sm" role="button" href="/authors/edit/\${author.id}">Edit</a></td>
                                <td><a class="btn btn-dark btn-sm" role="button" href="/authors/delete/\${author.id}"
                                    onclick="return confirm('Are you sure you want to delete this author?')">Delete</a></td>
                            </tr>
                        `;
                        tbody.innerHTML += row;
                    });

                    const pagination = document.getElementById('pagination');
                    pagination.innerHTML = '';

                    if (!data.first) {
                        let prevBtn = document.createElement("button");
                        prevBtn.className = "btn btn-dark btn-sm mr-3";
                        prevBtn.textContent = "Previous";
                        prevBtn.addEventListener("click", () => loadAuthors(data.number - 1, keyword));
                        pagination.appendChild(prevBtn);
                    }

                    let pageInfo = document.createElement("span");
                    pageInfo.textContent = ` Page \${data.number+1} of \${data.totalPages}`;
                    pagination.appendChild(pageInfo);
                    

                    if (!data.last) {
                        let nextBtn = document.createElement("button");
                        nextBtn.className = "btn btn-dark btn-sm ml-3";
                        nextBtn.textContent = "Next";
                        nextBtn.addEventListener("click", () => loadAuthors(data.number + 1, keyword));
                        pagination.appendChild(nextBtn);
                    }
                },
                error: function (xhr) {
                    console.error("Error fetching authors:", xhr.responseText);
                }
            });
        }
                    // pagination.innerHTML += ` Page \${data.number + 1} of \${data.totalPages} `;
                // .catch(error => console.error('Error fetching authors:', error));
        

        // Load initial authors on page load
        document.addEventListener("DOMContentLoaded", () => {
            loadAuthors();

            document.getElementById("searchBar").addEventListener("submit", (e) =>{
                e.preventDefault();
                keyword = document.getElementById("keyword").value;
                loadAuthors(0, keyword);
            });
        });
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>