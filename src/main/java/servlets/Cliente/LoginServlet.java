package servlets.Cliente;



import beans.UsuarioBean;
import daos.UsuarioDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns ={"/LoginServlet"} )
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputEmail = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioBean usuario = usuarioDao.validarUsuarioPassword(inputEmail,inputPassword);

        if(usuario!=null){
            HttpSession session =request.getSession();
            session.setAttribute("usuario",usuario);

            response.sendRedirect(request.getContextPath()+"/UsuarioServlet?accion=Home");
        }else{
            response.sendRedirect(request.getContextPath()+"/LoginServlet?error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        RequestDispatcher view2;
        UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("usuario");
        String accion = request.getParameter("accion") == null ?
                "login" : request.getParameter("accion");

        switch(accion) {
            case "olvideContra":
                RequestDispatcher requestDispatcher0 = request.getRequestDispatcher("cliente/olvideContrasenia.jsp");
                requestDispatcher0.forward(request,response);
                break;
            default:
                if(usuarioBean==null && accion.equals("login")) {
                    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuario");
                    if (usuario != null && usuario.getIdUsuario() > 0) {
                        response.sendRedirect(request.getContextPath() + "/UsuarioServlet");
                    }
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/login.jsp");
                    requestDispatcher.forward(request, response);

                }else if(accion.equals("logout")){
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/LoginServlet?accion=login");
                }else{
                    view2 = request.getRequestDispatcher("cliente/access_denied.jsp");
                    view2.forward(request, response);
                }
        }


    }
}
