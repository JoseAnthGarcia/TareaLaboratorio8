package daos;

import beans.DistritoBean;
import beans.ProductoBean;
import beans.UsuarioBean;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDao extends BaseDao{

    public ArrayList<DistritoBean> obtenerDistritos(){

        ArrayList<DistritoBean> listaDistritos = new ArrayList<>();

        String sql = "SELECT * FROM distrito;";
        try (Connection conn = getConnection();Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {

                while (rs.next()) {
                    DistritoBean distrito = new DistritoBean();
                    distrito.setId(rs.getInt(1));
                    distrito.setNombre(rs.getString(2));
                    listaDistritos.add(distrito);
                }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaDistritos;
    }

    public void regitrarNuevoUsuario(String nombres, String apellidos,
                                     String dni, String correo,
                                     String contrasenia, int idDistrito){
        String sql = "INSERT INTO usuario(nombreUsuario, apellido, dni, correo, contrasenia, idDistrito)\n" +
                "VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,nombres);
            pstmt.setString(2,apellidos);
            pstmt.setString(3,dni);
            pstmt.setString(4, correo);
            pstmt.setString(5, contrasenia);
            pstmt.setInt(6, idDistrito);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DistritoBean buscarDistrito(String idDistrito){

        DistritoBean distrito = null;

        String sql = "SELECT * FROM distrito WHERE idDistrito = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setInt(1,Integer.parseInt(idDistrito));

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    distrito = new DistritoBean();
                    distrito.setId(rs.getInt(1));
                    distrito.setNombre(rs.getString(2));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return distrito;
    }

    public boolean buscarCorreo(String correo){
        boolean encontrado = false;

        String sql = "SELECT * FROM usuario WHERE correo = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,correo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    encontrado = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return encontrado;
    }

    /*Para la parte de editar*/
    public UsuarioBean obtenerUsuario(int usuarioId){



        String sql = "select u.idUsuario, u.nombreUsuario, u.apellido, u.dni, u.correo, u.contrasenia,u.idDistrito, d.nombreDistrito\n" +
                "from usuario u\n" +
                "inner join distrito d on u.idDistrito=d.idDistrito\n" +
                "where idUsuario=?;";
        UsuarioBean usuarioBean = new UsuarioBean();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, usuarioId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    DistritoBean distritoBean = new DistritoBean();
                    usuarioBean.setIdUsuario(rs.getInt(1));
                    usuarioBean.setNombre(rs.getString(2));
                    usuarioBean.setApellido(rs.getString(3));
                    usuarioBean.setDni(rs.getString(4));
                    usuarioBean.setCorreo(rs.getString(5));
                    usuarioBean.setContrasenia(rs.getString(6));
                    distritoBean.setId(rs.getInt(7));
                    distritoBean.setNombre(rs.getString(8));
                    usuarioBean.setDistrito(distritoBean);


                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return usuarioBean;
    }

    public void actualizarUsuario(String nombres, String apellidos,
                                      int idDistrito, int idUsuario){

        String sql = "UPDATE usuario SET nombreUsuario = ?, apellido = ?, idDistrito = ? WHERE idUsuario = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,nombres);
            pstmt.setString(2,apellidos);
            pstmt.setInt(3, idDistrito);
            pstmt.setInt(4, idUsuario);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void actualizarContra(int usuarioID, String contraseniaNew){
        String sql = "UPDATE usuario SET contrasenia = ? WHERE idUsuario = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,contraseniaNew);
            pstmt.setInt(2,usuarioID);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Parte de realizarUnPedido:
    public int calcularCantPagQuery(String query, int cantPorPag){

        String sql = query;  // numero de paginas
        int cantPag = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            while(rs.next()){
                cantPag++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (int)Math.ceil((double)cantPag/cantPorPag);
    }


    public static ArrayList<ProductoBean> listarProductosBodega(int idBodega, int pagina, int CantPorPag) {

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        int limit = (pagina - 1) * CantPorPag;

        String sql = "SELECT * FROM producto WHERE idBodega=?\n" +
                "limit ?,?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1,idBodega);
            pstmt.setInt(2, limit);
            pstmt.setInt(3,CantPorPag);

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    ProductoBean producto = new ProductoBean();
                    producto.setId(rs.getInt(1));
                    producto.setNombreFoto(rs.getString(2));
                    producto.setRutaFoto(rs.getString(3));
                    producto.setNombreProducto(rs.getString(4));
                    producto.setPrecioProducto(rs.getBigDecimal(7));
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaProductos;
    }
    public ArrayList<ProductoBean> buscarProducto(String textoBuscar) {

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();
        String sql = "select idProducto, nombreFoto, nombreProducto, descripcion, stock, precioUnitario, rutaFoto\n" +
                "from producto p where idBodega =30 and lower(nombreProducto) like ? ;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,"%"+textoBuscar+"%");


            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    ProductoBean productoBean = new ProductoBean();
                    productoBean.setId(rs.getInt(1));
                    productoBean.setNombreFoto(rs.getString(2));
                    productoBean.setNombreProducto(rs.getString(3));
                    productoBean.setDescripcion(rs.getString(4));
                    productoBean.setStock(rs.getInt(5));
                    productoBean.setPrecioProducto(rs.getBigDecimal(6));
                    productoBean.setRutaFoto(rs.getString(7));


                    listaProductos.add(productoBean);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaProductos;
    }



}
