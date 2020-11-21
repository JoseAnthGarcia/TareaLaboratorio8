package servlets;

import beans.MiBodegaProductosBean;
import beans.PedidoBean;
import beans.ProductoBean;
import daos.BodegaDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "BodegaServlet", urlPatterns = {"/BodegaServlet"})
public class BodegaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");

        System.out.println(accion);

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
            case "nada":
                view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                view.forward(request,response);

            case "agregar":
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
