<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="usuario" scope="session" type="beans.UsuarioBean" class="beans.UsuarioBean" />


<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Mi Marca</title>
    <style>
        body{
            background-color: #f4f4f2;
        }
        .container-fluid{
            padding: 7% 15% 7% 15%;
        }
        .btn-perfil {
            background-color: #343a40;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
            align-items: center;
            border-radius: 1em;
        }

        /* Darker background on mouse-over */
        .btn-perfil:hover {
            background-color: #767676;
        }
        .btn {
            background-color: #393e46;
            border: none;
            color: white;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
            align-items: center;
        }

        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
        .opcion{
            text-align: center;
            width: 179px;
            line-height: 2;



        }
        .productos{

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
        <div class="col-lg-8 col-md-12" >
            <img src="https://img.freepik.com/vector-gratis/concepto-tienda-supermercado-gente-supermercado_40816-713.jpg?size=626&ext=jpg">
        </div>
        <div class="col-lg-4 col-md-12" >
            <div class="row">
                <div class="col-lg-4 col-md-12" style="text-align: center">
                    <img src="imagenes/profile.png" width="100px" height=100px">
                </div>
                <div class="col-lg-8 col-md-12" style="text-align: center">
                    <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=miPerfil"
                    ><button class="btn-perfil"><%=usuario.getNombre()+" "+usuario.getApellido()%></button></a>
                </div>
            </div>



        </div>

    </div>
    <div class="row mt-5">
        <div class="col-lg-4 col-md-12 opcion mt-5" >
            <a class="opcion" href="<%=request.getContextPath()%>/UsuarioServlet?"
            ><button class="btn opcion">Productos disponibles
                <br>
            <img src="imagenes/dairy-products.png" width="50px" height="50px">
            </button></a>
        </div>
        <div class="col-lg-4 col-md-12 opcion mt-5" >
            <a class="opcion" href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido"
            ><button class="btn opcion">Realizar un pedido
                <br>
                <img src="imagenes/buy-button.png" width="50px" height="50px">
            </button></a>
        </div>
        <div class="col-lg-4 col-md-12 opcion mt-5" >
            <a class="opcion" href="<%=request.getContextPath()%>/UsuarioServlet?accion=listar"
            ><button class="btn opcion">Mis pedidos
                <br>
                <img src="imagenes/buy.png" width="50px" height="50px">
            </button></a>
        </div>

    </div>
</div>

</body>
</html>
