package filters;

import beans.BodegaBean;
import beans.UsuarioBean;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginUsuarioFilter", urlPatterns = {"/LoginServlet", ""})
public class LoginUsuarioFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        UsuarioBean usuario = (UsuarioBean) request.getSession().getAttribute("usuario");
        BodegaBean bodega = (BodegaBean) request.getSession().getAttribute("bodega");
        UsuarioBean admin = (UsuarioBean) request.getSession().getAttribute("admin");

        if(usuario!=null && usuario.getIdUsuario()>0){
            if(request.getParameter("accion").equals("logout")){
                chain.doFilter(req, resp);
            }else{
                response.sendRedirect(request.getContextPath() + "/UsuarioServlet?accion=Home");
            }
        }else if (bodega!=null && bodega.getIdBodega()>0){
            response.sendRedirect(request.getContextPath() + "/BodegaServlet");
        }else if (admin!=null && admin.getIdUsuario()>0){
            response.sendRedirect(request.getContextPath() + "/AdminServlet?accion=miPerfil");
        }else{
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
