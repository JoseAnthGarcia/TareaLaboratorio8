package servlets.Cliente;

import beans.DistritoBean;
import beans.PedidoBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import daos.PedidosUsuarioDao;
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

        String nombres;
        String apellidos;
        String idDistrito;

        boolean nombresB;
        boolean apellidosB;
        boolean distritoBoolean;



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
                nombres = request.getParameter("nombres");
                apellidos = request.getParameter("apellidos");
                idDistrito = request.getParameter("idDistrito");

                nombresB = validarString(nombres);
                apellidosB = validarString(apellidos);
                distritoBoolean = validarNumero(idDistrito);

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
                        //FALTA ENVIAR CORREO
                        //TODO: ENVIO DE CORREO FUCIONAL !!!
                        //TODO: cambien los valores de correoAenviar, asunto,contenido !!!
                        Emails emails = new Emails();
                        String correoAenviar = correo;
                        String asunto = "BIENVENIDO A *MI MARCA* !!!!";
                        String contenido = "Hola "+nombres+", te has registrado exitosamente en 'MI MARCA'.Para " +
                                "poder empezar a realizar pedidos, ingresa al link : http://localhost:8050/TareaLaboratorio8_war_exploded/UsuarioServlet";

                        try {
                            emails.enviarCorreo(correoAenviar, asunto, contenido);
                        } catch (MessagingException e) {
                            System.out.println("Se capturo excepcion en envio de correo");
                        }

                        request.setAttribute("correo",correo);
                        request.setAttribute("nombres",nombres);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("cliente/envioCorreo.jsp");
                        requestDispatcher.forward(request, response);
                        //response.sendRedirect(request.getContextPath()+"/UsuarioServlet");
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
                nombres = request.getParameter("nombres");
                apellidos = request.getParameter("apellidos");
                idDistrito = request.getParameter("idDistrito");

                nombresB = validarString(nombres);
                apellidosB = validarString(apellidos);
                distritoBoolean = validarNumero(idDistrito);

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
            case "buscar":
                int idBodega=30;

                String textoBuscar = request.getParameter("textoBuscar");

                request.setAttribute("listaProductos", usuarioDao.buscarProducto(idBodega,textoBuscar));
                RequestDispatcher view = request.getRequestDispatcher("cliente/realizarUnPedido.jsp");
                view.forward(request, response);
                break;

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion") == null ?
                "nada" : request.getParameter("accion");
        UsuarioDao usuarioDao = new UsuarioDao();
        int usuarioId=26;

        HttpSession session = request.getSession();
        ArrayList<ProductoBean> carrito = new ArrayList<>();
        session.setAttribute("carrito", carrito);

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
            case "realizarPedido":
                int idBodega=30; //Se debe jalar la bodega
                //VERIFICAR IDBODEGA?????------------------------------
                HttpSession session1 = request.getSession();
                session1.setAttribute("bodegaEscogida", usuarioDao.obtenerBodega(idBodega));
                int cantPorPagina=4;
                //calculamos paginas:
                String query = "SELECT * FROM producto WHERE idBodega="+idBodega+";";
                int cantPag = usuarioDao.calcularCantPagQuery(query, cantPorPagina);

                String pag = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");
                int paginaAct;
                try{
                    paginaAct = Integer.parseInt(pag); //try
                    if(paginaAct>cantPag){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<ProductoBean> listaProductos = usuarioDao.listarProductosBodega(idBodega, paginaAct, cantPorPagina);

                request.setAttribute("listaProductos",listaProductos);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);

                requestDispatcher = request.getRequestDispatcher("/cliente/realizarUnPedido.jsp");
                requestDispatcher.forward(request, response);
                break;

            case "agregarCarrito":
                //TODO: VALIDACION IDPRODUCTO
                int idProducto = Integer.parseInt(request.getParameter("productSelect"));
                HttpSession session2 = request.getSession();
                ((ArrayList<ProductoBean>) session2.getAttribute("carrito")).add(usuarioDao.obtenerProducto(idProducto));

                //-----------------------
                int idBodega2=30; //Se debe jalar la bodega
                //VERIFICAR IDBODEGA?????------------------------------
                HttpSession session3 = request.getSession();
                session3.setAttribute("bodegaEscogida", usuarioDao.obtenerBodega(idBodega2));
                int cantPorPagina2=4;
                //calculamos paginas:
                String query2 = "SELECT * FROM producto WHERE idBodega="+idBodega2+";";
                int cantPag2 = usuarioDao.calcularCantPagQuery(query2, cantPorPagina2);

                String pag2 = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");
                int paginaAct2;
                try{
                    paginaAct = Integer.parseInt(pag2); //try
                    if(paginaAct>cantPag2){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<ProductoBean> listaProductos2 = usuarioDao.listarProductosBodega(idBodega2, paginaAct, cantPorPagina2);

                request.setAttribute("listaProductos",listaProductos2);
                request.setAttribute("cantPag", cantPag2);
                request.setAttribute("paginaAct",paginaAct);

                requestDispatcher = request.getRequestDispatcher("/cliente/realizarUnPedido.jsp");
                requestDispatcher.forward(request, response);

                break;
            case "verCarrito":
                requestDispatcher = request.getRequestDispatcher("/cliente/carrito.jsp");
                requestDispatcher.forward(request, response);
                break;
            case "listar":

                PedidosUsuarioDao pedidosUsuarioDao = new PedidosUsuarioDao();

                pag = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");


                cantPag = pedidosUsuarioDao.calcularCantPag();
                try{

                    paginaAct = Integer.parseInt(pag); //try
                    if(paginaAct>cantPag){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<PedidoBean> listaPedidos = pedidosUsuarioDao.listarPedidosCliente(paginaAct);
                request.setAttribute("listaPedidos", listaPedidos);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);

                RequestDispatcher view = request.getRequestDispatcher("/cliente/listarPedidosUsuario.jsp");
                view.forward(request,response);
                break;
            case "cancelar":
                String idPedido = request.getParameter("idPedido");
                PedidosUsuarioDao pedidosUsuarioDao1 = new PedidosUsuarioDao();
                pedidosUsuarioDao1.cancelarPedido(Integer.parseInt(idPedido));
                response.sendRedirect(request.getContextPath()+"/UsuarioServlet?accion=listar");
                break;
        }
    }
}
