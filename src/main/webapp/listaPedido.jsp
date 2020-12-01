<%@ page import = "java.util.ArrayList" %>
<%@ page import= "beans.PedidoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaPedidos" scope="request" type="java.util.ArrayList<beans.PedidoBean>"/>
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
<html>
<head>
    <title>Listar pedidos</title>
    <jsp:include page="bootstrapRepository.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .btn {
            background-color: #d6d2c4;
            border: none;
            color: black;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }
        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #f05454;
        }
        .margen{
            margin-top: 2%;
        }
        .container-fluid{
            text-align: center;
            padding: 3% 15% ;
        }
        .page-item .page-link {
            color: #767676;
            border-color: #767676;
        }
        .page-item.active .page-link {
            border-color: #767676;
            background-color: #767676;
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
            <a href="#" ><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9XQYb7eVu1VyTTjGNd69RWqaIge0precdjw&usqp=CAU.png" height="30px"/></a>
        </div>
    </div>
</header>
<div class="container" style="margin-top: 20px">
    <h1 class="margen">Pedidos</h1>
    <div class="container-fluid">
        <table class="table container-fluid">
            <tr>
                <th>Codigo</th>
                <th>Estado</th>
                <th></th>
                <th></th>
            </tr>
            <%for (PedidoBean pedidos : listaPedidos) { %>
            <tr>
                <td><a href="<%=request.getContextPath()%>/PedidosServlet?accion=mostrar&codigo=<%=pedidos.getCodigo() %>"><%=pedidos.getCodigo()%></a>
                </td>
                <td><%=pedidos.getEstado()%></td>
                <td>
                    <% if(pedidos.getEstado().equalsIgnoreCase("Pendiente")){
                    %>
                <a href="<%=request.getContextPath()%>/PedidosServlet?accion=entregar&codigo=<%=pedidos.getCodigo()%>" class="btn btn-outline-success">Pedido Entregado</a>
                <a href="<%=request.getContextPath()%>/PedidosServlet?accion=cancelar&codigo=<%=pedidos.getCodigo()%>" class="btn btn-outline-danger">Cancelar Pedido</a>
                    <% } %>
                </td>

            </tr>
            <% } %>
        </table>
        <div class="row">
            <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
                <ul class="pagination justify-content-center">
                    <%if(paginaAct==1){%>
                    <li class="page-item disabled">
                        <span class="page-link">Anterior</span>
                    </li>
                    <%}else{%>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/PedidosServlet?pag=<%=paginaAct-1%>">Anterior</a>
                    </li>
                    <%}%>

                    <% for(int k=1; k<=cantPag; k++){
                        if(k==paginaAct){%>
                    <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                    </li>
                    <%      }else{%>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/PedidosServlet?pag=<%=k%>"><%=k%></a></li>
                    <%      }
                    } %>


                    <%if(paginaAct==cantPag){%>
                    <li class="page-item disabled">
                        <span class="page-link">Siguiente</span>
                    </li>
                    <%}else{%>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/PedidosServlet?pag=<%=paginaAct+1%>">Siguiente</a>
                    </li>
                    <%}%>

                </ul>
            </nav>
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
