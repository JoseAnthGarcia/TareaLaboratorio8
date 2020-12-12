<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 18/11/2020
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@ page import="beans.DistritoBean" --%>
<jsp:useBean id="busuario" scope="request" type="beans.UsuarioBean"/>


<%
    //boolean contrasenia = request.getAttribute("contrasenia") == null ? true : (Boolean) request.getAttribute("contrasenia");
    boolean contrasenia2A = request.getAttribute("contrasenia2A") == null ? true : (Boolean) request.getAttribute("contrasenia2A");
    boolean contraseniaB = request.getAttribute("contraseniaB") == null ? true : (Boolean) request.getAttribute("contraseniaB");
    boolean contrasenia2B = request.getAttribute("contrasenia2B") == null ? true : (Boolean) request.getAttribute("contrasenia2B");
    boolean contAntIguales = request.getAttribute("contAntIguales") == null ? true : (Boolean) request.getAttribute("contAntIguales");
    boolean contIguales = request.getAttribute("contIguales") == null ? true : (Boolean) request.getAttribute("contIguales");
%>
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
            margin-top: 5%;
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
    <title>Cambio Contraseña</title>
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
<!-- Uso de modal?-->
<!-- Uso de modal?-->
<div class='container' style="margin-top: 30px">
    <div class="row">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-6">
            <h1 class="margen">Editar su contraseña</h1>
            <form class="margen"  method="POST" action="<%=request.getContextPath()%>/UsuarioServlet?accion=validarContra">
                <!-- corte de piezas-->
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Contraseña Actual:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=contrasenia2A?"":"is-invalid"%>"
                               aria-describedby="inputPasswordFeedback"
                               name="contrasenia2A" id="inputPassword" <%=request.getParameter("contrasenia2A")==null?"":"value='"+request.getParameter("contrasenia2A")+"'"%> >
                        <div id="inputPasswordFeedback" class="invalid-feedback">
                            Ingrese una contraseña válida
                        </div>
                    </div>
                </div>
                <p class="margen"></p>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Nueva Contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=contraseniaB?"":"is-invalid"%>"
                               aria-describedby="inputPasswordBFeedback"
                               name="contraseniaB" id="inputPassword1" <%=request.getParameter("contraseniaB")==null?"":"value='"+request.getParameter("contraseniaB")+"'"%> >
                        <div id="inputPasswordBFeedback" class="invalid-feedback">
                            Ingrese una contraseña válida
                        </div>
                    </div>
                </div>
                <p class="margen"></p>
                <div class="form-group row">
                    <label for="inputPassword2" class="col-sm-2 col-form-label">Confirmar contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=contrasenia2B?"":"is-invalid"%>"
                               aria-describedby="inputPassword2BFeedback"
                               name="contrasenia2B" id="inputPassword2" <%=request.getParameter("contrasenia2B")==null?"":"value='"+request.getParameter("contrasenia2B")+"'"%> >
                        <div id="inputPassword2BFeedback" class="invalid-feedback">
                            Ingrese una contraseña válida
                        </div>
                    </div>
                </div>
                <p class="margen"></p>
                <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=miPerfil" class="btn btn-danger mt-3">Regresar</a>
                <button type="submit" class="btn btn-success pull-right mt-3">Confirmar</button>
            </form>
        </div>
        <div class="col-sm-3">
            <%if(!contAntIguales){%>
            <div class="alert alert-danger" role="alert">
                Las contraseña actual es incorrecta!
            </div>
            <%}%>
            <%if(!contIguales){%>
            <div class="alert alert-danger" role="alert">
                Los campos de la  nueva contraseña no coinciden!
            </div>
            <%}%>
        </div>

    </div>
</div>

</div>
<footer class="page-footer font-small blue" style="margin-top: 100px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>
</div>

</body>

</html>