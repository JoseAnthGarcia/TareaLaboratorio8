<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="codigo" scope="request" type="java.lang.String"/>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Mi Marca</title>
    <style>
        .izquierda1{
            left: 20px;
        }
        .izquierda2{
            left: 15px;
        }
        .izq{
            position: absolute;
            left: 15% ;
        }
        .der{
            position: absolute;
            right: 10% ;
        }
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
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="navbar-brand d-flex align-items-center">
                <strong>MiMarca.com</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Productos disponibles</strong>
            </a>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido" class="navbar-brand d-flex align-items-center">
                <strong>Realizar un pedido</strong>
            </a>
            <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar" class="navbar-brand d-flex align-items-center">
                <strong>Mis pedidos</strong>
            </a>
            <a>
                <div class="card">
                    <a href="<%=request.getContextPath()%>/LoginServlet?accion=logout"><img src="imagenes/sigout.png" height="30px"/></a>
                </div>
            </a>

        </div>
    </div>
</header>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">
            <h1>¡Pedido con codigo <%=codigo%> realizado exitosamente!</h1>
            <h4>Fecha límite de cancelacion: ######</h4>
            <a class="btn btn-outline-success der" href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home">OK</a>
        </div>
        <div class="col-sm-3"></div>
    </div>
</div>

</body>
</html>
