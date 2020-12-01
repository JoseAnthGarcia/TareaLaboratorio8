package servlets;

import beans.BodegaBean;
import beans.DistritoBean;
import daos.AdminDao;

import javax.mail.MessagingException;
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

@WebServlet(name = "AdminServlet",urlPatterns = {"/AdminServlet"} )
public class AdminServlet extends HttpServlet {

    public static boolean isRUCValid(long ruc){
        return isRUCValid(String.valueOf(ruc));
    }

    public static boolean isRUCValid(String ruc){
        if (ruc == null) {
            return false;
        }

        final int[] multipliers = {5,4,3,2,7,6,5,4,3,2};
        final String[] prefixes = getRucPrefixes();
        final int length = multipliers.length + 1;

        if(ruc.length() != length){
            return false;
        }

        boolean isPrefixOk = false;

        for (String prefix : prefixes){
            if(ruc.substring(0,2).equals(prefix)){
                isPrefixOk = true;
                break;
            }
        }

        if(!isPrefixOk){
            return false;
        }

        int sum = 0;

        for(int i = 0; i < multipliers.length; i++){
            final char section = ruc.charAt(i);

            if(!Character.isDigit(section)){
                return false;
            }

            sum += Character.getNumericValue(ruc.charAt(i)) * multipliers[i];
        }

        final int rest = sum % length;
        final String response = String.valueOf(length - rest);

        return response.charAt(response.length() - 1) == ruc.charAt(ruc.length() - 1);
    }

    public static String[] getRucPrefixes(){
        return new String[]{"10", "15", "17", "20"};
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

    public boolean validarNumero(String input){
        boolean resultado = true;
        try{
            int valor = Integer.parseInt(input);
        }catch (NumberFormatException e){
            resultado = false;
        }
        return resultado;
    }

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

        String accion = (String) request.getParameter("accion") == null ? "listar":
                (String) request.getParameter("accion");
        AdminDao bodegaDao = new AdminDao();

        String ruc = request.getParameter("ruc");
        String direccion = request.getParameter("direccion");
        String nombreBodega = request.getParameter("nombreBodega");
        String correo = request.getParameter("correo");
        String idDistrito = request.getParameter("idDistrito");

        ArrayList<DistritoBean> listaDistritos = bodegaDao.obtenerDistritos();
        request.setAttribute("listaDistritos", listaDistritos);

        switch (accion){
            case  "registrar":
                boolean rucB = isRUCValid(ruc);
                boolean direccionB = validarString(direccion);
                boolean nombreBodegaB = validarString(nombreBodega);
                boolean distritoB = validarNumero(String.valueOf(idDistrito));
                boolean correoB = validarCorreo(correo);
                int idBodega = 0;
                if(rucB && direccionB && nombreBodegaB && correoB && distritoB){

                    int idDistritoInt = Integer.parseInt(idDistrito);

                    boolean distritoSelected = false;
                    if(bodegaDao.buscarDistrito(idDistrito)!= null){
                        distritoSelected = true;
                    }
                    boolean rucExis = false;
                    if(bodegaDao.buscarRuc(ruc)){
                        rucExis = true;
                    }

                    if(distritoSelected && !rucExis && idDistritoInt != 0){
                        bodegaDao.guardarBodega(ruc,direccion,nombreBodega,correo,idDistritoInt);
                        idBodega = bodegaDao.buscarIdBodega(ruc);
                        Emails emails = new Emails();
                        String correoAenviar = "garcia.josea@pucp.edu.pe";
                        String asunto = "REGISTRAR CONTRASEÑA";
                        String contenido = "Se ha iniciado el registro de su bodega, para continuar con el " +
                                "registro ingrese al siguiente link y establezca una contraseña:" +
                                request.getContextPath()+"/AdminServlet?accion=definirContrasenia?idBodega="+idBodega;
                        try {
                            emails.enviarCorreo(correoAenviar, asunto, contenido);
                        } catch (MessagingException e) {
                            System.out.println("Se capturo excepcion en envio de correo");
                        }

                        response.sendRedirect(request.getContextPath()+"/AdminServlet");
                    }else{
                        request.setAttribute("rucExis", rucExis);
                        request.setAttribute("distritoSelected", distritoSelected);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrarBodega.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }else{
                    request.setAttribute("rucB",rucB);
                    request.setAttribute("direccionB",direccionB);
                    request.setAttribute("nombreBodegaB",nombreBodegaB);
                    request.setAttribute("correoB",correoB);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrarBodega.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;
            case "definirContrasenia":
                String idBodega2= request.getParameter("idBodega") == null ?
                        "nada" : request.getParameter("idBodega");

                String contrasenia = request.getParameter("contrasenia");
                String contrasenia2 = request.getParameter("contrasenia2");

                boolean contraseniaB = validarContrasenia(contrasenia);
                boolean contrasenia2B = validarContrasenia(contrasenia2);
                boolean contIguales = false;
                if(contrasenia.equals(contrasenia2)) {
                    contIguales = true;
                }
                if(contraseniaB && contrasenia2B ){
                    if(contIguales){
                        bodegaDao.registrarContrasenia(Integer.parseInt(idBodega2),contrasenia);
                        response.sendRedirect(request.getContextPath()+"/AdminServlet");
                    }else{
                        request.setAttribute("contIguales", contIguales);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("contraseniaBodega.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }else{
                    request.setAttribute("contraseniaB",contraseniaB);
                    request.setAttribute("contrasenia2B",contrasenia2B);
                    request.setAttribute("contIguales",contIguales);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("contraseniaBodega.jsp");
                    requestDispatcher.forward(request, response);
                }
                break;

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = (String) request.getParameter("accion") == null ? "listar":
                (String) request.getParameter("accion");

        AdminDao bodegaDao = new AdminDao();


        switch (accion){
            case "listar":
                String pag = request.getParameter("pag") == null ? "1" : request.getParameter("pag");

                int cantPag = AdminDao.calcularCantPag();
                int paginaAct;
                try{
                    paginaAct = Integer.parseInt(pag); //try
                    if(paginaAct>cantPag){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<BodegaBean> listaBodegas = bodegaDao.obtenerListaBodegas(paginaAct);

                request.setAttribute("lista", listaBodegas);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);


                RequestDispatcher view = request.getRequestDispatcher("listarBodegasAdmin.jsp");
                view.forward(request,response);
                break;
            case "registrar":
                ArrayList<DistritoBean> listaDistritos = bodegaDao.obtenerDistritos();
                request.setAttribute("listaDistritos", listaDistritos);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("registrarBodega.jsp");
                requestDispatcher.forward(request,response);
                break;
            case "bloquear":
                String nombreBodega = request.getParameter("nombreB");
                boolean estado = Boolean.parseBoolean(request.getParameter("state"));
                AdminDao.actualizarEstadoBodega(nombreBodega,estado);
                response.sendRedirect("AdminServlet");
                break;
            case "definirContrasenia":
                String idBodega= request.getParameter("idBodega") == null ?
                        "nada" : request.getParameter("idBodega");
                request.setAttribute("idBodega",idBodega);
                RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("contraseniaBodega.jsp");
                requestDispatcher2.forward(request,response);
                break;
        }


    }
}
