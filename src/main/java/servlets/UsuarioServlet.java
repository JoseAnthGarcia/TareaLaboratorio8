package servlets;

import beans.DistritoBean;
import daos.UsuarioDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UsuarioServlet",urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");

        switch (accion){
            case "nada":
                //manda indice
                break;

            case "agregar":
                UsuarioDao usuarioDao = new UsuarioDao();

                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String dni = request.getParameter("dni");
                String correo = request.getParameter("correo");
                String contrasenia = request.getParameter("contrasenia");
                int idDistrito = Integer.parseInt(request.getParameter("idDistrito"));

                usuarioDao.regitrarNuevoUsuario(nombres, apellidos, dni, correo, contrasenia, idDistrito);

                response.sendRedirect(request.getContextPath()+"/UsuarioServlet");

                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");

        switch (accion){
            case "nada":
                //manda indice
                break;

            case "agregar":
                UsuarioDao usuarioDao = new UsuarioDao();
                ArrayList<DistritoBean> listaDistritos = usuarioDao.obtenerDistritos();
                request.setAttribute("listaDistritos", listaDistritos);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("registroNuevoUsuario.jsp");
                requestDispatcher.forward(request, response);
                break;
        }
    }
}
