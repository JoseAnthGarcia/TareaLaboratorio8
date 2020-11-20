package servlets;

import beans.distritosB;
import daos.ClienteD;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "ListaBodegasServlet",
        urlPatterns = {"/ListaBodegasServlet"}
)
public class distritosS extends HttpServlet {
    public distritosS() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClienteD clientDao = new ClienteD();
        String pag = request.getParameter("pag") == null ? "1" : request.getParameter("pag");
        int paginaAct = Integer.parseInt(pag);
        int cantPag = clientDao.calcularCantPag();
        ArrayList<distritosB> listaBodegas = clientDao.listarBodegas(paginaAct);
        request.setAttribute("listaBodegas", listaBodegas);
        request.setAttribute("cantPag", cantPag);
        request.setAttribute("paginaAct", paginaAct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("distritos.jsp");
        requestDispatcher.forward(request, response);
    }
}
