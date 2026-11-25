import modelo.*;
import servicio.*;
import excepciones.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("SISTEMA DE GESTION DE TRAMITES CIUDADANOS");
        System.out.println("Modulo: Licencia de Conducir");
        System.out.println("========================================\n");

        GestorTramites gestor = new GestorTramites();

        System.out.println("--- ESCENARIO 1: Flujo exitoso ---");
        try {
            Ciudadano c1 = new Ciudadano("C001", "Maria", "Lopez", "Hernandez", "LOHM900115MDFZPR09", "Av. Reforma 123");
            System.out.println("[OK] Ciudadano registrado: " + c1.obtenerNombreCompleto());

            SolicitudLicencia sol = gestor.registrarSolicitud(c1, "automovilista");
            System.out.println("[OK] Solicitud creada: " + sol.getFolio() + " - Tipo: " + sol.getTipo());

            sol.agregarRequisito(new Requisito("CURP"));
            sol.agregarRequisito(new Requisito("Comprobante de domicilio"));
            sol.agregarRequisito(new Requisito("Identificacion oficial"));

            gestor.entregarRequisito(sol, "CURP");
            gestor.entregarRequisito(sol, "Comprobante de domicilio");
            gestor.entregarRequisito(sol, "Identificacion oficial");
            System.out.println("[OK] Todos los requisitos entregados.");

            sol.avanzarARevision();
            System.out.println("[OK] Estado actual: " + sol.getEstado());

            gestor.procesarPago(sol, 1500.00, "PAY-001");
            System.out.println("[OK] Pago procesado correctamente.");

            gestor.programarCita(sol, "2026-12-05", "10:00");
            System.out.println("[OK] Cita programada.");

            ServidorPublico sp = new ServidorPublico("SP01", "Juan", "Perez", "Gomez", "Jefe de Ventanilla", "Secretaria de Movilidad");
            sol.cambiarEstado("En proceso de emision");
            System.out.println("[OK] " + sp.emitirResolucion(sol, true));
            System.out.println("\n>>> TRAMITE COMPLETADO CON EXITO <<<\n");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        System.out.println("--- ESCENARIO 2: Manejo de excepciones ---");

        try {
            new Ciudadano("C002", "Pedro", "Ramirez", "Soto", "CURP-MALA", "Calle 5");
        } catch (CURPInvalidaException e) {
            System.out.println("[EXCEPCION] " + e.getMessage());
        }

        try {
            Ciudadano c3 = new Ciudadano("C003", "Ana", "Torres", "Vega", "TOVA850520MDFRNL08", "Calle 10");
            SolicitudLicencia sol2 = gestor.registrarSolicitud(c3, "motociclista");
            sol2.agregarRequisito(new Requisito("CURP"));
            sol2.avanzarARevision();
        } catch (RequisitoFaltanteException e) {
            System.out.println("[EXCEPCION] " + e.getMessage());
        } catch (CURPInvalidaException e) {
            System.out.println("[EXCEPCION] " + e.getMessage());
        }

        try {
            Ciudadano c4 = new Ciudadano("C004", "Luis", "Garcia", "Mora", "GAML920315HDFRDS09", "Calle 20");
            SolicitudLicencia sol3 = gestor.registrarSolicitud(c4, "chofer");
            gestor.procesarPago(sol3, 500.00, "PAY-002");
        } catch (PagoInsuficienteException e) {
            System.out.println("[EXCEPCION] " + e.getMessage());
        } catch (CURPInvalidaException e) {
            System.out.println("[EXCEPCION] " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("Fin de la ejecucion.");
        System.out.println("========================================");
    }
}
