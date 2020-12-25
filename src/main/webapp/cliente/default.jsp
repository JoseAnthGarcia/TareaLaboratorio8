<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
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
    <title>Página no encontrada</title>
</head>
<body>
<div class="jumbotron jumbotron-fluid">
    <div class="container">
        <h1 class="display-4">Página no encontrada</h1>
        <p class="lead">La página que usted esta buscando no existe.</p>
    </div>
</div>
<footer class="page-footer font-small blue" style="margin-top: 20%">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="<%=request.getContextPath()%>/LoginServlet">MiMarca</a>
    </div>
</footer>

</body>

</body>
</html>