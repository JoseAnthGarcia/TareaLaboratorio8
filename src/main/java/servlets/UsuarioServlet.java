package servlets;
import beans.DistritoBean;
import beans.UsuarioBean;
import daos.UsuarioDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        boolean resultado2= true;
        if(input.equalsIgnoreCase("")){
            resultado = false;
        }
        try{
            int numero= Integer.parseInt(input);
            resultado2=false;
        }catch (NumberFormatException e){
            resultado2=true;
        }
        boolean resultadoFinal= resultado&&resultado2;


        return resultadoFinal;
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

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // El email a validar
        String email = input;

        Matcher mather = pattern.matcher(email);

        if (mather.find() == false) {
            resultado = false;
        }

        return resultado;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");

        UsuarioDao usuarioDao = new UsuarioDao();
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String idDistrito = request.getParameter("idDistrito");

        System.out.println(nombres);
        System.out.println(apellidos);
        System.out.println(idDistrito);

        boolean nombresB = validarString(nombres);
        boolean apellidosB = validarString(apellidos);
        boolean distritoBoolean = validarNumero(idDistrito);

        ArrayList<DistritoBean> listaDistritos = usuarioDao.obtenerDistritos();
        request.setAttribute("listaDistritos", listaDistritos);
        /*Para editar*/
        int usuarioId =26;
        UsuarioBean bUsuario = usuarioDao.obtenerUsuario(usuarioId);
        request.setAttribute("usuario", bUsuario);
        ArrayList<DistritoBean> listaDistritos2 = usuarioDao.obtenerDistritos();
        request.setAttribute("listaDistritos2", listaDistritos2);

        switch (accion){
            case "nada":
                //manda indice
                break;

            case "agregar":
                String dni = request.getParameter("dni");
                String correo = request.getParameter("correo");
                String contrasenia = request.getParameter("contrasenia");
                String contrasenia2 = request.getParameter("contrasenia2");
                boolean dniB = validarDni(dni);
                boolean correoB = validarCorreo(correo);
                boolean contraseniaB = validarString(contrasenia);
                boolean contrasenia2B = validarString(contrasenia2);

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

            case "actualizar":
                String cambiar = request.getParameter("cambiar") == null ?
                        "nada" : request.getParameter("cambiar");
                request.setAttribute("cambiar",cambiar);

                if(nombresB && apellidosB  && distritoBoolean){

                    int idDistritoInt = Integer.parseInt(idDistrito);

                    boolean distritoSelected = false;
                    if(usuarioDao.buscarDistrito(idDistrito)!= null){
                        distritoSelected = true;
                    }

                    if(distritoSelected && idDistritoInt != 0){
                        usuarioDao.actualizarUsuario(nombres, apellidos, idDistritoInt,usuarioId);
                        response.sendRedirect(request.getContextPath()+"/UsuarioServlet?accion=miPerfil");
                    }else{

                        request.setAttribute("distritoSelected", distritoSelected);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("editarUsuario.jsp");
                        requestDispatcher.forward(request, response);

                    }


                }else{
                    request.setAttribute("nombresB",nombresB);
                    request.setAttribute("apellidosB",apellidosB);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("editarUsuario.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;

            case "validarContra":
                //obtener usuarioID , o antes ya lo tengo para usar
                //String contraseniaA = request.getParameter("contrasenia");
                String contraseniaA = bUsuario.getContrasenia();
                String contrasenia2A = request.getParameter("contrasenia2A");
                String contraseniaBB = request.getParameter("contraseniaB");
                String contrasenia2BB = request.getParameter("contrasenia2B");


                boolean contAntIguales=false;
                if(contraseniaA.equals(contrasenia2A)){
                    contAntIguales=true;
                }

                boolean contIguales= false;
                if(contraseniaBB.equals(contrasenia2BB)){
                    contIguales= true;
                }

                if(contAntIguales && contIguales){
                    usuarioDao.actualizarContra(usuarioId, contraseniaBB); //ojo con usuarioId
                    response.sendRedirect(request.getContextPath()+"/UsuarioServlet?accion=miPerfil");
                }else{
                    UsuarioBean usuarioDopel = usuarioDao.obtenerUsuario(usuarioId);
                    //los hiddens no funcionan sin un busuario nuevo.....u.u
                    request.setAttribute("busuario",usuarioDopel);
                    request.setAttribute("contraseniaB",!contraseniaBB.equals(""));
                    request.setAttribute("contrasenia2B",!contrasenia2BB.equals(""));
                    request.setAttribute("contrasenia2A",!contrasenia2A.equals(""));
                    request.setAttribute("contIguales",contIguales);
                    request.setAttribute("contAntIguales",contAntIguales);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("cambioContrasenia.jsp");
                    requestDispatcher.forward(request,response);
                }
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");
        UsuarioDao usuarioDao = new UsuarioDao();
        int usuarioId=26;

        switch (accion) {
            case "nada":
                //manda indice
                break;

            case "agregar":

                ArrayList<DistritoBean> listaDistritos = usuarioDao.obtenerDistritos();
                request.setAttribute("listaDistritos", listaDistritos);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("registroNuevoUsuario.jsp");
                requestDispatcher.forward(request, response);
                break;
            case "miPerfil":


                UsuarioBean usuario =usuarioDao.obtenerUsuario(usuarioId);

                request.setAttribute("usuario",usuario);

                requestDispatcher = request.getRequestDispatcher("miPerfil.jsp");
                requestDispatcher.forward(request, response);
                break;
            case "editar":
                String cambiar= request.getParameter("cambiar") == null ?
                        "nada" : request.getParameter("cambiar");
                ArrayList<DistritoBean> listaDistritos2 = usuarioDao.obtenerDistritos();
                request.setAttribute("listaDistritos2", listaDistritos2);

                UsuarioBean bUsuario = usuarioDao.obtenerUsuario(usuarioId);

                    request.setAttribute("cambiar",cambiar);
                    request.setAttribute("usuario", bUsuario);
                    requestDispatcher = request.getRequestDispatcher("editarUsuario.jsp");
                    requestDispatcher.forward(request, response);

                break;

            case "cambioContra":
                //int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                UsuarioBean busuario = usuarioDao.obtenerUsuario(usuarioId);

                //request.setAttribute("contrasenia",busuario.getContrasenia()); //puede reducirse mas adelante
                request.setAttribute("busuario",busuario);
                requestDispatcher = request.getRequestDispatcher("cambioContrasenia.jsp");
                requestDispatcher.forward(request,response);
                break;

        }
    }
}
