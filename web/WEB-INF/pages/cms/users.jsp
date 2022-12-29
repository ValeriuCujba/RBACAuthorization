<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" 
              crossorigin="anonymous">
    </head>
    <body>
        <h1>Hello World!</h1>


        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Email</th>
                    <th scope="col">Roles</th>
                    <th scope="col">Actions</th>

                </tr>
            </thead>
            <tbody>

                <c:forEach var="user" items="${users}" >
                    <tr>
                        <td> <c:out value="${user.getId()}"/></td>
                        <td> <c:out value="${user.getFirstName()}"/></td>
                        <td> <c:out value="${user.getLastName()}"/></td>
                        <td> <c:out value="${user.getEmail()}"/></td>
                        <td> 
                            <ul>
                                <c:forEach var="role" items="${user.getRoles()}">
                                    <li> <c:out value="${role.getName()}"/></li>
                                </c:forEach>   
                            </ul>
                        </td>
                        <td> <a class="btn btn-outline-secondary" href="../cms/users?action=edit" role="button">Edit Roles</a>                       
                        </td>
                    </tr
                </c:forEach>


            </tbody>
        </table>

    </body>
</html>
