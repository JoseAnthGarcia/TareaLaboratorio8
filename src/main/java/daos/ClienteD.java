
package daos;

import beans.distritosB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteD {
    public ClienteD() {
    }

    public int calcularCantPag(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        String sql = "select ceil(count(nombreFoto)/8)\n" +
                "from bodega\n;";

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

    public ArrayList<distritosB> listarBodegas(int pagina){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList listaBodegas = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        int limit = (pagina-1)*8;

        String sql = "select nombreFoto,rutaFoto ,nombreBodega, direccion\n" +
                "from bodega where lower(estado) = \"activo\" limit ?,8;;";

        try (Connection conn = DriverManager.getConnection(url, "root", "root");
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    distritosB bodega = new distritosB();
                    bodega.setNombreFoto(rs.getString(1));
                    bodega.setRutaFoto(rs.getString(2));
                    bodega.setNombreBodega(rs.getString(3));
                    bodega.setDireccion(rs.getString(4));
                    listaBodegas.add(bodega);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaBodegas;
    }

    public int calcularCantPagDistrito(int idDistrito){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        String sql = "select ceil(count(nombreFoto)/3)\n" +
                "from bodega\n where idDistrito="+idDistrito+" and  lower(estado) = \"activo\";";

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


    public ArrayList<distritosB> listarBodegasDistrito(int pagina, int idDistrito){
        ArrayList<distritosB> listaBodegas = new ArrayList();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=America/Lima";

        int limit = (pagina-1)*3;

        String sql = "select nombreFoto,rutaFoto ,nombreBodega, direccion\n" +
                "from bodega where idDistrito = "+idDistrito+" " +
                "and  lower(estado) = \"activo\" limit ?,3;;";


        try (Connection conn = DriverManager.getConnection(url, "root", "root");
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setInt(1, limit);

            try(ResultSet rs = pstmt.executeQuery();){
                while (rs.next()) {
                    distritosB bodega = new distritosB();
                    bodega.setNombreFoto(rs.getString(1));
                    bodega.setRutaFoto(rs.getString(2));
                    bodega.setNombreBodega(rs.getString(3));
                    bodega.setDireccion(rs.getString(4));
                    listaBodegas.add(bodega);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listaBodegas;

    }
}