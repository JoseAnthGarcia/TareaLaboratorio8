<%--
  Created by IntelliJ IDEA.
  User: Katherine
  Date: 29/11/2020
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String correo = (String) request.getAttribute("correo");%>
<%String nombres = (String) request.getAttribute("nombres");%>
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
<body>
<div class="container-fluid">
    <h1>Registro exitoso</h1>
    <h3><%=nombres%>, se ha enviado un mensaje de confirmación al correo '<%=correo%>'. Por favor, verifica tu cuenta.</h3>

</div>
<footer class="page-footer font-small blue" style="margin-top: 20px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>

</body>
</html>
