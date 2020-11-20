package servlets;

import beans.PedidosBodegaBean;
import daos.PedidoBodegaDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "PedidosBodegaServlet", urlPatterns = {"/PedidosServlet"})
public class PedidosBodegaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion") == null?
                "listar":
                request.getParameter("accion");
        PedidoBodegaDao pedidoBodegaDao = new PedidoBodegaDao();
        RequestDispatcher view;
        String codigo = request.getParameter("codigo");
        switch (action){
            case "listar":
                String pag = request.getParameter("pag") == null ?
                        "1" : request.getParameter("pag");
                int paginaAct = Integer.parseInt(pag);
                int cantPag = pedidoBodegaDao.calcularCantPag();
                try{
                    paginaAct = Integer.parseInt(pag);
                    if(paginaAct>cantPag){
                        paginaAct = 1;
                    }
                }catch(NumberFormatException e){
                    paginaAct = 1;
                }

                ArrayList<PedidosBodegaBean> listaPedidosBodega = pedidoBodegaDao.obtenerListaPedidosBodega(paginaAct);
                request.setAttribute("listaPedidosBodega", listaPedidosBodega);
                request.setAttribute("cantPag", cantPag);
                request.setAttribute("paginaAct",paginaAct);
                view = request.getRequestDispatcher("listaPedidosBodega.jsp");
                view.forward(request,response);
                break;
            case "mostrar":
                listaPedidosBodega = new ArrayList<>();
                PedidosBodegaBean pedidos = pedidoBodegaDao.mostrarPedido(codigo);
                request.setAttribute("listaPedidosBodega", listaPedidosBodega);
                request.setAttribute("pedido", pedidos);
                view = request.getRequestDispatcher("mostrarPedido.jsp");
                view.forward(request,response);
                break;
            case "entregar":
                if (pedidoBodegaDao.obtenerPedidoBodega(codigo) != null) {
                    pedidoBodegaDao.entregarPedido(codigo);
                }
                response.sendRedirect(request.getContextPath() + "/PedidosServlet");
                break;
            case "cancelar":
                if (pedidoBodegaDao.obtenerPedidoBodega(codigo) != null) {
                    pedidoBodegaDao.cancelarPedido(codigo);
                }
                response.sendRedirect(request.getContextPath() + "/PedidosServlet");
                break;
        }
    }
}
