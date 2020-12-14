<%@ page import="beans.BodegaBean" %><%--
  Created by IntelliJ IDEA.
  User: lizbe
  Date: 13/12/2020
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
   BodegaBean bodega = (BodegaBean) request.getAttribute("bodega");
%>
<!doctype html>
<html lang="en">
<head>
    <title>Home</title>
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
            <a href="<%=request.getContextPath()%>/BodegaServlet?accion=home" class="navbar-brand d-flex align-items-center">
                <strong>Mi Bodega</strong>
            </a>
            <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listar" class="navbar-brand d-flex align-items-center">
                <strong>Productos</strong>
            </a>
            <a href="<%=request.getContextPath()%>/PedidosServlet" class="navbar-brand d-flex align-items-center">
                <strong>Pedidos</strong>
            </a>
            <a href="#" ><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9XQYb7eVu1VyTTjGNd69RWqaIge0precdjw&usqp=CAU.png" height="30px"/></a>
        </div>
    </div>
</header>
<div class="jumbotron text-right">
    <div class="container">
        <h1 class="display-3"><%=bodega.getNombreBodega()%> </h1>
    </div>
</div>
<div class=" Container">

    <div class="row">

        <div class="col-lg-6 col-md-6 centrar">
            <img src="<%=request.getContextPath()%>/ImagenServlet?idBodega=<%=bodega.getIdBodega()%>" width="480px" height="480px" />
        </div>
        <div class="col-lg-6 col-md-12 centrar">
            <table class="table">
                <tr>
                    <h4>RUC: <%=bodega.getRucBodega()%></h4>
                </tr>
                <tr>
                    <h4>Dirección: <%=bodega.getDireccionBodega()%> </h4>
                </tr>
                <tr>
                    <h4>Distrito: <%=bodega.getDistrito().getNombre()%> </h4>
                </tr>
                <tr>
                    <h4>Correo: <%=bodega.getCorreoBodega()%> </h4>
                </tr>
            </table>
        </div>
    </div>


</div>

    <footer class="page-footer font-small blue" style="margin-top: 60px">
        <div class="footer-copyright text-center py-3">© 2020 Copyright:
            <a href="#"> MiMarca.com</a>
        </div>
    </footer>


</body>
</html>
