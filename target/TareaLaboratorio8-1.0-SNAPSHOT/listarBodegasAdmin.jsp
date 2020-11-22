<%@ page import="java.util.ArrayList" %>
<%@ page import="daos.BodegasAdminDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ArrayList<beans.BodegasAdminBean> listaBodegas = (ArrayList<beans.BodegasAdminBean>) request.getAttribute("lista");
%>

<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>


<html>
<head>
    <jsp:include page="bootstrapRepository.jsp"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .btn{
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
        .btn-activar{
            background-color: #8DBC81;
            border: none;
            color: black;
            padding: 12px 24px;
            font-size: 15px;
            cursor: pointer;
        }
        /* Darker background on mouse-over */
        .btn-activar:hover {
            background-color: #34A100;
            color: white;
        }
        .btn-boton2 {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }
        /* Darker background on mouse-over */
        .btn-boton2:hover {
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
    <title>Lista de bodegas</title>
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
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Administración</strong>
            </a>
            <a href="<%=request.getContextPath()%>/AdminServlet?accion=registrar" class="navbar-brand d-flex align-items-center">
                <strong>Registrar bodega</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Lista de bodegas</strong>
            </a>
            <a href="#" ><img src="imagenes/sigout.png" height="30px"/></a>
        </div>
    </div>
</header>
<div class ='container'>
    <h1 class="margen">Mis Bodegas</h1>
    <div class="container-fluid">
        <table class="table container-fluid">
            <tr>
                <th>RUC de la bodega</th>
                <th>Nombre de la bodega</th>
                <th>Estado</th>
                <th>Bloquear bodega</th>
            </tr>
            <%
                for(beans.BodegasAdminBean bodega : listaBodegas){
            %>
            <tr>
                <td><%= bodega.getRucBodega() %></td>
                <td><%= bodega.getNombreBodega() %></td>
                <td><%= bodega.getEstadoBodega() %></td>
                <% if(bodega.getEstadoBodega().toLowerCase().equals("activo")){%>
                <%if(BodegasAdminDao.pedidoPendiente(bodega.getNombreBodega())){ %>
                <td>
                    <a onclick="return confirm('No es posible bloquear esta bodega porque presenta al menos un pedido en estado pendiente')"
                       class="btn btn-danger">Bloquear</a></td>
                <% }else{%>
                <td>
                    <a onclick="return confirm('¿Estas seguro que deseas bloquear?')"
                       href="<%=request.getContextPath()%>/AdminServlet?accion=bloquear&nombreB=<%=bodega.getNombreBodega()%>&state=true"
                       class="btn btn-danger">Bloquear</a></td>
                <% }%>
                <% }else{%>
                <td><a onclick="return confirm('¿Estas seguro que deseas activar esta bodega?')"
                       href="<%=request.getContextPath()%>/AdminServlet?accion=bloquear&&nombreB=<%=bodega.getNombreBodega()%>&state=false"
                       class="btn btn-activar">Activar</a></td>
                <%} %>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <div class="row">

        <a href="#" class="btn btn-boton2">Volver</a>

        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/AdminServlet?pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/AdminServlet?pag=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/AdminServlet?pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
    </div>

</div>
</body>
</html>
