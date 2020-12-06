<%--
  Created by IntelliJ IDEA.
  User: jose_
  Date: 5/12/2020
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="beans.BodegaBean" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.ProductoBean" %>
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
        <%//Listar productos de cierta bodega%>
        <%HttpSession session1 = request.getSession();
            ArrayList<ProductoBean> listaProductos = (ArrayList<ProductoBean>) session1.getAttribute("carrito");
            if(listaProductos.size()!=0){%>
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
                    <button class="btn btn-secondary" href="<%=request.getContextPath()%>/UsuarioServlet?accion=agregarCarrito&productSelect=<%=producto.getId()%>">Seleccionar</button>
                </td>
            </tr>
            <%}%>
            <%}else{%>
            <div class="alert alert-secondary" role="alert">
                El carrito se encuentra vacio.
            </div>
            <%}%>
            </tbody>
        </table>
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