package modelo;
public abstract class Persona {
    private String idPersona;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Persona(String idPersona, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }
    public String obtenerNombreCompleto() {
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    public String getIdPersona() { return idPersona; }
    public String getNombre() { return nombre; }
}
