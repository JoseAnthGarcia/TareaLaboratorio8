package servlets.Bodega;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "LoginBodegaServlet", urlPatterns ={"/LoginBodega"} )
public class LoginBodegaServlet extends HttpServlet {

    public  boolean validarContrasenia(String contrasenia) {
        boolean resultado = true;
        Pattern pattern2 = Pattern
                .compile("(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}");
        Matcher mather = pattern2.matcher(contrasenia);

        if (mather.find() == false) {
            resultado = false;
        }
        return  resultado;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputEmail = request.getParameter("inputEmail");
        String inputPassword = request.getParameter("inputPassword");

        String accion = request.getParameter("accion") == null ?
                "login" : request.getParameter("accion");

        BodegaDao bodegaDao = new BodegaDao();
        BodegaBean bodega = bodegaDao.validarUsuarioPassword(inputEmail, inputPassword);
        //UsuarioDao usuarioDao = new UsuarioDao();
        //UsuarioBean usuario = usuarioDao.validarUsuarioPassword(inputEmail,inputPassword);


        switch (accion){
            case "actualizarContra":
                AdminDao bodegaD = new AdminDao();
                String ruc = request.getParameter("ruc");
                String contrasenia = request.getParameter("contrasenia");
                boolean contraseniaB=validarContrasenia(contrasenia);
                boolean rucExis=bodegaD.buscarRuc(ruc);

                if(rucExis){

                    BodegaBean bodega1 = bodegaD.buscarBodega(ruc);
                    boolean bodegaEstado=bodega1.getEstadoBodega().equalsIgnoreCase("activo");
                    if (contraseniaB ) {
                        bodegaD.registrarContrasenia(bodega1.getIdBodega(), contrasenia);
                        String nombreBodega= bodega1.getNombreBodega();
                        Long ruc2= Long.valueOf(ruc);
                        request.setAttribute("ruc2",ruc2);
                        request.setAttribute("nombreBodega", nombreBodega);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("ContraseniaExitosa.jsp");
                        requestDispatcher.forward(request, response);

                    } else {
                        request.setAttribute("contraseniaB", contraseniaB);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("actualizarContraBodega.jsp");
                        requestDispatcher.forward(request, response);
                    }

                }else{
                    request.setAttribute("rucExis",rucExis);
                    request.setAttribute("contraseniaB",contraseniaB);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("actualizarContraBodega.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;

        }

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

        switch (accion){
            case "actualizarContra":
                RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("actualizarContraBodega.jsp");
                requestDispatcher2.forward(request,response);
                break;

        }


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
