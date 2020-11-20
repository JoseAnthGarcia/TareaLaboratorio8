package servlets;

import beans.MiBodegaProductosBean;
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

        BodegaDao bodegaDao = new BodegaDao();

        int cantPag = bodegaDao.calcularCantPag();

        String pag = request.getParameter("pag") == null ?
                "1" : request.getParameter("pag");
        int paginaAct = Integer.parseInt(pag); // se trata de obtener la pagina actual, si es null, se asigna la 1 por defecto
        if (paginaAct>cantPag){
            paginaAct=1;
        }

        RequestDispatcher view;

        switch (accion){
            case "nada":
                ArrayList<ProductoBean> listaProductos = bodegaDao.listarProductoBodega(paginaAct); // se lista los productos de la pagina actual

                request.setAttribute("listaProductoBodegas", listaProductos);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);
                view = request.getRequestDispatcher("MiBodegaProductos.jsp");
                view.forward(request,response);

            case "agregar":
                break;
            case "editar":
                int idProducto = Integer.parseInt(request.getParameter("idProducto")); //Validar idProducto
                view = request.getRequestDispatcher("MiBodegaProductos.jsp"); //Cambiar al jsp de editar producto
                view.forward(request,response);
                break;
            case "elimimar":
                break;
        }

    }
}
