package daos;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeDao extends BaseDao{

    public void obtenerDistritos(){
        try (Connection connection = getConnection();){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
