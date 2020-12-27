package servlets.Administrador;

import beans.BodegaBean;
import beans.UsuarioBean;
import daos.AdminDao;
import daos.BodegaDao;
import daos.UsuarioDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginAdminServlet", urlPatterns = {"/LoginAdmin"})
public class LoginAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String inputEmail = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");

        AdminDao adminDao = new AdminDao();
        UsuarioBean admin = adminDao.validarUsuarioPassword(inputEmail, inputPassword);

        if(admin!=null){
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
            response.sendRedirect(request.getContextPath()+"/AdminServlet?accion=miPerfil");
        }else{
            response.sendRedirect(request.getContextPath()+"/LoginAdmin?error");  // TODO: manejo de error
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        RequestDispatcher view2;

        UsuarioBean admin = (UsuarioBean) session.getAttribute("admin");

        String accion = request.getParameter("accion") == null ?
                "login" : request.getParameter("accion");

        if(admin==null && accion.equals("login")) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/login.jsp");
            requestDispatcher.forward(request, response);

        }else if(accion.equals("logout")) {
            session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/LoginAdmin?accion=login");
        }else if(admin!=null && accion.equals("login")) {
            response.sendRedirect(request.getContextPath() + "/AdminServlet?accion=miPerfil");
        }else{
            view2 = request.getRequestDispatcher("administrador/access_denied.jsp");
            view2.forward(request, response);
        }

    }
}
