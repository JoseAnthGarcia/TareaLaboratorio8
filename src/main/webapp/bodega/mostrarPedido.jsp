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
    PedidosDatosDTO pedido = (PedidosDatosDTO)request.getAttribute("pedido");
%>
<html>
<head>
    <title>Información de Pedido</title>
    <jsp:include page="../bootstrapRepository.jsp"/>

</head>
<body>
<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">

        </div>
    </div >
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>MiMarca.com</strong>
            </a>
            <a href="../MiBodega.html" class="navbar-brand d-flex align-items-center">
                <strong>Mi Bodega</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Productos</strong>
            </a>
            <a href="<%=request.getContextPath()%>/PedidosServlet" class="navbar-brand d-flex align-items-center">
                <strong>Pedidos</strong>
            </a>
            </a>
        </div>
    </div>
</header>
<div class="container" style="margin-top: 20px">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6">
                <h1 class='mb-3'>Información del Pedido <%=pedido.getCodigo()%></h1>
            </div>
        </div>
        <table class="table container-fluid">
            <div >
                <h4>Bodega : <%=pedido.getNombreBodega()%></h4>
            </div>
            <div>
                <h4>Fecha y hora de registro: <%=pedido.getFecha_registro()%></h4>
            </div>
            <div>
                <h4>Fecha y hora de recojo: <%=pedido.getFecha_recojo()%></h4>
            </div>
            <div>
                <h4>Fecha límite para cancelar el pedido: <%=pedido.getFecha_limite()%></h4>
            </div>
            <div>
                <h4>Unidades: <%=pedido.getUnidades() == 0 ? "Sin unidades" :pedido.getUnidades()%></h4>
            </div>
            <div>
                <h4>Costo: <%=pedido.getCosto_total() == null? "Sin costo" : pedido.getCosto_total()%></h4>
            </div>
            <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/PedidosServlet">Regresar</a>
        </table>
    </div>
<footer class="page-footer font-small blue" style="margin-top: 60px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#"> MiMarca.com</a>
    </div>
</footer>
</div>
</body>
</html>
