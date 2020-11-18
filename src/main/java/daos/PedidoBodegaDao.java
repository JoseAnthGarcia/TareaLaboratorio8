package daos;

import beans.PedidosBodegaBean;

import java.sql.*;
import java.util.ArrayList;

public class PedidoBodegaDao extends BaseDao{

    public int calcularCantPag(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        String sql = "select ceil(count(codigo)/5) from pedido where idBodega = 1";

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

    public ArrayList<PedidosBodegaBean> obtenerListaPedidosBodega(int pagina) {
        int limit = (pagina-1)*5;
        ArrayList<PedidosBodegaBean> listaPedidosBodega = new ArrayList<>();
        String sql = "select idPedido, codigo, estado from pedido where idBodega = 1 limit ?, 5";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);

            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    PedidosBodegaBean pedidos = new PedidosBodegaBean();
                    pedidos.setIdPedido(rs.getInt(1));
                    pedidos.setCodigo(rs.getString(2));
                    pedidos.setEstado(rs.getString(3));
                    listaPedidosBodega.add(pedidos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPedidosBodega;
    }

    public PedidosBodegaBean mostrarPedido(String codigo) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "SELECT p.codigo, b.nombreBodega, p.fecha_registro, p.fecha_recojo, \n" +
                "DATE_ADD(p.fecha_recojo, INTERVAL 1 DAY) as `fecha limite`, count(php.idProducto), t1.`costo total` \n" +
                "FROM pedido p LEFT JOIN bodega b ON p.idBodega = b.idBodega left join pedido_has_producto php \n" +
                "ON p.idPedido = php.idPedido LEFT JOIN (SELECT t.idPedido, sum(t.`Costo por producto`) as `costo total` \n" +
                "FROM (SELECT php.idPedido, php.idProducto, (php.cantidad*p.precioUnitario) as `Costo por producto`FROM pedido_has_producto php \n" +
                "LEFT JOIN producto p ON php.idProducto = p.idProducto) t  GROUP BY t.idPedido) t1 ON p.idPedido = t1.idPedido \n" +
                "WHERE codigo = ? GROUP BY p.idPedido";
        PedidosBodegaBean pedidos = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    pedidos = new PedidosBodegaBean();
                    pedidos.setCodigo(rs.getString(2));
                    pedidos.setFecha_registro(rs.getString(4));
                    pedidos.setFecha_recojo(rs.getString(5));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public PedidosBodegaBean obtenerPedidoBodega(String codigo) {

        PedidosBodegaBean pedidosBodegaBean = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM mydb.pedido WHERE codigo = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, codigo);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        pedidosBodegaBean = new PedidosBodegaBean();
                        pedidosBodegaBean.setIdPedido(rs.getInt(1));
                        pedidosBodegaBean.setCodigo(rs.getString(2));
                        pedidosBodegaBean.setEstado(rs.getString(3));
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pedidosBodegaBean;
    }
    /*public void borrarTrabajo(String jobId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = getConnection()) {
                String sql = "DELETE FROM jobs WHERE job_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, jobId);
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    public void entregarPedido(String codigo) {

        try (Connection conn = getConnection();) {
            String sql = "update mydb.pedido set estado = 'Entregado' where codigo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, codigo);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelarPedido(String codigo) {
        try (Connection conn = getConnection();) {
            String sql = "update mydb.pedido set estado = 'Cancelado' where codigo = ?  AND (datediff(now(), fecha_recojo) > 1)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, codigo);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
