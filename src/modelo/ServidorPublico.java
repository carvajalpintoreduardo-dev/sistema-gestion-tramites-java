package modelo;
public class ServidorPublico extends Persona {
    private String cargo;
    private String adscripcion;

    public ServidorPublico(String idPersona, String nombre, String apP, String apM, String cargo, String adscripcion) {
        super(idPersona, nombre, apP, apM);
        this.cargo = cargo;
        this.adscripcion = adscripcion;
    }
    public String emitirResolucion(SolicitudLicencia s, boolean aprobar) {
        if (aprobar) s.cambiarEstado("Aprobada");
        else s.cambiarEstado("Rechazada");
        return "Resolucion emitida por " + obtenerNombreCompleto() + " (" + cargo + "): " + s.getEstado();
    }
    public boolean validarDocumentacion(SolicitudLicencia s) {
        return s.todosRequisitosCumplidos();
    }
}
