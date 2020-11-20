package daos;

// A futuro:
// TODO: nombre base de datos ->
// TODO: se ha hardcodeado el id de la bodega

import beans.MiBodegaProductosBean;
import beans.ProductoBodegasBean;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class MiBodegaProductosDao extends BaseDao {

    public static int calcularCantPag(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: idBodega se ha hardcodeado
        String sql = "select ceil(count(*)/5) from producto where idBodega=1";  // numero de paginas

        int cantPag = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            rs.next();
            cantPag = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public static ArrayList<MiBodegaProductosBean> listarProductoBodega(int pagina){

        ArrayList<MiBodegaProductosBean> listaProductos = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        int limit = (pagina-1)*5;
        String sql = "select nombreFoto, rutaFoto, nombreProducto,descripcion,stock,precioUnitario from producto WHERE idBodega = 1 limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    MiBodegaProductosBean producto = new MiBodegaProductosBean();
                    producto.setNombreFoto(rs.getString(1));
                    producto.setRutaFoto(rs.getString(2));
                    producto.setNombreProducto(rs.getString(3));
                    producto.setDescripcion(rs.getString(4));
                    producto.setStock(rs.getInt(5));
                    producto.setPrecioProducto(rs.getBigDecimal(6));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }

    public MiBodegaProductosBean obtenerProducto(String nombreProducto) {

        MiBodegaProductosBean miBodegaProductosBean = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM mydb.producto WHERE nombreProducto = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, nombreProducto);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        miBodegaProductosBean = new MiBodegaProductosBean();
                        miBodegaProductosBean.setNombreFoto(rs.getString(1));
                        miBodegaProductosBean.setRutaFoto(rs.getString(2));
                        miBodegaProductosBean.setNombreProducto(rs.getString(3));
                        miBodegaProductosBean.setDescripcion(rs.getString(4));
                        miBodegaProductosBean.setStock(rs.getInt(5));
                        miBodegaProductosBean.setPrecioProducto(rs.getBigDecimal(6));
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return miBodegaProductosBean;
    }

    public static void crearProducto(String nombreProducto, String descripcion, int stock, BigDecimal precioUnitario){
        // TODO: añadir el manejo de imagenes

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: idBodega, nombreFoto, rutaFoto se ha hardcodeado
        String sql = "insert into producto (nombreFoto,rutaFoto,nombreProducto,descripcion,stock,precioUnitario,idBodega) values (\n" +
                "'foto random', '/fotoRandom', ?, ?, ?, ?, 1);";  // numero de paginas

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

    public static void actualizarProducto(String nombreProducto, String descripcion, int stock, BigDecimal precioUnitario){
        // TODO: añadir el manejo de imagenes

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: idBodega, nombreFoto, rutaFoto se ha hardcodeado
        String sql = "update mydb.producto set descripcion = ?, stock = ?, precioUnitario = ? where nombreProducto = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            // todo: cuando se añada rutaFoto y nombreFoto esto tmb cambiará

            pstmt.setString(1, descripcion);
            pstmt.setInt(2, stock);
            pstmt.setBigDecimal(3, precioUnitario);
            pstmt.setString(4, nombreProducto);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void borrarProducto(String nombreProducto) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";
            try (Connection conn = getConnection()) {
                String sql = "DELETE FROM producto WHERE nombreProducto = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nombreProducto);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
