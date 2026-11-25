package modelo;
import excepciones.RequisitoFaltanteException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudLicencia {
    private String folio;
    private Ciudadano ciudadano;
    private TipoLicencia tipo;
    private List<Requisito> requisitos;
    private Cita cita;
    private List<Pago> pagos;
    private String estado;

    public SolicitudLicencia(String folio, Ciudadano ciudadano, TipoLicencia tipo) {
        this.folio = folio;
        this.ciudadano = ciudadano;
        this.tipo = tipo;
        this.requisitos = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.estado = "Registrada";
        ciudadano.agregarSolicitud(this);
    }
    public void agregarRequisito(Requisito r) { requisitos.add(r); }
    public boolean todosRequisitosCumplidos() {
        if (requisitos.isEmpty()) return false;
        for (Requisito r : requisitos) { if (!r.isCumplido()) return false; }
        return true;
    }
    public void cambiarEstado(String nuevoEstado) {
        if (estado.equals("Rechazada") && nuevoEstado.equals("Aprobada")) {
            throw new IllegalStateException("No se puede aprobar una solicitud rechazada.");
        }
        this.estado = nuevoEstado;
    }
    public void avanzarARevision() throws RequisitoFaltanteException {
        if (!todosRequisitosCumplidos()) {
            throw new RequisitoFaltanteException("Faltan requisitos para avanzar. Folio: " + folio);
        }
        cambiarEstado("En revision");
    }
    public void registrarPago(Pago p) { pagos.add(p); }
    public void asignarCita(Cita c) { this.cita = c; }
    public String getFolio() { return folio; }
    public String getEstado() { return estado; }
    public TipoLicencia getTipo() { return tipo; }
    public List<Requisito> getRequisitos() { return requisitos; }
}
