<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaBodegas" type="java.util.ArrayList<beans.BodegaBean>" scope="request"/>
<jsp:useBean id="paginaAct" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="cantPag" type="java.lang.Integer" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
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
        .margen{
            margin-top: 2%;
        }
        .container-fluid{
            text-align: center;
            padding: 3% 15% ;
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

    <title>Bodegas disponibles</title>
</head>
<body>

<!-- todo:  corregir el espaciado entre Mi Bodega, Pedidos y Productos -->
<header>
    <jsp:include page="/cliente/includes/headerClient.jsp"/>
</header>
<div class="container" style="margin-top: 20px">
    <div class="row">
        <div class="col-sm-6">
            <h1>Estas todas las bodegas disponibles:</h1>
        </div>
        <%if(request.getSession().getAttribute("noBodegaEscogida")!=null){%>
        <div class="col-sm-6">
            <div class="alert alert-danger" role="alert">
                Seleccione alguna bodega.
            </div>
        </div>
        <%request.getSession().removeAttribute("noBodegaEscogida");
        }%>
    </div>
</div>


<div class="container" style="margin-top: 30px">
    <!-- Presentacion de productos -->
    <form method="post" action="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega">
    <% int cant = 0;
        for(int i=0; i<2; i++){
    %>
    <div class="row">
        <% int min = i*4;
            int max = (i+1)*4;
            for(int j=min; j<max; j++){
                if(cant < listaBodegas.size()){
        %>
        <div class="col-sm-3"> <!-- Probar medidas "sm-3"? -->
            <div class="form-check">
                <input class="form-check-input" type="radio" name="idBodega" id="exampleRadios1" value="<%=listaBodegas.get(j).getIdBodega()%>">
                <label class="form-check-label" for="exampleRadios1">
                    Seleccionar
                </label>
            </div>
            <img src="https://lh3.googleusercontent.com/proxy/p0p83ybh7bHr451_Eb8-kibqGOZXzq7ZUYI94U9qhCTxSxn1dG5SO6KvJZ2CwLDjcKBw36sX4EZ0X8b9NgWPQyHOfIu6O4-E" width="150" class="img-thumbnail">
            <p class="mb-1"><b>Bodega: </b> <%=listaBodegas.get(j).getNombreBodega()%> </p>
            <p class="mb-0"><b>direccion: </b> <%=listaBodegas.get(j).getDireccionBodega()%> </p>
        </div>
        <% } else{ %>
        <div class="col-sm-3"> <!-- Probar medidas "sm-3"? -->
        </div>
        <%}%>
        <% cant++;
        } %>
    </div>
    <% } %>


    <!-- paginacion -->
    <div class="row">

        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=Home" class="btn btn-outline-danger">Volver a inicio</a>
        <a href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega1" class="btn btn-outline-danger">Escoger bodega cercana</a>

        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega2&pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega2&pag=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=escogerBodega2&pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>

        <button type="submit" class="btn btn-outline-success">Escoger bodega</button>

    </div>
    </form>


</div>

<footer class="page-footer font-small blue" style="margin-top: 20px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>



</div>


</body>
</html>