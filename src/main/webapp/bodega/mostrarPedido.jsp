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

    <style>
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
            background-color: #f05454;
        }
    </style>

</head>
<body>
<header>
    <jsp:include page="includes/headerBodega.jsp" />
</header>
<div class="container" style="margin-top: 20px">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2">
            </div>
            <div class="col-sm-8">
                <h1 class='mb-3'>Información del Pedido <%=pedido.getCodigo()%></h1>
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
                    <a class="btn btn-outline-danger" href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos">Regresar</a>
                </table>
            </div>
            <div class="col-sm-2">
            </div>
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
