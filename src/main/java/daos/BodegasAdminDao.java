package daos;

import main.java.beans.BodegasAdminBean;

import java.sql.*;
import java.util.ArrayList;

import static daos.BaseDao.getConnection;

public class BodegasAdminDao {

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




    public ArrayList<BodegasAdminBean> obtenerListaBodegas(int pagina){

        ArrayList<BodegasAdminBean> listaBodegas = new ArrayList<>();

        int limit = (pagina-1)*5;
        String sql = "select * from bodega where idAdministrador=5 limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    BodegasAdminBean bodega = new BodegasAdminBean();
                    bodega.setRucBodega(rs.getInt("ruc"));
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

}
