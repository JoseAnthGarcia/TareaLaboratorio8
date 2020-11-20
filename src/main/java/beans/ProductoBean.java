package beans;

import java.math.BigDecimal;

public class ProductoBean {

    private int id;
    private String nombreFoto;
    private String nombreProducto;
    private String descripcion;
    private int stock;
    private BigDecimal precioProducto;
    private String rutaFoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }
}
