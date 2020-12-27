<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%Long ruc2 = (Long) request.getAttribute("ruc2");%>
<%String nombreBodega = (String) request.getAttribute("nombreBodega");%>
<html>
    <head>
        <title>Correo de confirmación</title>
        <jsp:include page="/bootstrapRepository.jsp"/>
        <jsp:include page="/includes/utf8Cod.jsp"/>

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
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Registro exitoso</h1>
            <p class="lead">Se ha registrado exitosamente la contaseña de la bodega <%=nombreBodega%>
                con RUC <%=ruc2%>.</p>
        </div>
    </div>


        <div class="row">
            <a href="<%=request.getContextPath()%>/Loginbodega" class="btn btn-outline-success primero" style="position: absolute; right: 15%">Ok</a>
        </div>
    </body>
</html>