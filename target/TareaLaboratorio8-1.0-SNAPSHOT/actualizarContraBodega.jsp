<%@ page import="beans.BodegaBean" %>
<%@ page import="beans.DistritoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    boolean contraseniaB = request.getAttribute("contraseniaB") == null ? true : (Boolean) request.getAttribute("contraseniaB");
    boolean rucExis = request.getAttribute("rucExis") == null ? false : (Boolean) request.getAttribute("rucExis");
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

        <title>Definir contraseña</title>
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
                    <h1>Actualizar contraseña</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/LoginBodega?accion=actualizarContra">
                        <div class="form-group row">
                            <label for="inputRuc" class="col-sm-2 col-form-label">RUC:</label>
                            <div class="col-sm-10">
                                <input type="input" class="form-control <%=!rucExis?"":"is-invalid"%>"
                                       aria-describedby="inputRucFeedback"
                                       name="ruc" id="inputRuc" <%=request.getParameter("ruc")==null?"":"value='"+request.getParameter("ruc")+"'"%>>
                                <div id="inputRucFeedback" class="invalid-feedback">
                                    El ruc no esta registrado.
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputPassword" class="col-sm-2 col-form-label">Nueva Contraseña:</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control <%=contraseniaB?"":"is-invalid"%>"
                                       aria-describedby="inputPasswordFeedback"
                                       name="contrasenia" id="inputPassword" <%=request.getParameter("contrasenia")==null?"":"value='"+request.getParameter("contrasenia")+"'"%>>
                                <div id="inputPasswordFeedback" class="invalid-feedback">
                                    Ingrese una contraseña valida, por favor.
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success pull-right">Registrar</button>
                    </form>
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