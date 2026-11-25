package factory;
import modelo.TipoLicencia;

public class TipoLicenciaFactory {
    public static TipoLicencia crearTipoLicencia(String tipo) {
        switch (tipo.toLowerCase()) {
            case "automovilista":
                return new TipoLicencia("Automovilista", 1500.00, 5, "Licencia para vehiculos particulares");
            case "motociclista":
                return new TipoLicencia("Motociclista", 1200.00, 4, "Licencia para motocicletas");
            case "chofer":
                return new TipoLicencia("Chofer", 2200.00, 5, "Licencia para transporte publico");
            case "conductor":
                return new TipoLicencia("Conductor", 1800.00, 5, "Licencia federal de conductor");
            default:
                throw new IllegalArgumentException("Tipo de licencia no reconocido: " + tipo);
        }
    }
}
