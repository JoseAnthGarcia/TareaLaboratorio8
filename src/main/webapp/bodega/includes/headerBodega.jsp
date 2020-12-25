<div class="navbar navbar-dark bg-dark box-shadow">
    <div class="container d-flex justify-content-between">
        <a class="navbar-brand d-flex align-items-center">
            <strong>MiMarca.com</strong>
        </a>
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=home" class="navbar-brand d-flex align-items-center">
            <strong>Mi Bodega</strong>
        </a>
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listar" class="navbar-brand d-flex align-items-center">
            <strong>Productos</strong>
        </a>
        <a href="<%=request.getContextPath()%>/BodegaServlet?accion=listarPedidos" class="navbar-brand d-flex align-items-center">
            <strong>Pedidos</strong>
        </a>
        <a href="<%=request.getContextPath()%>/LoginBodega?accion=logout" ><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9XQYb7eVu1VyTTjGNd69RWqaIge0precdjw&usqp=CAU.png" height="30px"/></a>
    </div>
</div>