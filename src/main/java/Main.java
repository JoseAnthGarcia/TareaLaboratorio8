import beans.BodegaBean;
import beans.ProductoBean;
import daos.BodegaDao;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BodegaDao bodegaDao = new BodegaDao();
        BodegaBean bodega = bodegaDao.validarUsuarioPasswordHashed("bodeguita@gmail.com", "bodega7");
        System.out.println(bodega.getNombreBodega());
    }
}
