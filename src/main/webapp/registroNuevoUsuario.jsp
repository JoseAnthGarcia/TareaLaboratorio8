<%@ page import="beans.DistritoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaDistritos" scope="request" type="java.util.ArrayList<beans.DistritoBean>"/>

<%
    boolean nombresB = request.getAttribute("nombresB") == null ? true : (Boolean) request.getAttribute("nombresB");
    boolean apellidosB = request.getAttribute("apellidosB") == null ? true : (Boolean) request.getAttribute("apellidosB");
    boolean dniB = request.getAttribute("dniB") == null ? true : (Boolean) request.getAttribute("dniB");
    boolean correoB = request.getAttribute("correoB") == null ? true : (Boolean) request.getAttribute("correoB");
    boolean contraseniaB = request.getAttribute("contraseniaB") == null ? true : (Boolean) request.getAttribute("contraseniaB");
    boolean contrasenia2B = request.getAttribute("contrasenia2B") == null ? true : (Boolean) request.getAttribute("contrasenia2B");
    boolean distritoSelected = request.getAttribute("distritoSelected") == null ? true : (Boolean) request.getAttribute("distritoSelected");
    boolean contIguales = request.getAttribute("contIguales") == null ? true : (Boolean) request.getAttribute("contIguales");
    boolean correoExis = request.getAttribute("correoExis") == null ? false : (Boolean) request.getAttribute("correoExis");
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
<div class="modal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Modal body text goes here.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>

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
                        <input type="text" class="form-control <%=nombresB?"":"is-invalid"%>"
                               aria-describedby="inputNameFeedback"
                               name="nombres"
                               id="inputName" <%=nombresB?"value='"+request.getParameter("nombres")+"'":""%>>
                        <div id="inputNameFeedback" class="invalid-feedback">
                            Ingrese datos, por favor.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputLastName" class="col-sm-2 col-form-label">Apellidos:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control <%=apellidosB?"":"is-invalid"%>"
                               aria-describedby="inputLastNameFeedback"
                               name="apellidos"
                               id="inputLastName" <%=nombresB?"value='"+request.getParameter("apellidos")+"'":""%>>
                        <div id="inputLastNameFeedback" class="invalid-feedback">
                            Ingrese datos, por favor.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputDni" class="col-sm-2 col-form-label">DNI:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control <%=dniB?"":"is-invalid"%>"
                               aria-describedby="inputDniFeedback"
                               name="dni" id="inputDni" <%=nombresB?"value='"+request.getParameter("dni")+"'":""%>>
                        <div id="inputDniFeedback" class="invalid-feedback">
                            Ingrese datos, por favor.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputEmail" class="col-sm-2 col-form-label">Correo:</label>
                    <div class="col-sm-10">
                        <input type="email" class="form-control <%=correoB?"":"is-invalid"%>"
                               aria-describedby="inputEmailFeedback"
                               name="correo"
                               id="inputEmail" <%=nombresB?"value='"+request.getParameter("correo")+"'":""%>>
                        <div id="inputEmailFeedback" class="invalid-feedback">
                            Ingrese datos, por favor.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=contraseniaB?"":"is-invalid"%>"
                               aria-describedby="inputPasswordFeedback"
                               name="contrasenia" id="inputPassword">
                        <div id="inputPasswordFeedback" class="invalid-feedback">
                            Ingrese contraseña valida, por favor.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword2" class="col-sm-2 col-form-label">Confirmar contraseña:</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control <%=contrasenia2B?"":"is-invalid"%>"
                               aria-describedby="inputPassword2Feedback"
                               name="contrasenia2" id="inputPassword2">
                        <div id="inputPassword2Feedback" class="invalid-feedback">
                            Ingrese contraseña valida, por favor.
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="selectDistrict" class="col-sm-2 col-form-label">Distrito</label>
                    <div class="col-sm-10">
                        <% if (request.getParameter("idDistrito") == null) {%>
                        <select class="form-control" id="selectDistrict" name="idDistrito">
                        <% }else{ %>
                            <select class="form-control
                            <%=((request.getParameter("idDistrito")).equals("0") || distritoSelected==false)?"is-invalid":""%>"
                            aria-describedby="idDistritoFeedback" id="selectDistrict" name="idDistrito">
                        <% } %>
                            <option selected value="0">Eliga un distrito</option>
                            <%for (DistritoBean distrito : listaDistritos) {%>
                            <option value="<%=distrito.getId()%>"><%=distrito.getNombre()%>
                            </option>
                        <% } %>
                            </select>
                            <div id="idDistritoFeedback" class="invalid-feedback">
                                Seleccione una opcion valida, por favor.
                            </div>
                    </div>


                </div>
                <button type="submit" class="btn btn-success pull-right">Registrarse</button>
            </form>
        </div>
        <div class="col-sm-3">
            <%if(!contIguales){%>
            <div class="alert alert-danger" role="alert">
                Las contraseñas no coinciden!
            </div>
            <%}%>
            <%if(correoExis){%>
            <div class="alert alert-danger" role="alert">
                El correo ingresado existe
            </div>
            <%}%>
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