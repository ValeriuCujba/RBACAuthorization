

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
              rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" 
              crossorigin="anonymous">
    </head>
    <body>
        
        <div class="container">
            <h1 class="text-center">Registration Form:</h1>


        <form action="registration" method="POST" >
            <div class="mb-3">
                <label for="firstName" class="form-label">First Name: </label>
                <input type="text" class="form-control" id="firstName" name="firstName" required>                
            </div>
            <div class="mb-3">
                <label for="lastName" class="form-label">Last Name: </label>
                <input type="text" class="form-control" id="lastName" name="lastName" required>                
            </div>            
            <div class="mb-3">
                <label for="email" class="form-label">Email address</label>
                <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" required>              
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>            
            <button type="submit" name="action" value="save" class="btn btn-primary">Register</button>
        </form>
        </div>
        
        
    </body>
</html>
