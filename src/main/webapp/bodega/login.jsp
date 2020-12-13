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

    <title>Bienvenido Bodega!</title>
</head>
<body>

<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">

        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>MiMarca</strong>
            </a>

        </div>
    </div>
</header>

<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">Bievenido!</h1>
        <p class="lead text-muted">Gran cadena de bodegas.</p>
    </div>
</section>

<div class="container" style="margin-top: 80px;">
    <div class="row">
        <div class="col-lg-6">
            <img src="imagen_nulo.png" height="350px"/>
        </div>
        <div class="col-lg-6">

            <div>
                <h1 class="jumbotron-heading" style="margin-bottom: 30px; padding-bottom: 10px; border-bottom: 0.2px solid;">Ingrese sus datos proporcionados por la compañía</h1>
            </div>

            <form method="POST" action="<%=request.getContextPath()%>/LoginBodega">
                <div class="form-group">
                    <label for="inputRuc">Correo Electrónico</label>
                    <input type="text" class="form-control" id="inputRuc" name="inputEmail">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Contraseña</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" name="inputPassword">
                    <small id="emailHelp" class="form-text text-muted">No comparta su contraseña con nadie.</small>
                    <a href="<%=request.getContextPath()%>/LoginBodega?accion=actualizarContraCorreo">¿Se olvidó su contraseña?</a>
                </div>
                <% if (request.getParameter("error") != null) { %>
                <div class="text-danger mb-2">Error en usuario o contraseña</div>
                <% } %>
                <button type="submit" class="btn btn-primary my-2">Ingresar</button>
                <a href="#" class="btn btn-secondary my-2">Salir</a>
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
