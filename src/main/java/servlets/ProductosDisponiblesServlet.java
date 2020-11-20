package servlets;

import beans.ProductoBodegasBean;
import daos.ProductosBodegas;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

@WebServlet(name = "ClientServlet", urlPatterns = {"/ClientServlet"})
public class ProductosDisponiblesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductosBodegas productosBodegas = new ProductosBodegas();
        String pag = request.getParameter("pag") == null ?
                "1" : request.getParameter("pag");

        int cantPag = productosBodegas.calcularCantPag();

        int paginaAct;
        try{
            paginaAct = Integer.parseInt(pag); //try
            if(paginaAct>cantPag){
                paginaAct = 1;
            }
        }catch(NumberFormatException e){
            paginaAct = 1;
        }



        ArrayList<ProductoBodegasBean> listaProductos = productosBodegas.listarProductoBodegas(paginaAct);

        request.setAttribute("listaProductoBodegas", listaProductos);
        request.setAttribute("cantPag", cantPag);
        request.setAttribute("paginaAct",paginaAct);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("listarProductosBodegas.jsp");
        requestDispatcher.forward(request,response);
    }
}
