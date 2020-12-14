<%@ page import="dtos.ProductoCantDto" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="detalles" scope="request" type="dtos.DetallesPedidoDto"/>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <title>Ver pedido</title>
</head>
<body>
<div class="container" style="margin-top: 30px">
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <h1>Codigo del pedido: <%=detalles.getPedido().getCodigo()%></h1>
            <h4>Bodega: <%=detalles.getPedido().getBodegaBean().getNombreBodega()%></h4>
            <h4>Fecha y hora del registro: <%=detalles.getPedido().getFecha_registro()%></h4>
            <h4>Fecha y hora de entrega: <%=detalles.getPedido().getFecha_recojo()%></h4>
            <h4>Fecha limite para cancelar el pedido: <%=detalles.getFechaLimitCancel()%></h4>
            <table class="table">
                <tr>
                    <th>Nombre producto</th>
                    <th>Precio unitario</th>
                    <th>Unidades</th>
                    <th>Costo por producto</th>
                </tr>
                <% Double totalPagar = 0.0;
                    for (ProductoCantDto productoDto: detalles.getListaProductCant()){%>
                <tr>
                    <td><%=productoDto.getProducto().getNombreProducto()%></td>
                    <td><%=productoDto.getProducto().getPrecioProducto()%></td>
                    <td><%=productoDto.getCant()%></td>
                    <% double precio = productoDto.getProducto().getPrecioProducto().doubleValue()*productoDto.getCant();%>
                    <td><%=String.format("%.2f", precio)%></td>
                </tr>
                <% totalPagar = totalPagar + precio;
                    } %>
            </table>
            <h1>Total a pagar: <%=String.format("%.2f", totalPagar)%></h1>
        </div>
        <div class="col-sm-2"></div>
    </div>
</div>
<a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home">Inicio</a>
</body>
</html>
