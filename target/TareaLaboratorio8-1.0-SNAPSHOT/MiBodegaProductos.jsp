<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.MiBodegaProductosBean" %>
<%@ page import="daos.MiBodegaProductosDao" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="beans.ProductoBean" %><%--
  Created by IntelliJ IDEA.
  User: Anacleto
  Date: 8/11/2020
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaProductoBodegas" scope="request" type="java.util.ArrayList<beans.ProductoBean>"/>
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>

<html>
<head>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>


    <!-- para los iconos como botones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        .btn {
            background-color: #ffffff;
            border: none;
            color: black;
            padding: 12px 16px;
            font-size: 15px;
            cursor: pointer;
        }
        /* Darker background on mouse-over */
        .btn:hover {
            background-color: #767676;
        }
    </style>

    <title>Productos</title>
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
            <a href="../MiBodega.html" class="navbar-brand d-flex align-items-center">
                <strong>Mi Bodega</strong>
            </a>
            <a href="#" class="navbar-brand d-flex align-items-center">
                <strong>Productos</strong>
            </a>
            <a href="../Pedidos/Pedidos.html" class="navbar-brand d-flex align-items-center">
                <strong>Pedidos</strong>
            </a>
            <a>
                <div class="card"><a href="../login.html" >
                    <a href="#" ><img src="imagenes/sigout.png" height="30px"/></a>
                </a>
                </div>
            </a>

        </div>
    </div>
</header>



<div class="container" style="margin-top: 20px">

    <!-- botones -->
    <div class="row justify-content-end" style="margin-bottom: 20px;">
        <a href="<%=request.getContextPath()%>/BodegaProductos?action=formAdd">
            <button class="btn"><i class="fa fa-plus"></i></button>
        </a>
    </div>

    <form>
        <div class="row">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col">Imagen</th>
                    <th scope="col">NOMBRE</th>
                    <th scope="col">DESCRIPCIÓN</th>
                    <th scope="col">STOCK</th>
                    <th scope="col">PRECIO UNITARIO</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>

                <!-- Inicio del bucle para listar los productos-->
                <%
                    for(ProductoBean producto : listaProductoBodegas){
                %>
                <tr>
                    <td>
                        <img src="https://mui.today/__export/1589466590626/sites/mui/img/2020/05/14/coca-cola-sabor-botella-vidrio.jpg_879108255.jpg" height="35">
                        <!-- todo: reemplazar por la ruta de la foto ingresar
                    <img src="<%=producto.getRutaFoto()%>" height="35"> -->
                    </td>
                    <td><%=producto.getNombreProducto()%></td>
                    <td><%=producto.getDescripcion()%></td>
                    <td><%=producto.getStock()%></td>
                    <td>S/<%=producto.getPrecioProducto()%></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=editar&idProducto=<%=producto.getId()%>">
                            <button class="btn"><i class="fa fa-edit"></i></button>
                        </a>
                    </td>
                    <td>
                        <a href="EditarProducto.html">
                            <button class="btn"><i class="fa fa-trash"></i></button>
                        </a>
                    </td>
                </tr>
                <%
                    }
                %>

                </tbody>
            </table>
        </div>
    </form>


    <!-- paginacion -->
    <div class="row">
        <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
            <ul class="pagination justify-content-center">
                <%if(paginaAct==1){%>
                <li class="page-item disabled">
                    <span class="page-link">Anterior</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?pag=<%=paginaAct-1%>">Anterior</a>
                </li>
                <%}%>

                <% for(int k=1; k<=cantPag; k++){
                    if(k==paginaAct){%>
                <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                </li>
                <%      }else{%>
                <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?pag=<%=k%>"><%=k%></a></li>
                <%      }
                } %>


                <%if(paginaAct==cantPag){%>
                <li class="page-item disabled">
                    <span class="page-link">Siguiente</span>
                </li>
                <%}else{%>
                <li class="page-item">
                    <a class="page-link" href="<%=request.getContextPath()%>/BodegaServlet?pag=<%=paginaAct+1%>">Siguiente</a>
                </li>
                <%}%>

            </ul>
        </nav>
    </div>

</div>


<footer class="page-footer font-small blue" style="margin-top: 60px">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">MiMarca</a>
    </div>
</footer>



</div>


</body>
</html>
