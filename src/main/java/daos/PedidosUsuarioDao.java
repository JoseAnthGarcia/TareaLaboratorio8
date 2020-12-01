package daos;

import beans.PedidoBean;

import java.sql.*;
import java.util.ArrayList;

import static daos.BaseDao.getConnection;

public class PedidosUsuarioDao {

    public int calcularCantPag(){

        String sql = "select ceil(count(codigo)/5) from pedido where idUsuario=23;";

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

    public ArrayList<PedidoBean> listarPedidosCliente (int pagina){

        ArrayList<PedidoBean> listaPedidos=  new ArrayList<>();
        int limit = (pagina-1)*5;
        String sql= "select codigo, estado \n" +
                "from pedido\n" +
                "where idUsuario=23 order by estado desc\n" +
                "limit ?,5;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while(rs.next()){
                    PedidoBean pedidosClienteBean = new PedidoBean();
                    pedidosClienteBean.setCodigo(rs.getString("codigo"));
                    pedidosClienteBean.setEstado(rs.getString("estado"));
                    listaPedidos.add(pedidosClienteBean);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaPedidos;
    }

    public static boolean verificarHoraPedido(int codigo){
        boolean aTiempo = true;

        String sql;

        sql = "select (TIMEDIFF(fecha_recojo, now())) from pedido where codigo=?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            try(ResultSet rs = pstmt.executeQuery();){
                rs.next();
                String tiempo = rs.getString(1);
                String[] array = tiempo.split(":");
                if(Integer.parseInt(array[0])==0){   //Si rs < 60 minutos -> aTiempo = false
                    aTiempo=false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aTiempo;
    }

    public static void cancelarPedido(int codigo){
        String sql;

        sql = "\n" +
                "update pedido set estado= \"Cancelado\" where codigo=?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
