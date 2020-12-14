<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String ruc = (String) request.getAttribute("ruc");%>
<%String nombreBodega = (String) request.getAttribute("nombreBodega");%>
<%String correo = (String) request.getAttribute("correo");%>
<html>
    <head>
        <title>Correo de confirmación</title>
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

    <jsp:include page="/administrador/headerAdmin.jsp"/>

    <body>
        <div class="container-fluid">
            <h1>Registro exitoso</h1>
            <h3>Se ha iniciado el proceso de registro  de la bodega <%=nombreBodega%> con ruc
                <%=ruc%>, revise su correo  <%=correo%> para establecer su contraseña y culminar con el registro .</h3>

        </div>
        <footer class="page-footer font-small blue" style="margin-top: 20px">
            <div class="footer-copyright text-center py-3">© 2020 Copyright:
                <a href="#">MiMarca</a>
            </div>
        </footer>

    </body>
</html>