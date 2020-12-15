package servlets.Bodega;

import beans.BodegaBean;
import beans.UsuarioBean;
import daos.AdminDao;
import daos.BodegaDao;
import daos.UsuarioDao;
import servlets.Emails;

import javax.mail.MessagingException;
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
        BodegaBean bodega = bodegaDao.validarUsuarioPasswordHashed(inputEmail, inputPassword);
        //UsuarioDao usuarioDao = new UsuarioDao();
        //UsuarioBean usuario = usuarioDao.validarUsuarioPassword(inputEmail,inputPassword);
        AdminDao bodegaD = new AdminDao();

        switch (accion){
            case "actualizarContraCorreo":
                
                String ruc = request.getParameter("ruc");
                boolean rucExis=bodegaD.buscarRuc(ruc);

                if(rucExis){
                    BodegaBean bodega1 = bodegaD.buscarBodega(ruc);
                    boolean bodegaEstado=bodega1.getEstadoBodega().equalsIgnoreCase("activo");
                    
                    String nombreBodega= bodega1.getNombreBodega();
                    Long ruc2= Long.valueOf(ruc);
                    request.setAttribute("ruc2",ruc2);
                    request.setAttribute("nombreBodega", nombreBodega);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("correoContra.jsp");
                    requestDispatcher.forward(request, response);
                    Emails emails = new Emails();
                    String correoAenviar = bodega1.getCorreoBodega();
                    String asunto = "ACTUALIZAR CONTRASEÑA";
                    String contenido = "Se ha solicitado la actualizacion de la contraseña de su bodega "+nombreBodega+" con RUC:"+ruc2+
                            ", para continuar con el registro ingrese al siguiente link y establezca una nueva contraseña:" +
                            "http://localhost:8080/TareaLaboratorio8_war_exploded/LoginBodega?accion=actualizarContra&idBodega="
                            +bodega1.getIdBodega()+"&rucBodega="+ruc2;

                    try {
                        emails.enviarCorreo(correoAenviar, asunto, contenido);
                    } catch (MessagingException e) {
                        System.out.println("Se capturo excepcion en envio de correo");
                    }
                    
                }else{
                    request.setAttribute("rucExis",rucExis);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("actualizarContraBodega.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;
            case "actualizarContra":
                int idBodega2= Integer.parseInt(request.getParameter("idBodega") == null ?
                        "nada" : request.getParameter("idBodega"));
                Long rucBodega2= Long.parseLong((request.getParameter("rucBodega") == null ?
                        "nada" : request.getParameter("rucBodega")));

                request.setAttribute("idBodega",idBodega2);
                request.setAttribute("rucBodega",rucBodega2);
                if(idBodega2==bodegaD.buscarIdBodega(String.valueOf(rucBodega2))) {
                    String contrasenia = request.getParameter("contrasenia");
                    String contrasenia2 = request.getParameter("contrasenia2");

                    boolean contraseniaB = validarContrasenia(contrasenia);
                    boolean contrasenia2B = validarContrasenia(contrasenia2);
                    boolean contIguales = false;
                    if (contrasenia.equals(contrasenia2)) {
                        contIguales = true;
                    }
                    if (contraseniaB && contrasenia2B) {
                        if (contIguales) {
                            bodegaD.registrarContrasenia(idBodega2, contrasenia);
                            bodega = bodegaD.buscarBodega(idBodega2);
                            String nombreBodega = bodega.getNombreBodega();
                            Long ruc3 = bodega.getRucBodega();
                            request.setAttribute("ruc2", ruc3);
                            request.setAttribute("nombreBodega", nombreBodega);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("ContraseniaExitosa.jsp");
                            requestDispatcher.forward(request, response);
                            ;
                        } else {
                            request.setAttribute("contIguales", contIguales);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher("contraseniaBodega.jsp");
                            requestDispatcher.forward(request, response);
                        }
                    } else {
                        request.setAttribute("contraseniaB", contraseniaB);
                        request.setAttribute("contrasenia2B", contrasenia2B);
                        request.setAttribute("contIguales", contIguales);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("contraseniaBodega.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }else{
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("ErrorIdBodega.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;

        }

        if(bodega!=null){
            HttpSession session = request.getSession();
            session.setAttribute("bodega",bodega);
            response.sendRedirect(request.getContextPath()+"/BodegaServlet?accion=home"); // TODO: mostrar la pagina HOME
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

        switch (accion) {
            case "actualizarContraCorreo":
                RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("actualizarContraBodega.jsp");
                requestDispatcher2.forward(request, response);
                break;
            case "actualizarContra":
                try {
                    int idBodega = Integer.parseInt(request.getParameter("idBodega") == null ?
                            "nada" : request.getParameter("idBodega"));
                    request.setAttribute("idBodega", idBodega);
                    String rucBodegaString = request.getParameter("rucBodega") == null ? "nada" : request.getParameter("rucBodega");
                    Long rucBodega = Long.parseLong(request.getParameter("rucBodega") == null ?
                            "nada" : request.getParameter("rucBodega"));

                    request.setAttribute("rucBodega", rucBodega);
                    RequestDispatcher requestDispatcher3 = request.getRequestDispatcher("contraseniaBodega.jsp");
                    requestDispatcher3.forward(request, response);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    RequestDispatcher requestDispatcher4 = request.getRequestDispatcher("ErrorIdBodega.jsp");
                    requestDispatcher4.forward(request, response);
                }
                break;
        }


        if (bodegaBean == null && accion.equals("login")) {
            // TODO: por que se valida dos veces la misma vain en bodegaBean?

            BodegaBean bodega = (BodegaBean) session.getAttribute("bodega");

            if (bodegaBean != null && bodega.getIdBodega() > 0) {
                response.sendRedirect(request.getContextPath() + "/BodegaServlet");
            }
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("bodega/login.jsp");
            requestDispatcher.forward(request, response);

        } else if (accion.equals("logout")) {

            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/LoginBodega?accion=login");
        } else {
            view2 = request.getRequestDispatcher("bodega/access_denied.jsp");
            view2.forward(request, response);
        }

    }
}
