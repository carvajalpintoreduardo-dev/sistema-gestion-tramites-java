package modelo;
import excepciones.CURPInvalidaException;
import java.util.ArrayList;
import java.util.List;

public class Ciudadano extends Persona {
    private String curp;
    private String domicilio;
    private List<SolicitudLicencia> solicitudes;

    public Ciudadano(String idPersona, String nombre, String apP, String apM, String curp, String domicilio) throws CURPInvalidaException {
        super(idPersona, nombre, apP, apM);
        validarCURP(curp);
        this.curp = curp;
        this.domicilio = domicilio;
        this.solicitudes = new ArrayList<>();
    }
    private void validarCURP(String curp) throws CURPInvalidaException {
        if (curp == null || curp.length() != 18) {
            throw new CURPInvalidaException("CURP invalida: debe tener 18 caracteres. Recibido: " + curp);
        }
    }
    public void agregarSolicitud(SolicitudLicencia s) { solicitudes.add(s); }
    public List<SolicitudLicencia> getSolicitudes() { return solicitudes; }
    public String getCurp() { return curp; }
}
