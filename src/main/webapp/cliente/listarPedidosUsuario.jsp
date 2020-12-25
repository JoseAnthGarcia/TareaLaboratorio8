<%@ page import="beans.PedidoBean" %>
<%@ page import="daos.PedidosUsuarioDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id= "listaPedidos" scope="request" type="java.util.ArrayList<beans.PedidoBean>" />
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
        .margen{
            margin-top: 2%;
        }
        .container-fluid{
            text-align: center;
            padding: 3% 15% ;
        }
        .page-item .page-link {
            color: #343a40;
            border-color: #343a40;
        }
        .page-item.active .page-link {
            border-color: #343a40;
            background-color: #343a40;
        }
    </style>
    <title>Mis pedidos</title>
</head>
<body>
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class ='container'>
    <h1 class="margen">Mis pedidos</h1>
    <div class="container-fluid">
        <table class="table container-fluid">
            <tr>
                <th>Código </th>
                <th>Costo total</th>
                <th>Estado</th>
                <th>Cancelar pedido</th>
            </tr>
            <% for (PedidoBean pedido: listaPedidos){%>
            <tr>
                <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=verDetallesPedido&idPedido=<%=pedido.getId()%>" ><%=pedido.getCodigo()%></a> </td>
                <td>S/. <%=pedido.getTotalApagar()%></td>
                <td><%=pedido.getEstado()%></td>
                <% if(pedido.getEstado().equalsIgnoreCase("Pendiente")){%>
                    <%if(PedidosUsuarioDao.verificarHoraPedido(Integer.parseInt(pedido.getCodigo()))){ %>
                    <td>
                        <a onclick="return confirm('¿Estas seguro que deseas cancelar tu pedido?')"
                        href="<%=request.getContextPath()%>/UsuarioServlet?accion=cancelarPedido&codigoPedido=<%=Integer.parseInt(pedido.getCodigo())%>"
                        class="btn btn-danger">Cancelar</a></td>
                    <%}else{ %>
                     <td>
                         <button onclick="alert('No es posible cancelar este pedido porque el tiempo límite de cancelación se ha cumplido')" class="btn btn-danger">
                             Bloquear</button>
                     </td>
                    <%} %>
                <% }else{ %>
                <td></td>
                <% }%>
            </tr>
            <% } %>
        </table>
    </div>
    <div class="row">
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger">Regresar</a>
        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar&pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>





                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar&pag=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar&pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
    </div>

    <div class="mt-5">
        <footer class="page-footer font-small blue" style="margin-top: 20px">

            <div class="footer-copyright text-center py-3">© 2020 Copyright:
                <a href="#">MiMarca</a>
            </div>

        </footer>
    </div>

</div>

</body>
</html>
