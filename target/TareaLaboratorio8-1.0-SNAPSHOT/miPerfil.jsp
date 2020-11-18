<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 17/11/2020
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="usuario" scope="request" type="beans.UsuarioBean" />
<html>
<head>
    <jsp:include page="bootstrapRepository.jsp"/>
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

        .margen {
            margin-top: 2%;
        }

        .container-fluid {
            text-align: center;
            padding: 3% 15%;
        }

        .page-item .page-link {
            color: #343a40;
            border-color: #343a40;
        }

        .page-item.active .page-link {
            border-color: #343a40;
            background-color: #343a40;
        }
        .centrar{
            align-items: center;
            justify-content: center;
        }
        tr{
            height: 70px;
        }
        th{
            height: 70px;
        }

    </style>

    <title>Mi perfil</title>
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
            <a href="#"><img src="imagenes/sigout.png" height="30px"/></a>

        </div>
    </div>
</header>
<div class=" Container">

    <h1 class="mb-3 mt-3">Mi perfil</h1>
    <div class="row">

        <div class="col-lg-6 col-md-12 centrar">
            <img src="https://lh3.googleusercontent.com/proxy/NKPsgN2kY96sNXegV5KjOe9j1afG-Jc4IQh_RWpLCYs_RTVZLYF_jGF6CFVCPuZoQf_btiNsLNQx5RjtBcFo0G3gMBaQRP1HkqyXgYhyKai8yMD_8D2E2IrMNxcsp8TB9M678DdU2Mz-rdOrGu-R" width="408px" height="400px" />
        </div>
        <div class="col-lg-6 col-md-12 centrar">
            <table class="table">
                <tr>
                    <th>Nombres</th>
                    <td><%=usuario.getNombre()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&usuarioId=<%=usuario.getIdUsuario() %>"
                           class="btn btn-success">Editar</a></td>
                </tr>
                <tr>
                    <th>Apellidos</th>
                    <td><%=usuario.getApellido()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&usuarioId=<%=usuario.getIdUsuario() %>"
                           class="btn btn-success">Editar</a></td>
                </tr>
                <tr>
                    <th>DNI</th>
                    <td><%=usuario.getDni()%></td>
                    <td></td>
                </tr>
                <tr>
                    <th>Correo</th>
                    <td><%=usuario.getCorreo()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&usuarioId=<%=usuario.getIdUsuario() %>"
                           class="btn btn-success">Cambiar Contraseña</a></td>
                </tr>
                <tr>
                    <th>Distrito</th>
                    <td><%=usuario.getDistrito().getNombre()%></td>
                    <td><a href="<%=request.getContextPath()%>/UsuarioServlet?accion=editar&usuarioId=<%=usuario.getIdUsuario() %>"
                           class="btn btn-success">Editar</a></td>
                </tr>


            </table>
        </div>
    </div>


</div>

</body>
</html>
