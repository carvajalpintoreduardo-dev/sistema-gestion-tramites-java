import modelo.*;
import servicio.*;
import excepciones.*;
import java.util.Scanner; // <--- EVIDENCIA DE LA LIBRERÍA SOLICITADA

public class Main {
    public static void main(String[] args) {
        // Instanciamos el objeto Scanner para leer desde el teclado
        Scanner scanner = new Scanner(System.in); 
        
        System.out.println("====================================================================");
        System.out.println("       SISTEMA DE GESTIÓN DE TRÁMITES CIUDADANOS (v1.0)             ");
        System.out.println("       Módulo: Licencia de Conducir - Interfaz de Consola           ");
        System.out.println("====================================================================\n");

        GestorTramites gestor = new GestorTramites();

        // ==========================================================
        // ESCENARIO 1: INGRESO DE DATOS VÍA TECLADO (Scanner)
        // ==========================================================
        System.out.println("▶ INICIO DEL PROCESO: Trámite de Licencia de Conducir\n");

        System.out.println("[1] INGRESO DE DATOS DEL CIUDADANO (vía teclado):");
        System.out.print("    - ID Ciudadano: "); String id = scanner.nextLine();
        System.out.print("    - Nombre: "); String nombre = scanner.nextLine();
        System.out.print("    - Apellido Paterno: "); String apP = scanner.nextLine();
        System.out.print("    - Apellido Materno: "); String apM = scanner.nextLine();
        System.out.print("    - CURP (18 caracteres): "); String curp = scanner.nextLine();
        System.out.print("    - Domicilio: "); String domicilio = scanner.nextLine();

        try {
            Ciudadano c1 = new Ciudadano(id, nombre, apP, apM, curp, domicilio);
            System.out.println("    ✅ Ciudadano registrado exitosamente.\n");

            System.out.println("[2] SELECCIÓN DE TIPO DE LICENCIA:");
            System.out.println("    Opciones: automovilista, motociclista, chofer, conductor");
            System.out.print("    - Ingrese tipo: "); String tipoLicencia = scanner.nextLine();
            
            SolicitudLicencia sol = gestor.registrarSolicitud(c1, tipoLicencia);
            System.out.println("    - Folio generado: " + sol.getFolio());
            System.out.println("    - Tipo de trámite: " + sol.getTipo().getNombre());
            System.out.println("    📍 FASE ACTUAL: " + sol.getEstado().toUpperCase() + "\n");

            System.out.println("[3] CARGA DE REQUISITOS:");
            System.out.println("    (El sistema agrega automáticamente los 3 requisitos base)");
            sol.agregarRequisito(new Requisito("CURP"));
            sol.agregarRequisito(new Requisito("Comprobante de domicilio"));
            sol.agregarRequisito(new Requisito("Identificación oficial"));
            
            System.out.println("    - Marcando requisitos como entregados...");
            gestor.entregarRequisito(sol, "CURP");
            gestor.entregarRequisito(sol, "Comprobante de domicilio");
            gestor.entregarRequisito(sol, "Identificación oficial");
            
            sol.avanzarARevision();
            System.out.println("    📍 FASE ACTUAL: " + sol.getEstado().toUpperCase() + "\n");

            System.out.println("[4] REGISTRO DE PAGO:");
            System.out.println("    - Costo del trámite: $" + sol.getTipo().getCostoBase());
            System.out.print("    - Ingrese monto a pagar: $"); double montoPago = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer
            System.out.print("    - Folio de pago bancario: "); String folioPago = scanner.nextLine();
            
            gestor.procesarPago(sol, montoPago, folioPago);
            System.out.println("    ✅ Pago procesado y acreditado correctamente.\n");

            System.out.println("[5] PROGRAMACIÓN DE CITA:");
            System.out.print("    - Fecha (YYYY-MM-DD): "); String fecha = scanner.nextLine();
            System.out.print("    - Hora (HH:MM): "); String hora = scanner.nextLine();
            gestor.programarCita(sol, fecha, hora);
            System.out.println("    ✅ Cita asignada.\n");

            System.out.println("[6] RESOLUCIÓN ADMINISTRATIVA:");
            ServidorPublico sp = new ServidorPublico("SP01", "Juan", "Pérez", "Gómez", "Jefe de Ventanilla", "Secretaría de Movilidad");
            sol.cambiarEstado("En proceso de emisión");
            System.out.println("    📍 FASE ACTUAL: " + sol.getEstado().toUpperCase());
            
            String resolucion = sp.emitirResolucion(sol, true);
            System.out.println("    🏁 RESOLUCIÓN FINAL: " + resolucion + "\n");
            
            System.out.println("====================================================================");
            System.out.println("✅ TRÁMITE COMPLETADO CON ÉXITO.");
            System.out.println("====================================================================\n\n");

        } catch (Exception e) {
            System.out.println("❌ ERROR EN EL FLUJO: " + e.getMessage() + "\n");
        }

        // ==========================================================
        // ESCENARIO 2: DEMOSTRACIÓN AUTOMÁTICA DE EXCEPCIONES
        // (Se mantiene automático para garantizar que se vean los errores)
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
            sol2.agregarRequisito(new Requisito("CURP")); 
            sol2.avanzarARevision(); 
        } catch (RequisitoFaltanteException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        } catch (CURPInvalidaException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        }

        System.out.println("\nPrueba C: Intento de pago con monto insuficiente");
        try {
            Ciudadano c4 = new Ciudadano("C004", "Luis", "García", "Mora", "GAML920315HDFRDS09", "Calle 20");
            SolicitudLicencia sol3 = gestor.registrarSolicitud(c4, "chofer"); 
            gestor.procesarPago(sol3, 500.00, "PAY-002"); 
        } catch (PagoInsuficienteException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        } catch (CURPInvalidaException e) {
            System.out.println("    ⚠️ [EXCEPCION CAPTURADA] " + e.getMessage());
        }

        System.out.println("\n====================================================================");
        System.out.println("Fin de la ejecución. Sistema operando con normalidad.");
        System.out.println("====================================================================");
        
        scanner.close();
    }
}
