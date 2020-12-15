<%--
  Created by IntelliJ IDEA.
  User: Lapadula
  Date: 8/11/2020
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- validaciones de input -->
<%
    boolean validStock = request.getAttribute("validStock") != null ?
            ((boolean) request.getAttribute("validStock")) : true;

    boolean validPrecioUnitario = request.getAttribute("validPrecioUnitario") != null ?
            ((boolean) request.getAttribute("validPrecioUnitario")) : true;

    boolean validNombreProducto = request.getAttribute("validNombreProducto") != null ?
            ((boolean) request.getAttribute("validNombreProducto")) : true;
%>

<html>
<head>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>


    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>

        }
        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
    </style>

    <title>Agregar Producto</title>

</head>

<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">

        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>MiMarca.com</strong>
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
            <a>
                <div class="card"><a href="login.html" >
                    <img src="imagenes/sigout.png" height="30px"/>
                </a>
                </div>
            </a>
        </div>
    </div>
</header>
<p></p>
<main class="my-form">
    <div class="cotainer">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">Registrar Producto</div>
                    <div class="card-body">

                        <!-- FORMULARIO -->
                        <% System.out.println(request.getContextPath()); %>
                        <form method="POST" action="<%=request.getContextPath()%>/BodegaServlet?accion=guardar" enctype="multipart/form-data"
                        >

                            <!-- NOMBRE -->
                            <div class="form-group row">
                                <label for="nombreProducto" class="col-md-4 col-form-label text-md-right">Producto</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validNombreProducto?"":"is-invalid"%>" name="nombreProducto"
                                           id="nombreProducto"
                                           aria-describedby="validationServer05Feedback" required>
                                    <div id="validationServer05Feedback" class="invalid-feedback">
                                        No puede estar vacío
                                    </div>

                                </div>
                            </div>

                            <!-- DESCRIPCIO -->
                            <div class="form-group row">
                                <label for="Descripcion" class="col-md-4 col-form-label text-md-right">Descripción</label>
                                <div class="col-md-6">
                                    <textarea  type="text" id="Descripcion" class="form-control" name="descripcion">Ingrese una descripcion</textarea>
                                </div>
                            </div>

                            <!-- STOCK -->
                            <div class="form-group row">
                                <label for="Stock" class="col-md-4 col-form-label text-md-right">Stock</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validStock?"":"is-invalid"%>" name="stock"
                                           id="Stock"
                                           aria-describedby="validationServer03Feedback" required>
                                    <div id="validationServer03Feedback" class="invalid-feedback">
                                        Debe ser un número válido
                                    </div>
                                </div>
                            </div>

                            <!-- PRECIO UNITARIO -->
                            <div class="form-group row">
                                <label for="Precio" class="col-md-4 col-form-label text-md-right">Precio Unitario</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control <%=validPrecioUnitario?"":"is-invalid"%>" name="precioProducto"
                                           id="Precio"
                                           aria-describedby="validationServer04Feedback" required>
                                    <div id="validationServer04Feedback" class="invalid-feedback">
                                        Debe ser un número válido
                                    </div>
                                </div>

                            </div>

                            <!--imagenes -->
                            <div class="form-group row">
                                <label  class="col-md-4 col-form-label text-md-right">Imagen:</label>
                                <div class="col-md-6">
                                    <input type="file" name="foto">
                                </div>
                            </div>

                            <!-- BOTONES CONFIRMAR Y CANCELAR <-->
                            <div class="col-md-6 offset-md-4">
                                <a>
                                    <input type="submit" value="Confirmar" class="btn btn-outline-secondary">
                                </a>
                                <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/BodegaServlet">
                                        Cancelar
                                </a>
                            </div>

                    </div>
                </div>

                </form>
            </div>
        </div>
    </div>
    </div>
    </div>

    <footer class="page-footer font-small blue" style="margin-top: 60px">
        <div class="footer-copyright text-center py-3">© 2020 Copyright:
            <a href="#"> MiMarca.com.pe</a>
        </div>
    </footer>
</main>
</body>
</html>
