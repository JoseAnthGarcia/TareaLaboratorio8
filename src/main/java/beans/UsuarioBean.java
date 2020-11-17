package beans;

public class UsuarioBean {
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private String contrasenia;
    private DistritoBean distrito;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public DistritoBean getDistrito() {
        return distrito;
    }

    public void setDistrito(DistritoBean distrito) {
        this.distrito = distrito;
    }
}
