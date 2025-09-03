<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Author</title>
</head>
<body>
    <h1>
        New Author
    </h1>
    <form action="/authors" method="post">
        <p>Name:
            <input type="text" name="name" required>
            <button type="submit">Add</button>
        </p>
    </form>
</body>
</html>