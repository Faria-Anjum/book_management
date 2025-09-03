
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<body>
    <h1>Authors</h1>
    <a href="/authors/new">Add New Author</a>

    <table border="1" cellpadding="5">
        <tr>
            <th>
                ID
            </th>
            <th>
                Name
            </th>
        </tr>
        <c:forEach var="auth" items="${authors}">
            <tr>
                <td>
                    ${auth.id}
                </td>
                <td>
                    ${auth.name}
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>