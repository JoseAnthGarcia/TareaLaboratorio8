package daos;

import beans.BodegaBean;
import beans.DistritoBean;

import java.sql.*;
import java.util.ArrayList;

import static daos.BaseDao.getConnection;

public class AdminDao {

    public static int calcularCantPag() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "select ceil(count(ruc)/5) from bodega where idAdministrador=5;";

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




    public ArrayList<BodegaBean> obtenerListaBodegas(int pagina){

        ArrayList<BodegaBean> listaBodegas = new ArrayList<>();

        int limit = (pagina-1)*5;
        String sql = "select * from bodega where idAdministrador=5 limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    BodegaBean bodega = new BodegaBean();
                    bodega.setRucBodega(rs.getLong("ruc"));
                    bodega.setNombreBodega(rs.getString("nombreBodega"));
                    bodega.setEstadoBodega(rs.getString("estado"));

                    listaBodegas.add(bodega);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return listaBodegas;

    }
    public int buscarIdBodega(Long ruc){
        String sql = "SELECT idBodega FROM bodega WHERE ruc = ?";
        int idBodega=0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setLong(1,ruc);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    idBodega = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return idBodega;
    }

    public static boolean pedidoPendiente(String nombreBodega){    //devuelve true si presenta al menos un pedido en estado pendiente
        boolean pedidoPendiente = false;

        String sql = "select estado from pedido where idBodega=(select idBodega from bodega where nombreBodega = ?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreBodega);

            try (ResultSet rs = pstmt.executeQuery()) {

                while(rs.next()){
                    if(rs.getString(1).toLowerCase().equals("pendiente")){
                        pedidoPendiente = true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidoPendiente;
    }

    public static void actualizarEstadoBodega(String nombreBodega,boolean estado){

        String sql;

        //estado=true -> se bloquea la bodega
        sql = estado ? "update bodega set estado = \"Bloqueado\" where nombreBodega = ?;" : "update bodega set estado = \"Activo\" where nombreBodega = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreBodega);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarBodega(String ruc, String direccion, String nombreBodega, String correo,int idDistrito){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO  bodega(ruc,direccion,idDistrito,nombreBodega,correo,idAdministrador) " +
                "VALUES (?,?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,ruc);
            pstmt.setString(2,direccion);
            pstmt.setInt(3,idDistrito);
            pstmt.setString(4,nombreBodega);
            pstmt.setString(5,correo);
            pstmt.setInt(6,5);

            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void registrarContrasenia(int idBodega,String contrasenia){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "UPDATE bodega SET contrasenia = ? WHERE idBodega = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1,contrasenia);
            pstmt.setInt(2,idBodega);

            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


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

    public boolean buscarRuc(String ruc){
        boolean encontrado = false;

        String sql = "SELECT * FROM bodega WHERE ruc = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,ruc);
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
    public int buscarIdBodega(String ruc){
        String sql = "SELECT idBodega FROM bodega WHERE ruc = ?";
        int idBodega=0;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1,ruc);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    idBodega = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return idBodega;
    }

}
