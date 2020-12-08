package daos;

import beans.*;
import dtos.ProductoCantDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;


public class UsuarioDao extends BaseDao {

    public ArrayList<DistritoBean> obtenerDistritos() {

        ArrayList<DistritoBean> listaDistritos = new ArrayList<>();

        String sql = "SELECT * FROM distrito;";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement();
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
                                     String contrasenia, int idDistrito) {
        String sql = "INSERT INTO usuario(nombreUsuario, apellido, dni, correo, contrasenia, idDistrito,contraseniaHashed)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, sha2(?,256));";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, nombres);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, dni);
            pstmt.setString(4, correo);
            pstmt.setString(5, contrasenia);
            pstmt.setInt(6, idDistrito);
            pstmt.setString(7, contrasenia);


            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public DistritoBean buscarDistrito(String idDistrito) {

        DistritoBean distrito = null;

        String sql = "SELECT * FROM distrito WHERE idDistrito = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, Integer.parseInt(idDistrito));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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

    public boolean buscarCorreo(String correo) {
        boolean encontrado = false;

        String sql = "SELECT * FROM usuario WHERE correo = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    encontrado = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return encontrado;
    }

    /*Para la parte de editar*/
    public UsuarioBean obtenerUsuario(int usuarioId) {


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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return usuarioBean;
    }

    public void actualizarUsuario(String nombres, String apellidos,
                                  int idDistrito, int idUsuario, UsuarioBean usuarioBean) {

        String sql = "UPDATE usuario SET nombreUsuario = ?, apellido = ?, idDistrito = ? WHERE idUsuario = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, nombres);
            pstmt.setString(2, apellidos);
            pstmt.setInt(3, idDistrito);
            pstmt.setInt(4, idUsuario);
            usuarioBean.setNombre(nombres);
            usuarioBean.setApellido(apellidos);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void actualizarContra(int usuarioID, String contraseniaNew) {
        String sql = "UPDATE usuario SET contrasenia = ? WHERE idUsuario = ?";


        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, contraseniaNew);
            pstmt.setInt(2, usuarioID);

            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Parte de realizarUnPedido:
    public int calcularCantPagQuery(String query, int cantPorPag) {

        String sql = query;  // numero de paginas
        int cantPag = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                cantPag++;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return (int) Math.ceil((double) cantPag / cantPorPag);
    }


    public static ArrayList<ProductoBean> listarProductosBodega(int idBodega, int pagina, int cantPorPag) {

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        int limit = (pagina - 1) * cantPorPag;

        String sql = "SELECT * FROM producto WHERE idBodega=?\n" +
                "limit ?,?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, cantPorPag);

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

    public ArrayList<ProductoBean> buscarProducto(int idBodega, String textoBuscar) {

        ArrayList<ProductoBean> listaProductos = new ArrayList<>();

        String sql = "select * from producto where idBodega =? and lower(nombreProducto) like ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, idBodega);
            pstmt.setString(2, textoBuscar + "%");

            try (ResultSet rs = pstmt.executeQuery();) {

                while (rs.next()) {
                    ProductoBean productoBean = new ProductoBean();

                    productoBean.setId(rs.getInt(1));
                    productoBean.setNombreFoto(rs.getString(2));
                    productoBean.setRutaFoto(rs.getString(3));
                    productoBean.setNombreProducto(rs.getString(4));
                    productoBean.setPrecioProducto(rs.getBigDecimal(7));

                    listaProductos.add(productoBean);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaProductos;
    }

    public UsuarioBean validarUsuarioPassword(String user, String password){
        String sql = "select* from usuario where correo=? and contraseniaHashed=sha2(?,256);";
        UsuarioBean usuarioBean = null;
        try(Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);){

            pstmt.setString(1,user);
            pstmt.setString(2,password);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    int usuarioId = rs.getInt(1);
                    usuarioBean = this.obtenerUsuario(usuarioId);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuarioBean;
    }

    //Realizar pedido----------------------------
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

    //ojo: ya el producto esta como existente, pues sino no se muestra en la lista
    public ProductoBean obtenerProducto(int idProducto){
        ProductoBean producto = null;
        String sql = "SELECT * FROM producto WHERE idProducto=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idProducto);
            try(ResultSet rs = pstmt.executeQuery()){
                //TODO: FALTA O DE FOTOS :C
                rs.next();
                producto = new ProductoBean();
                producto.setId(rs.getInt("idProducto"));
                producto.setNombreProducto(rs.getString("nombreProducto"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecioProducto(rs.getBigDecimal("precioUnitario"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return producto;
    }

    public String generarCodigoPedido(){
        // Los caracteres de interés en un array de char.
        char[] chars = "0123456789".toCharArray();
        // Longitud del array de char.
        int charsLength = chars.length;
        // Instanciamos la clase Random
        Random random = new Random();
        // Un StringBuffer para componer la cadena aleatoria de forma eficiente
        StringBuffer buffer = new StringBuffer();
        // Bucle para elegir una cadena de 10 caracteres al azar
        for (int i = 0; i < 9; i++) {
            // Añadimos al buffer un caracter al azar del array
            buffer.append(chars[random.nextInt(charsLength)]);
        }
        // Y solo nos queda hacer algo con la cadena
        //System.out.println(buffer.toString());
        return buffer.toString();
    }

    public int crearPedido(PedidoBean pedido){
        int idPedido =-1;
        String sql = "insert into pedido (codigo, fecha_registro,\n" +
                "fecha_recojo,idUsuario,idBodega)\n" +
                "values(?,now(),?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            pstmt.setString(1, pedido.getCodigo());
            pstmt.setString(2, pedido.getFecha_recojo());
            pstmt.setInt(3,pedido.getUsuario().getIdUsuario());
            pstmt.setInt(4,pedido.getBodegaBean().getIdBodega());
            pstmt.executeUpdate();
            try(ResultSet rsKey = pstmt.getGeneratedKeys()){
                if(rsKey.next()){
                    idPedido = rsKey.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idPedido;
    }

    public void ingresarProductosApedido(int idPedido, ArrayList<ProductoCantDto> listaProductos){
        for(ProductoCantDto productoPedido: listaProductos){
            String sql = "insert into pedido_has_producto (idPedido,idProducto, Cantidad)\n" +
                    "values(?,?, ?);";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setInt(1, idPedido);
                pstmt.setInt(2,productoPedido.getProducto().getId());
                pstmt.setInt(3,productoPedido.getCant());
                pstmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
