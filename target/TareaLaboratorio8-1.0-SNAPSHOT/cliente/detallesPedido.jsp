<%@ page import="dtos.ProductoCantDto" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="detalles" scope="request" type="dtos.DetallesPedidoDto"/>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <jsp:include page="/includes/utf8Cod.jsp"/>
    <title>Ver pedido</title>
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
            background-color: #767676;
        }
        .container-fluid {
            text-align:left;
            padding: 15px 15%;
        }



    </style>
</head>
<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class="container-fluid " style="margin-top: 30px">
    <div class="row mt-3">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <h1>Codigo del pedido: <%=detalles.getPedido().getCodigo()%></h1>
            <h4>Bodega: <%=detalles.getPedido().getBodegaBean().getNombreBodega()%></h4>
            <h4>Fecha y hora del registro: <%=detalles.getPedido().getFecha_registro()%></h4>
            <h4>Fecha y hora de entrega: <%=detalles.getPedido().getFecha_recojo()%></h4>
            <h4>Fecha limite para cancelar el pedido: <%=detalles.getFechaLimitCancel()%></h4>
            <table class="table mt-5" style="text-align: center;">
                <tr>
                    <th>Nombre producto</th>
                    <th>Precio unitario</th>
                    <th>Unidades</th>
                    <th>Costo parcial por producto</th>
                </tr>
                <%BigDecimal totalPagar = new BigDecimal("0");
                    for (ProductoCantDto productoDto: detalles.getListaProductCant()){%>
                <tr>
                    <td><%=productoDto.getProducto().getNombreProducto()%></td>
                    <td>S/. <%=productoDto.getProducto().getPrecioProducto()%></td>
                    <td><%=productoDto.getCant()%></td>
                    <%  BigDecimal cant = new BigDecimal(productoDto.getCant());
                        BigDecimal precioParc = productoDto.getProducto().getPrecioProducto().multiply(cant);%>
                    <td>S/. <%=precioParc%></td>
                </tr>
                <% totalPagar = totalPagar.add(precioParc);
                    } %>
            </table>
            <h1>Total a pagar: S/. <%=totalPagar%></h1>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar" class="button btn btn-primary mt-5">Regresar</a>
</div>

</body>
</html>
