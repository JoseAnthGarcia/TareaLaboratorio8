package servlets;

import beans.MiBodegaProductosBean;
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

@WebServlet(name = "BodegaProductosServlet", urlPatterns = {"/BodegaProductos"})
public class BodegaProductosServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view;

        String action = request.getParameter("action");
        switch (action) {
            case "addProduct":
                String nombreProducto = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");
                int stock = Integer.parseInt(request.getParameter("stock"));
                BigDecimal precioUnitario = BigDecimal.valueOf(Double.parseDouble(request.getParameter("precioProducto")));
                MiBodegaProductosDao.crearProducto(nombreProducto, descripcion, stock, precioUnitario);

                response.sendRedirect(request.getContextPath()+"/BodegaProductos");
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        int cantPag = MiBodegaProductosDao.calcularCantPag();

        String pag = request.getParameter("pag") == null ?
                "1" : request.getParameter("pag");
        int paginaAct = Integer.parseInt(pag); // se trata de obtener la pagina actual, si es null, se asigna la 1 por defecto
        if (paginaAct>cantPag){
            paginaAct=1;
        }

        MiBodegaProductosDao miBodegaProductosDao = new MiBodegaProductosDao();
        String nombreProducto = request.getParameter("nombreProducto");
        RequestDispatcher view;
        switch (action) {
            case "lista":
                ArrayList<MiBodegaProductosBean> listaProductos = MiBodegaProductosDao.listarProductoBodega(paginaAct); // se lista los productos de la pagina actual

                request.setAttribute("listaProductoBodegas", listaProductos);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);
                view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                view.forward(request,response);
                break;
            case "formAdd":
                view = request.getRequestDispatcher("anadirProducto.jsp");
                view.forward(request,response);
                break;
            case "editar":
                MiBodegaProductosBean producto = miBodegaProductosDao.obtenerProducto(nombreProducto);
                if (producto == null) {
                    response.sendRedirect(request.getContextPath() + "/BodegaProductos");
                } else {
                    request.setAttribute("producto", producto);
                    view = request.getRequestDispatcher("editarProducto.jsp");
                    view.forward(request, response);
                }
                break;
            case "borrar":
                if (miBodegaProductosDao.obtenerProducto(nombreProducto) != null) {
                    miBodegaProductosDao.borrarProducto(nombreProducto);
                }
                response.sendRedirect(request.getContextPath() + "/BodegaProductos");
                break;
        }

    }
}
