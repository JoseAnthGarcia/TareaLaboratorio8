package servlets;

import beans.MiBodegaProductosBean;
import beans.PedidoBean;
import beans.ProductoBean;
import daos.BodegaDao;
import daos.MiBodegaProductosDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@WebServlet(name = "BodegaServlet", urlPatterns = {"/BodegaServlet"})
public class BodegaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher view;

        String accion = request.getParameter("accion") == null ? "listar" : request.getParameter("accion");
        System.out.println("--"+accion+"--");
        switch (accion) {
            case "guardar":

                boolean validStock = true;
                boolean validPrecioUnitario = true;

                String nombreProducto = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");
                int stock = 0;
                BigDecimal precioUnitario = BigDecimal.valueOf(0);

                // se valida el stock
                try{
                    stock = Integer.parseInt(request.getParameter("stock"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    validStock = false;
                }

                // se valida el precioUnitario
                try{
                    precioUnitario = BigDecimal.valueOf(Double.parseDouble(request.getParameter("precioProducto")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    validPrecioUnitario = false;
                }

                // si es que los datos son correctos, se guarda el producto
                if (validStock & validPrecioUnitario) {
                    BodegaDao.crearProducto(nombreProducto, descripcion, stock, precioUnitario);
                    response.sendRedirect(request.getContextPath()+"/BodegaServlet");

                } else {
                    request.setAttribute("validStock",validStock);
                    request.setAttribute("validPrecioUnitario",validPrecioUnitario);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("anadirProducto.jsp");
                    dispatcher.forward(request,response);
                }


        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") == null ?
                "listar" : request.getParameter("accion");

        System.out.println("--"+accion+"--");

        BodegaDao bodegaDao = new BodegaDao();

        int cantPag = bodegaDao.calcularCantPag();

        String pag = request.getParameter("pag") == null ?
                "1" : request.getParameter("pag");
        int paginaAct = Integer.parseInt(pag); // se trata de obtener la pagina actual, si es null, se asigna la 1 por defecto
        if (paginaAct>cantPag){
            paginaAct=1;
        }

        RequestDispatcher view;
        ArrayList<ProductoBean> listaProductos = bodegaDao.listarProductoBodega(paginaAct); // se lista los productos de la pagina actual

        request.setAttribute("listaProductoBodegas", listaProductos);
        request.setAttribute("cantPag", cantPag);
        request.setAttribute("paginaAct",paginaAct);

        switch (accion){
            case "listar":
                view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                view.forward(request,response);
                break;

            case "agregar":
                view = request.getRequestDispatcher("anadirProducto.jsp");
                view.forward(request,response);
                break;
            case "editar":
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                ProductoBean producto = bodegaDao.buscarProducto2(idProducto);
                if(producto == null){
                    response.sendRedirect(request.getContextPath()+"/BodegaServlet");
                }else{
                    request.setAttribute("producto", producto);
                    view = request.getRequestDispatcher("editarProducto.jsp");
                    view.forward(request,response);
                }
                break;
            case "eliminar":
                String idProductoString = request.getParameter("idProducto");
                boolean idProductoNumber = true;

                int idProductoInt = -1;
                try{
                    idProductoInt = Integer.parseInt(idProductoString);
                }catch (NumberFormatException e){
                    idProductoNumber = false;
                }

                if(idProductoNumber){
                    if(bodegaDao.buscarProducto(idProductoInt)){

                        //busco pedidos existentes:
                        ArrayList<PedidoBean> listaPedidos = bodegaDao.buscarPedidoConProducto(idProductoInt);
                        if(listaPedidos.size()==0){ //si no existe pedidos:
                            bodegaDao.eliminarProducto(idProductoInt);
                            response.sendRedirect(request.getContextPath()+"/BodegaServlet");
                        }else{
                            request.setAttribute("pedidosConProducto", listaPedidos);
                            view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                            view.forward(request,response);
                        }

                    }else{
                        response.sendRedirect(request.getContextPath()+"/BodegaServlet");
                    }
                }else{
                    response.sendRedirect(request.getContextPath()+"/BodegaServlet");
                }


                break;
        }

    }
}
