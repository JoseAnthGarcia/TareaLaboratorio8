package servlets;

import main.java.beans.BodegasAdminBean;
import daos.BodegasAdminDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AdminServlet",urlPatterns = {"/AdminServlet"} )
public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BodegasAdminDao bodegaDao = new BodegasAdminDao();

        String accion = request.getParameter("accion") == null ?
                "listar" :
                request.getParameter("accion");

        switch(accion){
            case "listar":
                String pag = request.getParameter("pag") == null ? "1" : request.getParameter("pag");

                int cantPag = BodegasAdminDao.calcularCantPag();
                int paginaAct;
                try{
                    paginaAct = Integer.parseInt(pag); //try
                    if(paginaAct>cantPag){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<BodegasAdminBean> listaBodegas = bodegaDao.obtenerListaBodegas(paginaAct);

                request.setAttribute("lista", listaBodegas);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);


                RequestDispatcher view = request.getRequestDispatcher("listarBodegasAdmin.jsp");
                view.forward(request,response);
                break;
            case "bloquear":
                String nombreBodega = request.getParameter("nombreB");
                boolean estado = Boolean.parseBoolean(request.getParameter("state"));
                BodegasAdminDao.actualizarEstadoBodega(nombreBodega,estado);
                response.sendRedirect("AdminServlet");
                break;
        }


    }
}
