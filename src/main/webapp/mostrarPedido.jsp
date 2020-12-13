<%--
  Created by IntelliJ IDEA.
  User: lizbe
  Date: 8/11/2020
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import= "beans.PedidoBean" %>
<%@ page import="dtos.PedidosDatosDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PedidosDatosDTO pedido = (PedidosDatosDTO) request.getAttribute("pedido");
%>
<html>
<head>
    <title>Información de Pedido</title>
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


<div class="table container-fluid">
    <h1 class='mb-3'>Informacion del Pedido <%=pedido.getCodigo()%></h1>
        <div >
            <tr class='mb-3'>Bodega : <%=pedido.getNombreBodega()%></tr>
        </div>
        <div>
            <tr class='mb-3'>Fecha y hora de registro: <%=pedido.getFecha_registro()%></tr>
        </div>
        <div>
            <tr class='mb-3'>Fecha y hora de recojo: <%=pedido.getFecha_recojo()%></tr>
        </div>
        <div>
            <tr class='mb-3'>Fecha límite para cancelar el pedido: <%=pedido.getFecha_limite()%></tr>
        </div>
        <div>
            <tr class='mb-3'>Unidades: <%=pedido.getUnidades() == 0 ? "Sin unidades" :pedido.getUnidades()%></tr>
        </div>
        <div>
            <tr class='mb-3'>Costo: <%=pedido.getCosto_total() == null? "Sin costo" : pedido.getCosto_total()%></tr>
        </div>

</div>
<footer class="page-footer font-small blue" style="margin-top: 60px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#"> MiMarca.com</a>
    </div>
</footer>
</div>
</body>
</html>
