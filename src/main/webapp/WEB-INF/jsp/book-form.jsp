
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Book</title>
</head>
<body>
    <h1>
        New Book
    </h1>
    <form action="/books" method="post">
        <p>Name:
            <input type="text" name="title" required><br></p>
        <p>Author:
            <select name="authorId" required>
                <c:forEach var="auth" items="${authors}">
                    <option value="${auth.id}">${auth.name}</option>p
                </c:forEach>
            </select>
        </p>
        <button type="submit">Add</button>
    </form>
</body>
</html>