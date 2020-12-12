<%--
  Created by IntelliJ IDEA.
  User: lizbe
  Date: 8/11/2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import= "beans.PedidoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaPedidos" scope="request" type="java.util.ArrayList<beans.PedidoBean>"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="bootstrapRepository.jsp"/>

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
                <strong>MiMarca.com</strong>
            </a>
            <a href="../MiBodega.html" class="navbar-brand d-flex align-items-center">
                <strong>Mi Bodega</strong>
            </a>
            <a href="<%=request.getContextPath()%>/ClientServlet" class="navbar-brand d-flex align-items-center">
                <strong>Productos</strong>
            </a>
            <a href="<%=request.getContextPath()%>/PedidosServlet" class="navbar-brand d-flex align-items-center">
                <strong>Pedidos</strong>
            </a>
            </a>
        </div>
    </div>
</header>


<div class="container">
    <h1 class='mb-3'>Informacion del Pedido</h1>
    <%//TODO: Crear un DTO para mostrar la información del pedido %>
    <%for (PedidoBean pedidos : listaPedidos) { %>
    <tr>
        <td><a><%=pedidos.getCodigo()%></a>
        </td>
        <td><%=pedidos.getEstado()%></td>
        <td>
        </td>
    <%}%>
    </tr>

</div>
<footer class="page-footer font-small blue" style="margin-top: 60px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#"> MiMarca.com</a>
    </div>
</footer>
</div>
</body>
</html>
