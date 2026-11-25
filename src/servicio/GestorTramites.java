package servicio;
import modelo.*;
import excepciones.*;
import factory.TipoLicenciaFactory;

public class GestorTramites {
    public SolicitudLicencia registrarSolicitud(Ciudadano c, String tipoLicencia) {
        TipoLicencia tipo = TipoLicenciaFactory.crearTipoLicencia(tipoLicencia);
        String folio = "LIC-" + System.currentTimeMillis();
        return new SolicitudLicencia(folio, c, tipo);
    }
    public void entregarRequisito(SolicitudLicencia s, String nombreRequisito) {
        for (Requisito r : s.getRequisitos()) {
            if (r.getNombre().equalsIgnoreCase(nombreRequisito)) {
                r.marcarCumplido();
                return;
            }
        }
    }
    public void procesarPago(SolicitudLicencia s, double monto, String folioPago) throws PagoInsuficienteException {
        Pago p = new Pago(monto, folioPago, s.getTipo().getCostoBase());
        s.registrarPago(p);
    }
    public void programarCita(SolicitudLicencia s, String fecha, String hora) throws CitaNoDisponibleException {
        if (fecha == null || hora == null) {
            throw new CitaNoDisponibleException("Fecha u hora no validas.");
        }
        Cita c = new Cita(fecha, hora);
        c.confirmar();
        s.asignarCita(c);
    }
}
