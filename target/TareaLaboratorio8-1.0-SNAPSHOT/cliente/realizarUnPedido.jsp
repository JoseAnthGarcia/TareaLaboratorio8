<%@ page import="beans.ProductoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaProductos" type="java.util.ArrayList<beans.ProductoBean>" scope="request"/>

<% int cantPag = request.getAttribute("cantPag") == null ? -1 : (int) request.getAttribute("cantPag");%>
<% int paginaAct = request.getAttribute("paginaAct") == null ? -1 : (int) request.getAttribute("paginaAct");%>

<!DOCTYPE html>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Bienvenido Bodega!</title>
    <style>
        .izquierda1{
            left: 20px;
        }
        .izquierda2{
            left: 15px;
        }
        .izq{
            position: absolute;
            left: 15% ;
        }
        .der{
            position: absolute;
            right: 10% ;
        }
        .btn {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }

        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
    </style>

</head>
<body>

<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">

        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="navbar-brand d-flex align-items-center">
                <strong>MiMarca.com</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Productos disponibles</strong>
            </a>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="navbar-brand d-flex align-items-center">
                <strong>Realizar un pedido</strong>
            </a>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar" class="navbar-brand d-flex align-items-center">
                <strong>Mis pedidos</strong>
            </a>
            <a>
                <div class="card">
                    <a href="<%=request.getContextPath()%>/LoginServlet?accion=logout"><img src="imagenes/sigout.png" height="30px"/></a>
                </div>
            </a>

        </div>
    </div>
</header>

<div class="container" style="margin-top: 65px; margin-left: 15%; margin-right: 15%">

    <div class="row">
        <div class="col-sm-8">
            <h1>Realiza un pedido</h1>
            <h3>Bodega seleccionada: Todo Fresco</h3>
        </div>
        <div class="col-sm-4">
            <button class="btn btn-secondary" href="#">Cancelar y elegir otra bodega</button>
        </div>

    </div>

    <form method="post" action="<%=request.getContextPath()%>/UsuarioServlet?accion=buscar">
        <div class="form-group row">
            <div class="col-10">
                <input class="form-control" type="text" placeholder="Buscar producto"
                       name="textoBuscar"/>
            </div>
            <div class="col-2">
                <a class="btn btn-danger"
                   href="<%= request.getContextPath()%>/UsuarioServlet?accion=realizarPedido">Limpiar</a>
            </div>
        </div>
    </form>


    <div class="row">
        <%//Listar productos de cierta bodega%>
        <%if(listaProductos.size()!=0){%>
        <table class="table">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Producto</th>
                <th scope="col">Precio Unitario</th>
                <th scope="col">
                </th>
            </tr>
            </thead>
            <tbody>
            <%for (ProductoBean producto : listaProductos) {%>
            <tr>
                <td>Imagen x</td>
                <td><%=producto.getNombreProducto()%>
                </td>
                <td><%=producto.getPrecioProducto()%>
                </td>
                <td>
                    <button class="btn btn-secondary" href="#">Seleccionar</button>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>

    <div class="row mt-5">

        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger <%=listaProductos.size()!=0?"izq":""%>">Regresar</a>
        <%if (cantPag != -1) {%>
        <!-- paginacion -->
        <nav aria-label="Page navigation example" class="mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if (paginaAct == 1) {%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%} else {%>
                <li class="page-item">
                    <a class="page-link"
                       href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for (int k = 1; k <= cantPag; k++) {
                    if (k == paginaAct) {%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <% } else {%>
                <li class="page-item"><a class="page-link"
                                         href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=k%>"><%=k%>
                </a></li>
                <% }
                } %>


                <%if (paginaAct == cantPag) {%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%} else {%>
                <li class="page-item">
                    <a class="page-link"
                       href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
        <%}%>
        <a href="#" class="btn btn-outline-success <%=listaProductos.size()!=0?"der":""%>">Realizar un pedido</a>
        <%}else{%>
        <div class="row">
            <div class="izquierda1 col-12 alert alert-danger ml-2" role="alert">
                No se ha encontrado ningún producto con ese nombre.
            </div>
            <div class="izquierda2 col-2" >
                <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger">Regresar</a>
            </div>
        </div>


        <%}%>
    </div>


</div>
<div class="container mt-5">
    <footer class="page-footer font-small blue" style="margin-top: 20px">
        <div class="footer-copyright text-center py-3">© 2020 Copyright:
            <a href="#">MiMarca</a>
        </div>
    </footer>

</div>



</body>
</html>