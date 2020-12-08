<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="beans.BodegaBean" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.ProductoBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<div class="container" style="margin-top: 65px; margin-left: 15%; margin-right: 15%">
    <div class="row">
        <div class="col-sm-8">
            <h1>Mi carrito</h1>
        </div>
        <div class="col-sm-4">
            <img src="https://image.flaticon.com/icons/png/512/116/116356.png" height="50px"/>
        </div>
    </div>
    <form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=generarPedido">
    <div class="row">
        <%HttpSession session1 = request.getSession();
            //ArrayList<ProductoBean> listaProductos = (ArrayList<ProductoBean>) session1.getAttribute("carrito");
            //if(listaProductos.size()!=0){
            HashMap<Integer, ProductoBean> listaProductos = (HashMap<Integer, ProductoBean>) session1.getAttribute("carrito");
        if(!listaProductos.isEmpty()){%>
        <table class="table">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Producto</th>
                <th scope="col">Precio Unitario</th>
                <th scope="col">Stock</th>
                <th scope="col">
                </th>
            </tr>
            </thead>
            <tbody>
            <%//for (ProductoBean producto : listaProductos) {
                for (Map.Entry<Integer, ProductoBean> entry : listaProductos.entrySet()){%>
            <tr>
                <td>Imagen x</td>
                <td><%=entry.getValue().getNombreProducto()%>
                </td>
                <td><%=entry.getValue().getPrecioProducto()%>
                </td>
                <td>
                    <input name="<%=entry.getValue().getId()%>" type="number" min="0" id="Stock" class="form-control" >
                </td>
                <td>
                    <a class="btn btn-secondary" href="<%=request.getContextPath()%>/UsuarioServlet?accion=eliminarProductCarrito&productSelect=<%=entry.getValue().getId()%>">Eliminar</a>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="btn btn-outline-success">Regresar</a>
        <button type="submit" class="btn btn-outline-success">Generar pedido</button>
        <%}else{%>
        <div class="alert alert-secondary" role="alert">
            El carrito se encuentra vacio.
        </div>
        <%}%>

    </div>
    </form>

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