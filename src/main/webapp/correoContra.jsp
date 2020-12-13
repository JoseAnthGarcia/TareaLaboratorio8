<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%Long ruc2 = (Long) request.getAttribute("ruc2");%>
<%String nombreBodega = (String) request.getAttribute("nombreBodega");%>
<html>
    <head>
        <title>Correo</title>
        <jsp:include page="/bootstrapRepository.jsp"/>

        <!-- para los iconos como botones -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <style>

            .container-fluid{
                padding: 7% 15% ;
            }
            h1{
                font-size: 4rem;
            }

        </style>
    </head>
    <header>
        <div class="collapse bg-dark" id="navbarHeader">
            <div class="container">

            </div>
        </div>
        <div class="navbar navbar-dark bg-dark box-shadow">
            <div class="container d-flex justify-content-between">
                <a href="#" class="navbar-brand d-flex align-items-center">
                    <strong>Mi Marca.com</strong>
                </a>
            </div>
        </div>
    </header>
    <body>
        <div class="container-fluid">
            <h1>Actualizacion de contraseña</h1>
            <h3>Se ha enviado un correo al asociado al RUC para la actualizacion de la contaseña de la bodega <%=nombreBodega%>
                con RUC <%=ruc2%>.</h3>

        </div>
        <footer class="page-footer font-small blue" style="margin-top: 20px">
            <div class="footer-copyright text-center py-3">© 2020 Copyright:
                <a href="#">MiMarca</a>
            </div>
        </footer>

    </body>
</html>