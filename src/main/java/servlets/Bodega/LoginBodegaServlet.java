package servlets.Bodega;

import beans.BodegaBean;
import beans.UsuarioBean;
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

@WebServlet(name = "LoginBodegaServlet", urlPatterns ={"/LoginBodega"} )
public class LoginBodegaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputEmail = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");

        BodegaDao bodegaDao = new BodegaDao();
        BodegaBean bodega = bodegaDao.validarUsuarioPasswordHashed(inputEmail, inputPassword);
        //UsuarioDao usuarioDao = new UsuarioDao();
        //UsuarioBean usuario = usuarioDao.validarUsuarioPassword(inputEmail,inputPassword);

        if(bodega!=null){
            HttpSession session = request.getSession();
            session.setAttribute("bodega",bodega);
            response.sendRedirect(request.getContextPath()+"/BodegaServlet?accion=listar"); // TODO: mostrar la pagina HOME
        }else{
            response.sendRedirect(request.getContextPath()+"/LoginBodega?error");  // TODO: manejo de error
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();
        RequestDispatcher view2;

        BodegaBean bodegaBean = (BodegaBean) session.getAttribute("bodega");

        String accion = request.getParameter("accion") == null ?
                "login" : request.getParameter("accion");

        if(bodegaBean==null && accion.equals("login")) {
            // TODO: por que se valida dos veces la misma vain en bodegaBean?

            BodegaBean bodega = (BodegaBean) session.getAttribute("bodega");

            if (bodegaBean != null && bodega.getIdBodega() > 0) {
                response.sendRedirect(request.getContextPath() + "/BodegaServlet");
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("bodega/login.jsp");
            requestDispatcher.forward(request, response);

        }else if(accion.equals("logout")){
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/LoginBodega?accion=login");
        }else{
            view2 = request.getRequestDispatcher("bodega/access_denied.jsp");
            view2.forward(request, response);
        }

    }
}
