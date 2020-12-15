<%--
  Created by IntelliJ IDEA.
  User: RIPLEY
  Date: 6/12/2020
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
    <style>
        body{
            background: #F5F5F5;
        }
    </style>

    <title>Bienvenido Bodega!</title>
</head>
<body>x

<div class="container" style="margin-top: 80px;">
    <div class="row">
        <div class="col-lg-6">
            <img style="border-radius: 50%" src="https://us.123rf.com/450wm/yupiramos/yupiramos1702/yupiramos170217136/72457937-bodega-de-almacenamiento-de-color-con-m%C3%BAltiples-cajas-y-la-ilustraci%C3%B3n-de-vector-de-carretilla-elevadora.jpg?ver=6" height="450px"/>
        </div>
        <div class="col-lg-6">

            <div>
                <h1 class="jumbotron-heading" style="margin-bottom: 30px; padding-bottom: 10px; border-bottom: 0.2px solid;">Ingrese sus datos proporcionados por la compañía</h1>
            </div>

            <form method="POST" action="<%=request.getContextPath()%>/LoginBodega">
                <div class="form-group">
                    <input type="text" placeholder="Correo Electrónico" class="form-control" id="inputRuc" name="inputEmail">
                </div>
                <div class="form-group">
                    <input type="password" placeholder="Contraseña" class="form-control" id="exampleInputPassword1" name="inputPassword">
                    <small id="emailHelp" class="form-text text-muted">No comparta su contraseña con nadie.</small>
                    <a href="<%=request.getContextPath()%>/LoginBodega?accion=actualizarContraCorreo">¿Se olvidó su contraseña?</a> <br>

                </div>
                <% if (request.getParameter("error") != null) { %>
                <div class="text-danger mb-2">Error en usuario o contraseña</div>
                <% } %>
                <button type="submit" class="btn btn-primary my-2">Ingresar</button>
                <a href="<%=request.getContextPath()%>/LoginServlet?accion=parteEmpresa
" class="btn btn-secondary my-2">Anterior</a>
            </form>
        </div>
    </div>

    <footer class="page-footer font-small blue" style="margin-top: 60px">
        <div class="footer-copyright text-center py-3">© 2020 Copyright:
            <a href="#"> MiMarca.com.pe</a>
        </div>
    </footer>

</div>

</body>
</html>
