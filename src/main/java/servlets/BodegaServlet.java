package servlets;

import beans.BodegaBean;
import beans.PedidoBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import daos.BodegaDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

@WebServlet(name = "BodegaServlet", urlPatterns = {"/BodegaServlet"})
public class BodegaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher view;

        String accion = request.getParameter("accion") == null ? "listar" : request.getParameter("accion");
        System.out.println("--" + accion + "--");
        switch (accion) {
            case "guardar":

                boolean validStock = true;
                boolean validPrecioUnitario = true;
                boolean validNombreProducto = true;

                String nombreProducto = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");
                int stock = 0;
                BigDecimal precioUnitario = BigDecimal.valueOf(0);

                // se valida ue el nombre no esté vacio o lleno de espacios
                if (nombreProducto.trim().isEmpty()){
                    validNombreProducto = false;
                }

                // se valida el stock
                try {
                    stock = Integer.parseInt(request.getParameter("stock"));

                    // valida stock negativo
                    if (stock < 0){
                        validStock = false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    validStock = false;
                }

                // se valida el precioUnitario
                try {
                    precioUnitario = BigDecimal.valueOf(Double.parseDouble(request.getParameter("precioProducto")));

                    // valida stock negativo
                    if (precioUnitario.compareTo(BigDecimal.valueOf(0)) == -1){
                        validPrecioUnitario = false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    validPrecioUnitario = false;
                }

                // si es que los datos son correctos, se guarda el producto
                if (validStock & validPrecioUnitario & validNombreProducto) {
                    BodegaDao.crearProducto(nombreProducto, descripcion, stock, precioUnitario);

                } else {
                    request.setAttribute("validStock", validStock);
                    request.setAttribute("validPrecioUnitario", validPrecioUnitario);
                    request.setAttribute("validNombreProducto", validNombreProducto);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("anadirProducto.jsp");
                    dispatcher.forward(request, response);
                }

            case "actualizar":

                BodegaDao bodegaDao = new BodegaDao();

                //validar idProductor
                boolean idProductoNumber = true;
                int idProductoInt = -1;
                try {
                    idProductoInt = Integer.parseInt(request.getParameter("idProducto"));
                } catch (NumberFormatException e) {
                    idProductoNumber = false;
                }

                //validar descripcion
                boolean valdespcr2 = true;
                String descripcion2 = request.getParameter("descripcion");
                if(descripcion2.equals("")){
                    valdespcr2=false;
                }

                // se valida el stock
                boolean validStock2 = true;
                int stock2 = -1;
                try {
                    stock2 = Integer.parseInt(request.getParameter("stock"));
                } catch (NumberFormatException e) {
                    validStock2 = false;
                }

                // se valida el precioUnitario
                boolean validPrecioUnitario2 = true;
                BigDecimal precioUnitario2 = BigDecimal.valueOf(-1);
                try {
                    precioUnitario2 = BigDecimal.valueOf(Double.parseDouble(request.getParameter("precioProducto")));
                } catch (NumberFormatException e) {
                    validPrecioUnitario2 = false;
                }

                if(idProductoNumber && bodegaDao.buscarProducto2(idProductoInt)!=null){
                    if (validStock2 && validPrecioUnitario2 && valdespcr2) {
                        if(bodegaDao.buscarProducto2(idProductoInt)!=null){
                            //actualiza
                            bodegaDao.actualizarProducto(idProductoInt, descripcion2, stock2, precioUnitario2);
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                        }else{
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                        }
                    } else {
                        request.setAttribute("producto", bodegaDao.buscarProducto2(idProductoInt));
                        request.setAttribute("validDescr", valdespcr2);
                        request.setAttribute("validStock", validStock2);
                        request.setAttribute("validPrecioUnitario", validPrecioUnitario2);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("editarProducto.jsp");
                        dispatcher.forward(request, response);
                    }
                }else{
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                }


                break;

            case "buscar":
                // para la barra de busqueda
                /**
                 * NOTA: Al hacer la primera busqueda simepre se vera en la primera pagina
                 */

                String textoBuscar = request.getParameter("textoBuscar");

                int cantPag = BodegaDao.calcularCantPag(textoBuscar);
                int paginaAct = 1; // primera vista en la pagina 1


                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct", paginaAct);
                request.setAttribute("productoBusqueda", textoBuscar);

                request.setAttribute("listaProductoBodegas", BodegaDao.listarProductoBodega(paginaAct,textoBuscar)
                        );
                view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                view.forward(request, response);
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        BodegaBean bodegaActual = (BodegaBean) session.getAttribute("bodega");

        String accion = request.getParameter("accion") == null ?
                "listar" : request.getParameter("accion");

        // Por defecto se deja en un string vacio, que mostraría todos los productos
        String productoBusqueda = "";
        if (request.getParameter("productoBusqueda") != null && !request.getParameter("productoBusqueda").isEmpty()) {
            productoBusqueda = request.getParameter("productoBusqueda");
        }

        BodegaDao bodegaDao = new BodegaDao();

        int cantPag = bodegaDao.calcularCantPag(productoBusqueda);

        String pag = request.getParameter("pag") == null ?
                "1" : request.getParameter("pag");

        int paginaAct;
        try {
            paginaAct = Integer.parseInt(pag); //try
            if (paginaAct > cantPag) {
                paginaAct = 1;
            }
        } catch (NumberFormatException e) {
            paginaAct = 1;
        }

        RequestDispatcher view;
        ArrayList<ProductoBean> listaProductos = bodegaDao.listarProductoBodega(paginaAct, productoBusqueda); // se lista los productos de la pagina actual

        request.setAttribute("listaProductoBodegas", listaProductos);
        request.setAttribute("cantPag", cantPag);
        request.setAttribute("paginaAct", paginaAct);
        request.setAttribute("productoBusqueda", productoBusqueda);

        switch (accion) {
            case "listar":
                view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                view.forward(request, response);
                break;

            case "agregar":
                view = request.getRequestDispatcher("anadirProducto.jsp");
                view.forward(request, response);
                break;
            case "editar":

                boolean idProductoNumber = true;

                int idProductoInt = -1;

                try {
                    idProductoInt = Integer.parseInt(request.getParameter("idProducto"));
                } catch (NumberFormatException e) {
                    idProductoNumber = false;
                }

                if (idProductoNumber) {
                    ProductoBean producto = bodegaDao.buscarProducto2(idProductoInt);
                    if (producto == null) {
                        response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                    } else {
                        request.setAttribute("producto", producto);
                        view = request.getRequestDispatcher("editarProducto.jsp");
                        view.forward(request, response);
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                }

                break;
            case "eliminar":
                String idProductoString = request.getParameter("idProducto");
                boolean idProductoNumber2 = true;

                int idProductoInt2 = -1;
                try {
                    idProductoInt2 = Integer.parseInt(idProductoString);
                } catch (NumberFormatException e) {
                    idProductoNumber = false;
                }

                if (idProductoNumber2) {
                    if (bodegaDao.buscarProducto(idProductoInt2)) {

                        //busco pedidos existentes:
                        ArrayList<PedidoBean> listaPedidos = bodegaDao.buscarPedidoConProducto(idProductoInt2);
                        if (listaPedidos.size() == 0) { //si no existe pedidos:
                            bodegaDao.eliminarProducto(idProductoInt2);
                            response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                        } else {
                            request.setAttribute("pedidosConProducto", listaPedidos);
                            view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                            view.forward(request, response);
                        }

                    } else {
                        response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath() + "/BodegaServlet");
                }


                break;
        }

    }
}
