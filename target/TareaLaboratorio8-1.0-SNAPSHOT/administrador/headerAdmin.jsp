<div class="navbar navbar-dark bg-dark box-shadow">
    <div class="container d-flex justify-content-between">

        <a href="#" class="navbar-brand d-flex align-items-center">
            <strong>Administración</strong>
        </a>
        <a href="<%=request.getContextPath()%>/AdminServlet?accion=registrar" class="navbar-brand d-flex align-items-center">
            <strong>Registrar bodega</strong>
        </a>
        <a href="<%=request.getContextPath()%>/AdminServlet?accion=listar" class="navbar-brand d-flex align-items-center">
            <strong>Lista de bodegas</strong>
        </a>
        <a href="<%=request.getContextPath()%>/LoginAdmin?accion=logout" ><img src="imagenes/sigout.png" height="30px"/></a>
    </div>
</div>