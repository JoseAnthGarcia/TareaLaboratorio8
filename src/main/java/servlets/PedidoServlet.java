package servlets;

import beans.PedidoBean;
import daos.PedidoDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/PedidosServlet"})
public class PedidoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion") == null?
                "listar":
                request.getParameter("accion");
        PedidoDao pedidoDao = new PedidoDao();
        RequestDispatcher view;
        String codigo = request.getParameter("codigo");
        switch (action){
            case "listar":
                String pag = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");
                int paginaAct = Integer.parseInt(pag);
                int cantPag = pedidoDao.calcularCantPag();
                try{
                    paginaAct = Integer.parseInt(pag);
                    if(paginaAct>cantPag){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<PedidoBean> listaPedidos = pedidoDao.obtenerListaPedidos(paginaAct);
                request.setAttribute("listaPedidos", listaPedidos);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);
                view = request.getRequestDispatcher("listaPedido.jsp");
                view.forward(request,response);
                break;
            case "mostrar":
                listaPedidos = new ArrayList<>();
                PedidoBean pedido = pedidoDao.mostrarPedido(codigo);
                request.setAttribute("listaPedidos", listaPedidos);
                request.setAttribute("pedido", pedido);
                view = request.getRequestDispatcher("mostrarPedido.jsp");
                view.forward(request,response);
                break;
            case "entregar":
                if (pedidoDao.obtenerPedidoBodega(codigo) != null) {
                    pedidoDao.entregarPedido(codigo);
                    HttpSession session1 = request.getSession();
                    session1.setAttribute("estado", "entregado");
                }
                response.sendRedirect(request.getContextPath() + "/PedidosServlet");
                break;
            case "cancelar":
                if (pedidoDao.obtenerPedidoBodega(codigo) != null) {
                    HttpSession session = request.getSession();
                    boolean valCancelar = pedidoDao.verificarCancelarPedido(codigo);
                    session.setAttribute("valCancelar", valCancelar);
                    session.setAttribute("estado", "cancelado");
                    pedidoDao.cancelarPedido(codigo);
                }
                response.sendRedirect(request.getContextPath() + "/PedidosServlet");
                break;
        }
    }
}
