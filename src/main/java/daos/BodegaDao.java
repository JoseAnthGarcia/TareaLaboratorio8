package daos;

import beans.BodegaBean;
import beans.PedidoBean;
import beans.ProductoBean;
import beans.UsuarioBean;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BodegaDao extends BaseDao{

    public static void crearProducto(String nombreProducto, String descripcion, int stock, BigDecimal precioUnitario, int idBodega){
        // TODO: añadir el manejo de imagenes

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: idBodega, nombreFoto, rutaFoto se ha hardcodeado
        String sql = "insert into producto (nombreFoto,rutaFoto,nombreProducto,descripcion,stock,precioUnitario,idBodega) values (\n" +
                "'foto random', '/fotoRandom', ?, ?, ?, ?, ?);";  // numero de paginas

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            // todo: cuando se añada rutaFoto y nombreFoto esto tmb cambiará
            pstmt.setString(1, nombreProducto);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, stock);
            pstmt.setBigDecimal(4, precioUnitario);
            pstmt.setInt(5, idBodega);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static int calcularCantPag(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        // TODO: idBodega se ha hardcodeado
        String sql = "(count(*)/5) from producto where idBodega=1 AND estado='Existente'";  // numero de paginas

        int cantPag = 0;
        try (Connection conn = DriverManager.getConnection(url, "root", "root");
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            rs.next();
            cantPag = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public static int calcularCantPag(String productName, int idBodega) {
        /**
         *  Para barra de busqueda
         */

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "select * from producto where idBodega=? and lower(nombreProducto) like ? and estado='Existente';";  // numero de paginas

        int cantPag = 0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setString(2, "%"+productName+"%");

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    cantPag++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (int) Math.ceil((double) cantPag / 5);
    }


    public static ArrayList<ProductoBean> listarProductoBodega(int pagina){

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        int limit = (pagina-1)*5;
        String sql = "select idProducto, nombreFoto, rutaFoto, nombreProducto,descripcion,stock,precioUnitario from producto WHERE idBodega=1 AND estado='Existente' limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt(1));
                    /*producto.setNombreFoto(rs.getString(2));
                    producto.setRutaFoto(rs.getString(3));*/
                    producto.setNombreProducto(rs.getString(4));
                    producto.setDescripcion(rs.getString(5));
                    producto.setStock(rs.getInt(6));
                    producto.setPrecioProducto(rs.getBigDecimal(7));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    public static ArrayList<ProductoBean> listarProductoBodega(int pagina, String productName, int idBodega){
        /**
         *  Para barra de busqueda
         */

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        int limit = (pagina-1)*5;
        String sql = "select idProducto, nombreFoto, rutaFoto, nombreProducto,descripcion,stock,precioUnitario from producto WHERE idBodega=? AND lower(nombreProducto) like ? AND estado='Existente' limit ?,5;";  // numero de paginas

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setString(2, "%"+productName+"%");
            pstmt.setInt(3, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt(1));
                    /*producto.setNombreFoto(rs.getString(2));
                    producto.setRutaFoto(rs.getString(3));*/
                    producto.setNombreProducto(rs.getString(4));
                    producto.setDescripcion(rs.getString(5));
                    producto.setStock(rs.getInt(6));
                    producto.setPrecioProducto(rs.getBigDecimal(7));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    //devuelve imagen por ID
    public void listarImg(int id, HttpServletResponse response) {

        String sql = "select foto from producto where idProducto=?;";

        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/*"); //que hace esto?? uu

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            outputStream = response.getOutputStream();

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inputStream = rs.getBinaryStream("foto");
                }
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);

            int i = 0;
            while ((i = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(i);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean buscarProducto(int idProducto){

        boolean exisProduct = false;

        String sql = "SELECT * FROM producto WHERE idProducto = ? AND idBodega=1";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idProducto);

            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    exisProduct = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return exisProduct;
    }

    public ProductoBean buscarProducto2(int idProducto){

        ProductoBean producto = null;

        String sql = "SELECT idProducto, nombreProducto, descripcion, stock, precioUnitario FROM producto WHERE idProducto = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idProducto);

            try(ResultSet rs = pstmt.executeQuery();){
                if(rs.next()){
                    producto = new ProductoBean();
                    producto.setId(rs.getInt("idProducto"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setPrecioProducto(rs.getBigDecimal("precioUnitario"));

                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return producto;
    }

    public ArrayList<PedidoBean> buscarPedidoConProducto(int idProducto){

        ArrayList<PedidoBean> listaPedidos = new ArrayList<>();

        String sql = "SELECT pe.codigo FROM producto p\n" +
                "LEFT JOIN pedido_has_producto php ON p.idProducto = php.idProducto\n" +
                "LEFT JOIN pedido pe ON php.idPedido = pe.idPedido\n" +
                "WHERE p.idProducto = ? AND pe.estado = 'Pendiente';";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idProducto);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    PedidoBean pedido = new PedidoBean();
                    pedido.setCodigo(rs.getString(1));
                    listaPedidos.add(pedido);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaPedidos;
    }

    public void eliminarProducto(int idProducto){
        String sql = "UPDATE producto SET estado='Eliminado' WHERE idProducto=?;";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1,idProducto);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //-------------- Para inicio de sesion ------------------

    /*
    public BodegaBean validarUsuarioPassword(String user, String password){

        String sql = "select * from bodega where correo=? and contrasenia=?;";
        BodegaBean bodegaBean = null;
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1,user);
            pstmt.setString(2,password);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    int idBodega = rs.getInt(1);
                    bodegaBean = this.obtenerBodega(idBodega);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodegaBean;
    }*/

    //ENCRIPTAR CONTRASENIA
    public BodegaBean validarUsuarioPasswordHashed(String correo, String contrasenia) {

        BodegaBean bodegaBean = null;

        String sql = "select * from bodega where correo=? and contraseniaHashed=sha2(?,256)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);
            pstmt.setString(2, contrasenia);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    int idBodega = rs.getInt(1);
                    bodegaBean = this.obtenerBodega(idBodega);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return bodegaBean;
    }

    public BodegaBean obtenerBodega(int idBodega){
        BodegaBean bodega = null;
        String sql = "SELECT * FROM bodega WHERE idBodega=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idBodega);
            try(ResultSet rs = pstmt.executeQuery()){
                rs.next();
                bodega = new BodegaBean();
                bodega.setIdBodega(rs.getInt("idBodega"));
                bodega.setNombreBodega(rs.getString("nombreBodega"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bodega;
    }

    //-------------------------------Flujo bodega---------------------------

    public void actualizarProducto( int idProducto, String descripcion, int stock, BigDecimal precioUnitario) {

        String sql = "UPDATE producto SET descripcion = ?, stock = ?, precioUnitario = ? WHERE idProducto = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, descripcion);
            pstmt.setInt(2, stock);
            pstmt.setBigDecimal(3, precioUnitario);
            pstmt.setInt(4, idProducto);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
