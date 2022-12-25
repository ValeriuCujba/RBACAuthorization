
<%@page import="java.util.Comparator"%>
<%@page import="entities.Permission"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Permissions Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" 
              crossorigin="anonymous">
    </head>
    <body>              
        <%
            List<Permission> permissions = (List<Permission>) request.getAttribute("permissions");
            //sort list by id
            permissions.sort(Comparator.comparing(Permission::getId));

            Permission permissionToEdit = (Permission) request.getAttribute("permissionToEdit");
        %>

        <div class="container">
            <h1 class="text-center">All permissions!</h1>
            <div>
                <a class="btn btn-primary" href="../cms/permissions?action=showAdd" role="button">Add</a>
                <a class="btn btn-primary" href="../cms/permissions?action=showDelete" role="button">Delete</a>                 
            </div>

            <%
                boolean showAddForm = (boolean) request.getAttribute("showAddForm");
                if (showAddForm) {
            %>
            <form>
                <div class="mb-3">                    
                    <input name="permissionId" class="form-control"                            
                           value="<%=permissionToEdit.getId()%>" hidden="true">                    
                </div> 
                <div class="mb-3">
                    <label for="permission" class="form-label">Permission:</label>
                    <input type="text" class="form-control" id="permissionName" name="permissionName" value="<%=permissionToEdit.getName()%>">                    
                </div>               
                <button type="submit" name="action" value="saveOrUpdate" class="btn btn-primary" >Save</button>
            </form>

            <% }%>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col"></th>

                    </tr>
                </thead>
                <tbody>
                    <% for (Permission permission : permissions) {%>
                    <tr>
                        <td> <%= permission.getId()%></td>
                        <td> <%= permission.getName()%></td>
                        <%
                            boolean showDeleteButtons = (boolean) request.getAttribute("showDeleteButtons");
                            if (showDeleteButtons) {
                        %>

                        <td> <a class="btn btn-danger" href="../cms/permissions?id=<%=permission.getId()%>&action=delete" role="button">X</a>

                            <% } else {%>
                        <td> <a class="btn btn-outline-secondary" href="../cms/permissions?id=<%=permission.getId()%>&action=edit" role="button">Edit</a>

                            <% }%>

                        </td>

                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>

    </body>
</html>
