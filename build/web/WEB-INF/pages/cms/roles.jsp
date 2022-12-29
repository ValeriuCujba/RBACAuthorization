
<%@page import="entities.Permission"%>
<%@page import="entities.Role"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Roles Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" 
              rel="stylesheet" 
              integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" 
              crossorigin="anonymous">
    </head>
    <body>
        <%
            List<Role> roles = (List<Role>) request.getAttribute("roles");
        %>
        <div class="container"> 
            <h1 class="text-center">All roles!</h1>
            <div>
                <a class="btn btn-primary" href="../cms/roles?action=showAdd" role="button">Create role</a>           
                <br>
            </div>

            <%
                boolean showAddForm = (boolean) request.getAttribute("showAddForm");
                if (showAddForm) {
                    List<Permission> permissions = (List<Permission>) request.getAttribute("permissions");

            %>
            <form>               
                <div class="mb-3">
                    <label for="permission" class="form-label" style="font-size: 20px">Role Name:</label>
                    <div><input type="text" class="form-control" name="roleName" required ></div>                
                </div> 
                <div>
                    <h4>Select permissions:</h4>
                    <% for (Permission permission : permissions) {%>
                    <div class="form-check">                   
                        <input class="form-check-input" type="checkbox" value="<%=permission.getId()%>" id="<%=permission.getId()%>" name="<%=permission.getId()%>">
                        <label class="form-check-label" for="<%=permission.getId()%>">
                            <%=permission.getName()%>
                        </label>
                    </div>
                    <%}%>
                </div>
                <button type="submit" name="action" value="save" class="btn btn-primary" >Create</button>
                <a class="btn btn-danger" href="../cms/roles?action=closeAdd" role="button">Cancel</a>   

            </form>

            <% }%>
            <!--Edit form-->
            <%
                boolean showEditForm = (boolean) request.getAttribute("showEditForm");
                if (showEditForm) {
                    List<Permission> permissions = (List<Permission>) request.getAttribute("permissions");
                    Role role = (Role) request.getAttribute("roleToUpdate");
            %>
            <form>    
                <div class="mb-3">                   
                    <div><input type="text" class="form-control" name="roleUpdateId" value="<%=role.getId()%>" hidden="" ></div>                
                </div> 
                <div class="mb-3">
                    <label for="permission" class="form-label" style="font-size: 20px">Role Name:</label>
                    <div><input type="text" class="form-control" name="roleUpdateName" required value="<%=role.getName()%>" ></div>                
                </div> 
                <div>
                    <h4>Select permissions:</h4>
                    <% for (Permission permission : permissions) {%> 

                    <% if (role.getPermissions().contains(permission)) {%>
                    <div class="form-check">                   
                        <input class="form-check-input" type="checkbox" value="<%=permission.getId()%>" id="<%=permission.getId()%>" name="update<%=permission.getId()%>" checked="checked">
                        <label class="form-check-label" for="<%=permission.getId()%>">
                            <%=permission.getName()%>
                        </label>
                    </div>
                    <% } else {%>                   

                    <div class="form-check">                   
                        <input class="form-check-input" type="checkbox" value="<%=permission.getId()%>" id="<%=permission.getId()%>" name="update<%=permission.getId()%>">
                        <label class="form-check-label" for="<%=permission.getId()%>">
                            <%=permission.getName()%>
                        </label>
                    </div>                    
                    <%}%>
                    <%}%>
                </div>
                <button type="submit" name="action" value="update" class="btn btn-primary" >Update</button>
                <a class="btn btn-danger" href="../cms/roles?action=closeAdd" role="button">Cancel</a>  
            </form>

            <% }%>

            <!--Roles table-->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Permissions</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Role role : roles) {%>
                    <tr>
                        <td> <%= role.getId()%></td>
                        <td> <%= role.getName()%></td>
                        <td> 
                            <ul> 
                                <%for (Permission permission : role.getPermissions()) {%>
                                <li><%= permission.getName()%> </li>                          

                                <%}%>
                            </ul>  
                        </td>
                        <td>
                            <a class="btn btn-outline-secondary" href="../cms/roles?id=<%=role.getId()%>&action=edit" role="button">Edit</a>
                            <a class="btn btn-danger" href="../cms/roles?id=<%=role.getId()%>&action=delete" role="button">Delete</a>
                        </td>
                    </tr
                    <% }%>

                </tbody>
            </table>
        </div>

    </body>
</html>
