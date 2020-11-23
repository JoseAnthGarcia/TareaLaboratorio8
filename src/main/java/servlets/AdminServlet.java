package servlets;

import beans.BodegaBean;
import beans.DistritoBean;
import daos.AdminDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AdminServlet",urlPatterns = {"/AdminServlet"} )
public class AdminServlet extends HttpServlet {

    public boolean validarRuc(String ruc){
        boolean resultado = true;
        try{
            Long ruc_number = Long.parseLong(ruc);
            //se debe agregar dos ceros porque el ruc tiene 11 digitos
            if(ruc_number<0 || ruc_number>1000000000){
                resultado = false;
            }
        } catch (NumberFormatException e) {
            resultado = false;
        }
        if(ruc.equalsIgnoreCase("")){
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
    public boolean validarCorreo(String input){
        boolean resultado = true;
        if(input.contains("@")){
            System.out.println("contiene arroba");
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = (String) request.getParameter("accion") == null ? "listar":
                (String) request.getParameter("accion");
        AdminDao bodegaDao = new AdminDao();

        String ruc = request.getParameter("ruc");
        String direccion = request.getParameter("direccion");
        String nombreBodega = request.getParameter("nombreBodega");
        String correo = request.getParameter("correo");
        String idDistrito = request.getParameter("idDistrito");

        boolean rucB = validarRuc(ruc);
        boolean direccionB = validarString(direccion);
        boolean nombreBodegaB = validarString(nombreBodega);
        boolean distritoB = validarNumero(String.valueOf(idDistrito));
        boolean correoB = validarCorreo(correo);

        ArrayList<DistritoBean> listaDistritos = bodegaDao.obtenerDistritos();
        request.setAttribute("listaDistritos", listaDistritos);

        switch (accion){
            case  "registrar":

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
        }


    }
}
