import beans.ProductoBean;
import daos.BodegaDao;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args){
        System.out.println(BodegaDao.calcularCantPag("lapa"));
        for(ProductoBean productoBean : BodegaDao.listarProductoBodega(1, "lapa")){
            System.out.println(productoBean.getNombreProducto());
        }
    }
}
