package modelo;
public class Cita {
    private String fecha;
    private String hora;
    private boolean confirmada;

    public Cita(String fecha, String hora) {
        this.fecha = fecha;
        this.hora = hora;
        this.confirmada = false;
    }
    public void confirmar() { this.confirmada = true; }
    public boolean isConfirmada() { return confirmada; }
    @Override
    public String toString() { return fecha + " " + hora + (confirmada ? " [confirmada]" : ""); }
}
