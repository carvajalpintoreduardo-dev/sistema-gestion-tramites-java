package modelo;
public class TipoLicencia {
    private String nombre;
    private double costoBase;
    private int vigenciaAnios;
    private String descripcion;

    public TipoLicencia(String nombre, double costoBase, int vigenciaAnios, String descripcion) {
        this.nombre = nombre;
        this.costoBase = costoBase;
        this.vigenciaAnios = vigenciaAnios;
        this.descripcion = descripcion;
    }
    public double getCostoBase() { return costoBase; }
    public String getNombre() { return nombre; }
    @Override
    public String toString() { return nombre + " (vigencia " + vigenciaAnios + " anos, $" + costoBase + ")"; }
}
