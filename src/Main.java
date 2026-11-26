import modelo.*;
import servicio.*;
import excepciones.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("====================================================================");
        System.out.println("       SISTEMA DE GESTIÓN DE TRÁMITES CIUDADANOS (v1.0)             ");
        System.out.println("       Módulo: Licencia de Conducir - Ejecución en Consola          ");
        System.out.println("====================================================================\n");

        GestorTramites gestor = new GestorTramites();

        // ==========================================================
        // ESCENARIO 1: FLUJO EXITOSO CON EVIDENCIA DETALLADA
        // ==========================================================
        System.out.println("▶ INICIO DEL PROCESO: Trámite de Licencia de Conducir\n");

        // 1. Cómo ingresan los datos del ciudadano
        System.out.println("[1] INGRESO DE DATOS DEL CIUDADANO:");
        System.out.println("    - Nombre: María López Hernández");
        System.out.println("    - CURP: LOHM900115MDFZPR09");
        System.out.println("    - Domicilio: Av. Reforma 123");
        try {
            Ciudadano c1 = new Ciudadano("C001", "María", "López", "Hernández", "LOHM900115MDFZPR09", "Av. Reforma 123");
            System.out.println("    ✅ Ciudadano registrado exitosamente en el sistema.\n");

            // 2. Registro de solicitud y tipo
            System.out.println("[2] REGISTRO DE SOLICITUD:");
            SolicitudLicencia sol = gestor.registrarSolicitud(c1, "automovilista");
            System.out.println("    - Folio generado: " + sol.getFolio());
            System.out.println("    - Tipo de trámite: " + sol.getTipo().getNombre());
            
            // 3. Cómo se determina en qué fase del proceso se encuentra
            System.out.println("    📍 FASE ACTUAL DEL PROCESO: " + sol.getEstado().toUpperCase() + "\n");

            // 4. Validación de requisitos
            System.out.println("[3] VALIDACIÓN DE REQUISITOS:");
            sol.agregarRequisito(new Requisito("CURP"));
            sol.agregarRequisito(new Requisito("Comprobante de domicilio"));
            sol.agregarRequisito(new Requisito("Identificación oficial"));
            
            gestor.entregarRequisito(sol, "CURP");
            gestor.entregarRequisito(sol, "Comprobante de domicilio");
            gestor.entregarRequisito(sol, "Identificación oficial");
            System.out.println("    ✅ Todos los documentos han sido cargados y validados.");
            
            sol.avanzarARevision();
            System.out.println("    📍 FASE ACTUAL DEL PROCESO: " + sol.getEstado().toUpperCase() + "\n");

            // 5. Pago
            System.out.println("[4] REGISTRO DE PAGO:");
            System.out.println("    - Costo del trámite: $" + sol.getTipo().getCostoBase());
            gestor.procesarPago(sol, 1500.00, "PAY-001");
            System.out.println("    ✅ Pago de $1500.00 procesado y acreditado correctamente.\n");

            // 6. Cita
            System.out.println("[5] PROGRAMACIÓN DE CITA:");
            gestor.programarCita(sol, "2026-12-05", "10:00");
            System.out.println("    ✅ Cita asignada para el 2026-12-05 a las 10:00 hrs.\n");

            // 7. Cómo se determinó la resolución
            System.out.println("[6] DETERMINACIÓN DE LA RESOLUCIÓN ADMINISTRATIVA:");
            System.out.println("    - Evaluando documentación: " + (sol.todosRequisitosCumplidos() ? "COMPLETA" : "INCOMPLETA"));
            System.out.println("    - Evaluando pago: ACREDITADO");
            System.out.println("    - Evaluando cita: CONFIRMADA");
            
            ServidorPublico sp = new ServidorPublico("SP01", "Juan", "Pérez", "Gómez", "Jefe de Ventanilla", "Secretaría de Movilidad");
            sol.cambiarEstado("En proceso de emisión");
            
            System.out.println("    📍 FASE ACTUAL DEL PROCESO: " + sol.getEstado().toUpperCase());
            String resolucion = sp.emitirResolucion(sol, true);
            System.out.println("    🏁 RESOLUCIÓN FINAL: " + resolucion + "\n");
            
            System.out.println("====================================================================");
            System.out.println("✅ TRÁMITE COMPLETADO CON ÉXITO. El ciudadano puede recoger su licencia.");
            System.out.println("====================================================================\n\n");

        } catch (Exception e) {
            System.out.println("❌ ERROR CRÍTICO: " + e.getMessage() + "\n");
        }

        // ==========================================================
        // ESCENARIO 2: MANEJO DE EXCEPCIONES (Evidencia de robustez)
        // ==========================================================
        System.out.println("▶ PRUEBAS DE MANEJO DE EXCEPCIONES (Robustez del Sistema)\n");

        System.out.println("Prueba A: Intento de registro con CURP inválida");
        try {
            new Ciudadano("C002", "Pedro", "Ramírez", "Soto", "CURP-MALA", "Calle 5");
        } catch (CURPInvalidaException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        }

        System.out.println("\nPrueba B: Intento de avanzar de fase sin requisitos");
        try {
            Ciudadano c3 = new Ciudadano("C003", "Ana", "Torres", "Vega", "TOVA850520MDFRNL08", "Calle 10");
            SolicitudLicencia sol2 = gestor.registrarSolicitud(c3, "motociclista");
            sol2.agregarRequisito(new Requisito("CURP")); // Solo 1 de 3
            sol2.avanzarARevision(); // Esto debe fallar
        } catch (RequisitoFaltanteException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        } catch (CURPInvalidaException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        }

        System.out.println("\nPrueba C: Intento de pago con monto insuficiente");
        try {
            Ciudadano c4 = new Ciudadano("C004", "Luis", "García", "Mora", "GAML920315HDFRDS09", "Calle 20");
            SolicitudLicencia sol3 = gestor.registrarSolicitud(c4, "chofer"); // Cuesta $2200
            gestor.procesarPago(sol3, 500.00, "PAY-002"); // Solo paga $500
        } catch (PagoInsuficienteException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        } catch (CURPInvalidaException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        }

        System.out.println("\n====================================================================");
        System.out.println("Fin de la ejecución. Sistema operando con normalidad.");
        System.out.println("====================================================================");
    }
}
