<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="producto" scope="request" type="beans.ProductoBean" />

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
                    <img src="signout.png" height="30px"/>
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
                        <form name="my-form" action="<%=request.getContextPath()%>/BodegaProductos?action=addProduct" method="POST">
                            <div class="form-group row">
                                <label for="nombreProducto" class="col-md-4 col-form-label text-md-right">Producto</label>
                                <div class="col-md-6">
                                    <input type="text" id="nombreProducto" class="form-control" name="nombreProducto" value="<%=producto.getNombreProducto()%>">
                                </div>
                            </div>


                            <div class="form-group row">
                                <label for="Descripcion" class="col-md-4 col-form-label text-md-right">Descripción</label>
                                <div class="col-md-6">
                                    <input type="text" id="Descripcion" class="form-control" name="descripcion" value="<%=producto.getDescripcion()%>">
                                </div>
                            </div>


                            <div class="form-group row">
                                <label for="Stock" class="col-md-4 col-form-label text-md-right">Stock</label>
                                <div class="col-md-6">
                                    <input type="number" min="0" id="Stock" class="form-control" name="stock" value="<%=producto.getStock()%>">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="Precio" class="col-md-4 col-form-label text-md-right">Precio Unitario</label>
                                <div class="col-md-6">
                                    <input type="number" min="0" step=".01" id="Precio" class="form-control" name="precioProducto" value="<%=producto.getPrecioProducto()%>">
                                </div>

                            </div>

                            <div class="form-group row">
                                <!--TODO: como se maneja el subir imagenes-->
                                <label for="Imagen de la bodega" class="col-md-4 col-form-label text-md-right">Imagen del Producto</label>
                                <div class="col-md-6">
                                    <form enctype="multipart/form-data" action="uploader.php" method="POST">
                                        <input name="uploadedfile" type="file" />
                                    </form>
                                    <img src="frutas.svg" height="60px"/>
                                </div>
                            </div>

                            <div class="col-md-6 offset-md-4">
                                <a href="<%=request.getContextPath()%>/BodegaServlet?action=lista">
                                    <button type="button" class="btn btn-outline-secondary">
                                        Confirmar
                                    </button>
                                </a>
                                <a href="<%=request.getContextPath()%>/BodegaServlet?action=lista">
                                    <button class="btn btn-outline-danger">
                                        Cancelar
                                    </button>
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
