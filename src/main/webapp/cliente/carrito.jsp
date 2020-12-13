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

    <title>Carrito</title>
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
        .primero{
            position: absolute;
            left: 15%;
        }

        .tercero{
            position: absolute;
            right: 15%;
        }
    </style>

</head>
<body>

<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>

<div class="container" style="margin-top: 65px; margin-left: 15%; margin-right: 15%">
    <div class="row col-12">
        <div class="col-sm-4">
            <h1>Mi carrito</h1>
        </div>

        <div class="col-sm-4">
        </div>


        <div class="col-sm-4">
            <img src="https://image.flaticon.com/icons/png/512/116/116356.png" height="50px"/>
        </div>
    </div>
    <form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=generarPedido">
    <div class="row mt-5">
        <%HttpSession session1 = request.getSession();
            HashMap<Integer, ProductoBean> listaProductos = (HashMap<Integer, ProductoBean>) session1.getAttribute("carrito");
        if(!listaProductos.isEmpty()){%>
        <div class="row mt-5 mx-auto">
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
                <%
                    for (Map.Entry<Integer, ProductoBean> entry : listaProductos.entrySet()){%>
                <tr>
                    <td>Imagen x</td>
                    <td><%=entry.getValue().getNombreProducto()%>
                    </td>
                    <td><%=entry.getValue().getPrecioProducto()%>
                    </td>
                    <td>
                        <%if(request.getParameter(String.valueOf(entry.getValue().getId()))==null){%>
                        <input name="<%=entry.getValue().getId()%>" type="number" min="1" id="Stock" class="form-control" >
                        <%}else{
                        String textValid = request.getParameter(String.valueOf(entry.getValue().getId())).equals("")
                                || Integer.parseInt(request.getParameter(String.valueOf(entry.getValue().getId())))<1
                                ?"is-invalid":"";
                        String textvalue = request.getParameter(String.valueOf(entry.getValue().getId())).equals("")?"":"value='"+request.getParameter(String.valueOf(entry.getValue().getId()))+"'";%>
                        <input name="<%=entry.getValue().getId()%>" type="number" min="1" id="Stock" class="form-control <%=textValid%>"
                        <%=textvalue%>>
                        <div class="invalid-feedback">
                            Ingrese una cantidad valida, por favor.
                        </div>
                        <%}%>
                    </td>
                    <td>
                        <a class="btn btn-secondary" href="<%=request.getContextPath()%>/UsuarioServlet?accion=eliminarProductCarrito&productSelect=<%=entry.getValue().getId()%>">Eliminar</a>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>


        <div class="row col-12 mt-5">
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="btn btn-outline-success primero">Regresar</a>
            <button type="submit" class="btn btn-outline-success tercero">Generar pedido</button>
        </div>
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