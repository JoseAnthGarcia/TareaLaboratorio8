package daos;

import beans.DistritoBean;
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
}
