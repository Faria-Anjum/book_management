
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Book List</title>
</head>
<body>
    <h1>Books</h1>
    <a href="/books/new">Add New Book</a>

    <table cellpadding="5">
        <tr>
            <th>
                ID
            </th>
            <th>
                Title
            </th>
            <th>
                Author
            </th>
        </tr>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>
                    ${book.id}
                </td>
                <td>
                    ${book.title}
                </td>
                <td>
                    ${book.author.name}
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>