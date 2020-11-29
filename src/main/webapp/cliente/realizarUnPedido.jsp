<%@ page import="beans.ProductoBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaProductos" type="java.util.ArrayList<beans.ProductoBean>" scope="request" />
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
<!DOCTYPE html>

<html>
<head>
    <jsp:include page="/bootstrapRepository.jsp"/>
    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <title>Bienvenido Bodega!</title>

</head>
<body>

<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">

        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Anacleto.com</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Mi Bodega</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Productos</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Pedidos</strong>
            </a>
        </div>
    </div>
</header>

<div class="container" style="margin-top: 65px; margin-left: 210px">

    <div class="row">
        <div class="col-sm-8">
            <h1>Realiza un pedido</h1>
            <h3>Bodega seleccionada: #####</h3>
        </div>
        <div class="col-sm-4">
            <button class="btn btn-secondary" href="#">Cancelar y elegir otra bodega</button>
        </div>

    </div>


    <div class="row">
        <div class="form-group row">
            <div class="col-10">
                <input class="form-control" type="text" placeholder="Buscar producto"
                       name="textoBuscar"/>
            </div>
            <div class="col-2">
                <a class="btn btn-danger"
                   href="<%= request.getContextPath()%>/EmployeeServlet">Limpiar</a>
            </div>
        </div>
    </div>

    <div class="row">
        <%//Listar productos de cierta bodega%>
        <table class="table">
            <thead>
            <tr>
                <th scope="col"></th>
                <th scope="col">Producto</th>
                <th scope="col">Precio Unitario</th>
                <th scope="col">
                </th>
            </tr>
            </thead>
            <tbody>
            <%for(ProductoBean producto: listaProductos){%>
            <tr>
                <td>Imagen x</td>
                <td><%=producto.getNombreProducto()%></td>
                <td><%=producto.getPrecioProducto()%></td>
                <td>
                    <button class="btn btn-secondary" href="#">Seleccionar</button>
                </td>
            </tr>
            <%}%>
            </tbody>
        </table>
    </div>

    <!-- paginacion -->
    <div class="row">

        <a href="#" class="btn btn-outline-danger">Volver</a>

        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/UsuarioServlet?accion=realizarPedido&pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>

        <a href="#" class="btn btn-outline-success">Realizar un pedido</a>

    </div>
</div>

<footer class="page-footer font-small blue" style="margin-top: 20px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>


</body>
</html>