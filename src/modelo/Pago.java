package modelo;
import excepciones.PagoInsuficienteException;

public class Pago {
    private double monto;
    private String folio;
    private boolean procesado;

    public Pago(double monto, String folio, double costoEsperado) throws PagoInsuficienteException {
        if (monto < costoEsperado) {
            throw new PagoInsuficienteException("Monto insuficiente. Esperado: $" + costoEsperado + ", recibido: $" + monto);
        }
        this.monto = monto;
        this.folio = folio;
        this.procesado = true;
    }
    public double getMonto() { return monto; }
    public String getFolio() { return folio; }
    public boolean isProcesado() { return procesado; }
}
