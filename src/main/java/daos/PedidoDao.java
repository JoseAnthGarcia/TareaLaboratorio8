package daos;

import beans.PedidoBean;
import dtos.PedidosDatosDTO;

import java.sql.*;
import java.util.ArrayList;

public class PedidoDao extends BaseDao{
    public int calcularCantPag(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "select ceil(count(codigo)/5) from pedido where idBodega = 30";

        int cantPag = 0;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            rs.next();
            cantPag = rs.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cantPag;
    }

    public ArrayList<PedidoBean> obtenerListaPedidos(int pagina, int idBodega) {
        int limit = (pagina-1)*5;
        ArrayList<PedidoBean> listaPedidos = new ArrayList<>();
        String sql = "SELECT p.idPedido, p.codigo, p.estado FROM pedido p \n" +
                "where p.idBodega = ? limit ?, 5";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idBodega);
            statement.setInt(2, limit);

            try (ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    PedidoBean pedidos = new PedidoBean();
                    pedidos.setId(rs.getInt(1));
                    pedidos.setCodigo(rs.getString(2));
                    pedidos.setEstado(rs.getString(3));
                    listaPedidos.add(pedidos);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPedidos;
    }

    public PedidosDatosDTO mostrarPedido(String codigo) {

        String sql = "SELECT p.codigo, b.nombreBodega, p.fecha_registro, p.fecha_recojo, DATE_ADD(p.fecha_recojo, INTERVAL 1 DAY) as `fecha limite`, count(php.idProducto) as unidades, t1.`costo total` FROM pedido p \n" +
                "LEFT JOIN bodega b ON p.idBodega = b.idBodega left join pedido_has_producto php ON p.idPedido = php.idPedido \n" +
                "LEFT JOIN (SELECT t.idPedido, sum(t.`Costo por producto`) as `costo total` FROM (SELECT php.idPedido, php.idProducto, (php.cantidad*p.precioUnitario) as `Costo por producto`FROM pedido_has_producto php \n" +
                "LEFT JOIN producto p ON php.idProducto = p.idProducto) t  GROUP BY t.idPedido) t1 ON p.idPedido = t1.idPedido \n" +
                "WHERE codigo = ? GROUP BY p.idPedido";
        PedidosDatosDTO pedidos = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, codigo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    pedidos = new PedidosDatosDTO();
                    pedidos.setCodigo(rs.getString("codigo"));
                    pedidos.setNombreBodega(rs.getString("nombreBodega"));
                    pedidos.setFecha_registro(rs.getString("fecha_registro"));
                    pedidos.setFecha_recojo(rs.getString("fecha_recojo"));
                    pedidos.setFecha_limite(rs.getString("fecha limite"));
                    pedidos.setUnidades(rs.getInt("unidades"));
                    pedidos.setCosto_total(rs.getBigDecimal("costo total"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public PedidoBean obtenerPedidoBodega(String codigo) {

        PedidoBean pedidoBean = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql = "SELECT * FROM mydb.pedido WHERE codigo = ?";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, codigo);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        pedidoBean = new PedidoBean();
                        pedidoBean.setId(rs.getInt(1));
                        pedidoBean.setCodigo(rs.getString(2));
                        pedidoBean.setEstado(rs.getString(3));
                    }
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pedidoBean;
    }

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

    public boolean verificarCancelarPedido(String codigo){
        boolean esPosible=false;
        String sql = "select * from pedido where codigo = ?  AND (datediff(now(), fecha_recojo) > 1)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, codigo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    esPosible = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return esPosible;
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