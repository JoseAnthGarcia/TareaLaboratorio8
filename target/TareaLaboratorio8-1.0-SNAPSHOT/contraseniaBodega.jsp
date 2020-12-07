<%@ page import="beans.BodegaBean" %>
<%@ page import="beans.DistritoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="idBodega" scope="request" type="java.lang.Integer"/>

<%
    boolean contraseniaB = request.getAttribute("contraseniaB") == null ? true : (Boolean) request.getAttribute("contraseniaB");
    boolean contrasenia2B = request.getAttribute("contrasenia2B") == null ? true : (Boolean) request.getAttribute("contrasenia2B");
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
                    <a href="#"><img src="/imagenes/sigout.png" height="30px"/></a>

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
                    <h1>Registro de contraseña</h1>
                    <form method="POST" action="<%=request.getContextPath()%>/AdminServlet?accion=definirContrasenia&idBodega=<%=idBodega%>">
                        <div class="form-group row">
                            <% if (Integer.parseInt(idBodega)>0){ %>
                            <label for="inputPassword" class="col-sm-2 col-form-label">Contraseña:</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control <%=contraseniaB?"":"is-invalid"%>"
                                       aria-describedby="inputPasswordFeedback"
                                       name="contrasenia" id="inputPassword" <%=request.getParameter("contrasenia")==null?"":"value='"+request.getParameter("contrasenia")+"'"%>>
                                <div id="inputPasswordFeedback" class="invalid-feedback">
                                    Ingrese una contraseña valida, por favor.
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputPassword2" class="col-sm-2 col-form-label">Confirmar contraseña:</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control <%=contrasenia2B?"":"is-invalid"%>"
                                       aria-describedby="inputPassword2Feedback"
                                       name="contrasenia2" id="inputPassword2" <%=request.getParameter("contrasenia2")==null?"":"value='"+request.getParameter("contrasenia2")+"'"%>>
                                <div id="inputPassword2Feedback" class="invalid-feedback">
                                    Ingrese una contraseña valida, por favor.
                                </div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success pull-right">Registrar</button>
                        <% }  %>
                    </form>
                </div>
                <div class="col-sm-3">
                    <%if(!contIguales){%>
                    <div class="alert alert-danger" role="alert">
                        Las contraseñas no coinciden!
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