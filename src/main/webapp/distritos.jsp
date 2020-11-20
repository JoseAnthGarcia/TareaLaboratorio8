<%@ page import="beans.distritosB" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="listaBodegas" scope="request" type="java.util.ArrayList<beans.distritosB>"/>
<jsp:useBean id="cantPag" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="paginaAct" scope="request" type="java.lang.Integer"/>
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
                margin-top: 20px;
                margin-left: 20px;
            }
            /* Darker background on mouse-over */
            .btn:hover {
                background-color: #343a40;
            }
            .margen{
                margin-top: 2%;
            }
            .container-fluid{
                text-align: center;
                padding: 3% 15% ;
                margin-top: 20px;
            }
            .page-item .page-link {
                color: #343a40;
                border-color: #343a40;
                margin-top: 20px;
            }
            .page-item.active .page-link {
                border-color: #343a40;
                background-color: #343a40;
                margin-top: 20px;
            }
        </style>

        <title>Lista de Bodegas!</title>
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
                        <strong>MiBodega.com</strong>
                    </a>
                    <a href="#" ><img src="imagenes/sigout.png" height="30px"/></a>
                </div>

            </div>

        </header>
        <a href="#" class="btn btn-secondary" >Realizar un pedido</a>


        <div class="container" style="margin-top: 20px">

            <!-- Presentacion de bodegas -->
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
                    <img src="imagenes/bodega1.png"  class="img-thumbnail">
                    <p class="mb-1"><b>Bodega: </b> <%=listaBodegas.get(j).getNombreBodega()%> </p>
                    <p class="mb-0"><b>Dirección: </b> <%=listaBodegas.get(j).getDireccion()%> </p>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="exampleRadios" id="exampleRadios1" value="option1" checked>
                        <label class="form-check-label" for="exampleRadios1">
                            Escoger
                        </label>
                    </div>
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
                <nav aria-label="Page navigation example" class = "mx-auto"> <!-- Recordar centro !! -->
                    <ul class="pagination justify-content-center">
                        <%if(paginaAct==1){%>
                        <li class="page-item disabled">
                            <span class="page-link">Anterior</span>
                        </li>
                        <%}else{%>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/ListaBodegasServlet?pag=<%=paginaAct-1%>">Anterior</a>
                        </li>
                        <%}%>

                        <% for(int k=1; k<=cantPag; k++){
                            if(k==paginaAct){%>
                        <li class="page-item active">
                          <span class="page-link"><%=k%><span class="sr-only">(current)</span>
                          </span>
                        </li>
                        <%      }else{%>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/ListaBodegasServlet?pag=<%=k%>"><%=k%></a></li>
                        <%      }
                        } %>


                        <%if(paginaAct==cantPag){%>
                        <li class="page-item disabled">
                            <span class="page-link">Siguiente</span>
                        </li>
                        <%}else{%>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/ListaBodegasServlet?pag=<%=paginaAct+1%>">Siguiente</a>
                        </li>
                        <%}%>

                    </ul>
                </nav>

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
