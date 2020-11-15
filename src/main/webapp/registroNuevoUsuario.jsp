<%@ page import="beans.DistritoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaDistritos" scope="request" type="java.util.ArrayList<beans.DistritoBean>"/>
<!DOCTYPE html>
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
    </style>

    <title>Registrarse</title>
</head>
<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
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

<div class="container" style="margin-top: 30px">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">
            <h1>Registro</h1>
            <form method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=agregar">
                <div class="form-group row">
                    <label for="inputName" class="col-sm-2 col-form-label">Nombres:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="nombres" id="inputName">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputLastName" class="col-sm-2 col-form-label">Apellidos:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="apellidos" id="inputLastName">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputDni" class="col-sm-2 col-form-label">DNI:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" name="dni" id="inputDni">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputEmail" class="col-sm-2 col-form-label">Correo:</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control" name="correo" id="inputEmail">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="contrasenia" id="inputPassword">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword2" class="col-sm-2 col-form-label">Confirmar contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" name="contrasenia2" id="inputPassword2">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="selectDistrict" class="col-sm-2 col-form-label">Distrito</label>
                    <div class="col-sm-10">
                        <select class="form-control" id="selectDistrict" name="idDistrito">
                            <option selected value="0">Eliga un distrito</option>
                            <%for (DistritoBean distrito : listaDistritos) {%>
                                <option value="<%=distrito.getId()%>"><%=distrito.getNombre()%></option>
                            <% } %>
                        </select>
                    </div>
                </div>
                <button type="submit" class="btn btn-success pull-right">Registrarse</button>
            </form>
        </div>
        <div class="col-sm-3">
        </div>
    </div>
</div>


</div>
<footer class="page-footer font-small blue" style="margin-top: 20px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>
</div>

</body>
</html>