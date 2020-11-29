<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

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
                <strong>Anacleto.com</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Mi Bodega</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Productos</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Pedidos</strong>
            </a>
        </div>
    </div>
</header>

<div class="container" style="margin-top: 65px; margin-left: 210px">

    <div class="row">

        <div class="col-sm-6">
            <div class="row">
                <h1>Realiza un pedido</h1>
                <h3>Bodega seleccionada: #####</h3>
                <h5>Productos seleccionados:</h5>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="row mb-2">
                <button class="btn btn-secondary" href="#">Cancelar y elegir otra bodega</button>
            </div>

            <div class="row">
                <form>
                    <div class="form-group">
                        <input type="text" class="form-control" id="formGroupExampleInput" placeholder="Example input placeholder">
                    </div>
                </form>
            </div>

            <div class="row">
                <%//Listar productos de cierta bodega%>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Producto</th>
                        <th scope="col">Precio Unitario</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Imagen x</td>
                        <td>nombre del producto</td>
                        <td>precio</td>
                    </tr>
                    <tr>
                        <td>Imagen x</td>
                        <td>nombre del producto</td>
                        <td>precio</td>
                    </tr>
                    <tr>
                        <td>Imagen x</td>
                        <td>nombre del producto</td>
                        <td>precio</td>
                    </tr>
                    <tr>
                        <td>Imagen x</td>
                        <td>nombre del producto</td>
                        <td>precio</td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>

    </div>

</div>

<footer class="page-footer font-small blue" style="margin-top: 20px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>


</body>
</html>