package daos;

import beans.PedidoBean;
import beans.ProductoBean;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class BodegaDao extends BaseDao{

    public static void crearProducto(String nombreProducto, String descripcion, int stock, BigDecimal precioUnitario){
        // TODO: añadir el manejo de imagenes

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: idBodega, nombreFoto, rutaFoto se ha hardcodeado
        String sql = "insert into producto (nombreFoto,rutaFoto,nombreProducto,descripcion,stock,precioUnitario,idBodega) values (\n" +
                "'foto random', '/fotoRandom', ?, ?, ?, ?, 30);";  // numero de paginas

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            // todo: cuando se añada rutaFoto y nombreFoto esto tmb cambiará
            pstmt.setString(1, nombreProducto);
            pstmt.setString(2, descripcion);
            pstmt.setInt(3, stock);
            pstmt.setBigDecimal(4, precioUnitario);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int calcularCantPag(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        // TODO: idBodega se ha hardcodeado
        String sql = "select ceil(count(*)/5) from producto where idBodega=30 AND estado='Existente'";  // numero de paginas

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

    public static ArrayList<ProductoBean> listarProductoBodega(int pagina){

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        int limit = (pagina-1)*5;
        String sql = "select idProducto, nombreFoto, rutaFoto, nombreProducto,descripcion,stock,precioUnitario from producto WHERE idBodega=30 AND estado='Existente' limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt(1));
                    producto.setNombreFoto(rs.getString(2));
                    producto.setRutaFoto(rs.getString(3));
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

    public boolean buscarProducto(int idProducto){

        boolean exisProduct = false;

        String sql = "SELECT * FROM producto WHERE idProducto = ? AND idBodega=30";

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
