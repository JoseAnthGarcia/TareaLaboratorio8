import beans.ProductoBean;
import daos.BodegaDao;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){
        BodegaDao.crearProducto("nombre", "descipr", 99, BigDecimal.valueOf(29.01));
        for(ProductoBean productoBean : BodegaDao.listarProductoBodega(2)){
            System.out.println(productoBean.getNombreFoto());
        }
    }
}
