package servlets.Administrador;

import beans.BodegaBean;
import beans.DistritoBean;
import beans.UsuarioBean;
import daos.AdminDao;
import daos.BodegaDao;
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

        int idBodega ;
        Long rucBodega;

        switch (accion){
            case  "registrar":
                boolean rucB = isRUCValid(ruc);
                boolean direccionB = validarString(direccion);
                boolean nombreBodegaB = validarString(nombreBodega);
                boolean distritoB = validarNumero(String.valueOf(idDistrito));
                boolean correoB = validarCorreo(correo);

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
                        rucBodega= Long.valueOf(ruc);
                        request.setAttribute("idBodega",idBodega);
                        request.setAttribute("rucBodega",rucBodega);

                        Emails emails = new Emails();
                        String correoAenviar = correo;
                        String asunto = "REGISTRAR CONTRASEÑA";
                        String contenido = "Se ha iniciado el registro de su bodega "+nombreBodega+" con RUC:"+rucBodega+
                                ", para continuar con el registro ingrese al siguiente link y establezca su contraseña:" +
                                "http://localhost:8082/TareaLaboratorio8_war_exploded/AdminServlet?accion=definirContrasenia&idBodega="
                                +idBodega+"&rucBodega="+rucBodega;

                        try {
                            emails.enviarCorreo(correoAenviar, asunto, contenido);
                        } catch (MessagingException e) {
                            System.out.println("Se capturo excepcion en envio de correo");
                        }
                        request.setAttribute("ruc",ruc);
                        request.setAttribute("nombreBodega",nombreBodega);
                        request.setAttribute("correo",correo);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("BodegaExitosa.jsp");
                        requestDispatcher.forward(request, response);
                    }else{
                        request.setAttribute("rucExis", rucExis);
                        request.setAttribute("distritoSelected", distritoSelected);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/registrarBodega.jsp");
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
                int idBodega2= Integer.parseInt(request.getParameter("idBodega") == null ?
                             "nada" : request.getParameter("idBodega"));
                Long rucBodega2= Long.parseLong((request.getParameter("rucBodega") == null ?
                        "nada" : request.getParameter("rucBodega")));

                request.setAttribute("idBodega",idBodega2);
                request.setAttribute("rucBodega",rucBodega2);
                if(idBodega2==bodegaDao.buscarIdBodega(String.valueOf(rucBodega2))) {
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
                            bodegaDao.registrarContrasenia(idBodega2, contrasenia);
                            BodegaBean bodega = bodegaDao.buscarBodega(idBodega2);
                            nombreBodega = bodega.getNombreBodega();
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UsuarioBean adminActual = (UsuarioBean) session.getAttribute("admin");

        if(adminActual!= null){
            int idAdminActual = adminActual.getIdUsuario();
            String accion = (String) request.getParameter("accion") == null ? "listar":
                    (String) request.getParameter("accion");

            AdminDao bodegaDao = new AdminDao();


            switch (accion){
                case "listar":
                    String pag = request.getParameter("pag") == null ? "1" : request.getParameter("pag");

                    int cantPag = AdminDao.calcularCantPag(idAdminActual);
                    int paginaAct;
                    try{
                        paginaAct = Integer.parseInt(pag); //try
                        if(paginaAct>cantPag){
                            paginaAct = 1;
                        }
                    }catch(NumberFormatException e){
                        paginaAct = 1;
                    }

                    ArrayList<BodegaBean> listaBodegas = bodegaDao.obtenerListaBodegas(paginaAct,idAdminActual);

                    request.setAttribute("lista", listaBodegas);
                    request.setAttribute("cantPag", cantPag);
                    request.setAttribute("paginaAct",paginaAct);


                    RequestDispatcher view = request.getRequestDispatcher("administrador/listarBodegasAdmin.jsp");
                    view.forward(request,response);
                    break;
                case "registrar":
                    ArrayList<DistritoBean> listaDistritos = bodegaDao.obtenerDistritos();
                    request.setAttribute("listaDistritos", listaDistritos);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("administrador/registrarBodega.jsp");
                    requestDispatcher.forward(request,response);
                    break;
                case "bloquear":
                    String nombreBodega = request.getParameter("nombreB");
                    boolean estado = Boolean.parseBoolean(request.getParameter("state"));
                    AdminDao.actualizarEstadoBodega(nombreBodega,estado);
                    response.sendRedirect("AdminServlet");
                    break;
                case "definirContrasenia":

                    try{
                        int idBodega= Integer.parseInt(request.getParameter("idBodega") == null ?
                                "nada" : request.getParameter("idBodega"));
                        request.setAttribute("idBodega",idBodega);
                        String rucBodegaString = request.getParameter("rucBodega")==null?"nada":request.getParameter("rucBodega");
                        Long rucBodega= Long.parseLong(request.getParameter("rucBodega") == null ?
                                "nada" : request.getParameter("rucBodega"));

                        request.setAttribute("rucBodega",rucBodega);
                        RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("contraseniaBodega.jsp");
                        requestDispatcher2.forward(request,response);
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                        RequestDispatcher requestDispatcher2 = request.getRequestDispatcher("ErrorIdBodega.jsp");
                        requestDispatcher2.forward(request,response);
                    }
                    break;
            }


        }else{
            RequestDispatcher view2;
            view2 = request.getRequestDispatcher("administrador/access_denied.jsp");
            view2.forward(request, response);
        }

    }
}
