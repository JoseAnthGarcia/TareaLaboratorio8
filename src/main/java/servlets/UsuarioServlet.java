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

    public boolean validarDni(String dni){
        boolean resultado = true;
        try{
            int dni_int = Integer.parseInt(dni);
            if(dni_int<0 || dni_int>100000000){
                resultado = false;
            }
        } catch (NumberFormatException e) {
            resultado = false;
        }
        if(dni.equalsIgnoreCase("")){
            resultado = false;
        }
        return resultado;
    }

    public boolean validarString(String input){
        boolean resultado = true;

        if(input.equalsIgnoreCase("")){
            resultado = false;
        }

        return resultado;
    }

    public boolean validarNumero(String input){
        boolean resultado = true;
        try{
            int valor = Integer.parseInt(input);
        }catch (NumberFormatException e){
            resultado = false;
        }
        return resultado;
    }

    public boolean validarCorreo(String input){
        boolean resultado = true;
        if(input.contains("@")){
            System.out.println("contiene arroba");
        }
        return resultado;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");

        UsuarioDao usuarioDao = new UsuarioDao();
        switch (accion){
            case "nada":
                //manda indice
                break;

            case "agregar":
                String nombres = request.getParameter("nombres");
                String apellidos = request.getParameter("apellidos");
                String dni = request.getParameter("dni");
                String correo = request.getParameter("correo");
                String contrasenia = request.getParameter("contrasenia");
                String contrasenia2 = request.getParameter("contrasenia2");
                String idDistrito = request.getParameter("idDistrito");

                boolean nombresB = validarString(nombres);
                boolean apellidosB = validarString(apellidos);
                boolean dniB = validarDni(dni);
                boolean correoB = validarCorreo(correo);
                boolean contraseniaB = validarString(contrasenia);
                boolean contrasenia2B = validarString(contrasenia2);
                boolean distritoBoolean = validarNumero(idDistrito);

                ArrayList<DistritoBean> listaDistritos = usuarioDao.obtenerDistritos();
                request.setAttribute("listaDistritos", listaDistritos);

                if(nombresB && apellidosB && dniB && correoB && contraseniaB
                        && contrasenia2B && distritoBoolean){

                    int idDistritoInt = Integer.parseInt(idDistrito);

                    boolean distritoSelected = false;
                    if(usuarioDao.buscarDistrito(idDistrito)!= null){
                        distritoSelected = true;
                    }

                    boolean contIguales = false;
                    if(contrasenia.equals(contrasenia2)) {
                        contIguales = true;
                    }

                    boolean correoExis = false;
                    if(usuarioDao.buscarCorreo(correo)){
                        correoExis = true;
                    }

                    if(distritoSelected && contIguales && !correoExis && idDistritoInt != 0){
                        usuarioDao.regitrarNuevoUsuario(nombres, apellidos, dni, correo, contrasenia, idDistritoInt);
                        response.sendRedirect(request.getContextPath()+"/UsuarioServlet");
                    }else{
                        request.setAttribute("contIguales", contIguales);
                        request.setAttribute("correoExis", correoExis);
                        request.setAttribute("distritoSelected", distritoSelected);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("registroNuevoUsuario.jsp");
                        requestDispatcher.forward(request, response);

                    }


                }else{
                    request.setAttribute("nombresB",nombresB);
                    request.setAttribute("apellidosB",apellidosB);
                    request.setAttribute("dniB",dniB);
                    request.setAttribute("correoB",correoB);
                    request.setAttribute("contraseniaB",contraseniaB);
                    request.setAttribute("contrasenia2B",contrasenia2B);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("registroNuevoUsuario.jsp");
                    requestDispatcher.forward(request, response);
                }

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
