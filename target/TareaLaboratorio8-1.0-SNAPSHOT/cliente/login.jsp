<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    int correoEnviado = session.getAttribute("correoEnviado") == null ? 0 : (int) session.getAttribute("correoEnviado");
    Boolean cambioContra = session.getAttribute("cambioContra") == null ? false : (Boolean) session.getAttribute("cambioContra");

    //se tiene que hacer por sesion
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Ingreso al sistema</title>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/signin.css" rel="stylesheet">
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
            padding: 15px 15%;
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
        .ingresa{
            width: 323px;
        }
        .izquierda{
            position: relative;
            left: 70px;
        }

    </style>
</head>

<body >

<div class="container-fluid">

    <h1 class="mb-5 mt-3 ">Ingreso al sistema 'Mi Marca'</h1>
    <div class="row mb-3">

        <div class="col-lg-5 col-md-12 centrar">
            <img src="imagenes/profile.png" width="408px" height="400px" />
        </div>
        <div class="col-lg-7 col-md-12 ">
            <form class="form-signin" method="POST" action="<%=request.getContextPath()%>/LoginServlet?accion=login" style="
    margin-left: 150px;text-align: center;position: relative; top:40px;">
            <div class="row">
                <div class="col-10">

                    <input type="text" name="inputEmail" class="form-control ingresa" placeholder="Correo" autofocus="">


                </div>
                <div class="col-6">

                </div>

            </div>
                <div class="row mt-3">
                    <div class="col-10">

                        <input type="password" name="inputPassword" class="form-control ingresa" placeholder="Password">

                    </div>
                    <div class="col-2 izquierda">
                        <a href="<%=request.getContextPath()%>/LoginServlet?accion=olvideContra">
                            <button type="button" class="btn btn-primary">Olvidé mi contraseña</button></a>
                    </div>

                </div>

                    <% if (request.getParameter("error") != null) { %>
                <div class="text-danger mb-2">Error en usuario o contraseña</div>
                    <% } %>

                <div class="row">
                    <div class="col-6">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Ingresar</button>
                    </div>
                    <div class="col-6 " style="text-align: center; position: relative; top: 10px">
                        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=agregar"
                        >Registrarse</a>
                    </div>

                </div>

            </form>
        </div>
    </div>
    <div align="right">
        <%if(correoEnviado==1){%>
        <div class="alert alert-success" role="alert">
            El correo fue enviado exitosamente. Revise su bandeja de entrada.

        </div>
        <%}else if(correoEnviado==2){%>
        <div class="alert alert-danger" role="alert">
            Ha ocurrido un error al enviar el correo. Intentelo de nuevo.
        </div>
        <%}%>
        <%if(cambioContra){%>
        <div class="alert alert-success" role="alert">
            Contraseña restablecida exitosamente.
        </div>
        <%}%>
        <%session.invalidate(); %>
    </div>


</div>


</body>
</html>
