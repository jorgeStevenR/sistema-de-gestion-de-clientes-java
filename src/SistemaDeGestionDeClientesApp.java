import controller.ClienteController;
import model.Cliente;
import model.Rol;
import services.ClienteServices;
import java.util.Scanner;

/**
 * Clase principal del sistema de gestión de clientes.
 * 
 * Permite iniciar sesión con distintos usuarios sin cerrar el programa
 * hasta que el usuario elija salir explícitamente.
 * 
 * Flujo:
 * - Inicializa el sistema.
 * - Crea usuarios base (admin y estándar) usando el servicio.
 * - Permite iniciar sesión, cerrar sesión y volver a entrar.
 * - Finaliza solo si el usuario lo decide.
 * 
 * @author Jorge
 * @version 1.3
 */
public class SistemaDeGestionDeClientesApp {

    public static void main(String[] args) {

        System.out.println("===== SISTEMA DE GESTIÓN DE CLIENTES =====");

        // Servicio con capacidad para 50 clientes
        ClienteServices clienteServices = new ClienteServices(50);

        // Controlador del sistema
        ClienteController controller = new ClienteController(clienteServices);

        // Crear usuarios base usando el servicio (no creando objetos manualmente)
        clienteServices.crearUsuarios("adminUser", "Administrador del Sistema", "admin123", Rol.ADMINISTRADOR);
        clienteServices.crearUsuarios("brajan123", "Brajan Velásquez", "1234", Rol.ESTANDAR);

        // Scanner general
        Scanner scanner = new Scanner(System.in);

        // Bucle principal del sistema (permite relogueo)
        boolean salirDelPrograma = false;

        while (!salirDelPrograma) {

            // --- Login ---
            Cliente usuarioLogueado = null;
            while (usuarioLogueado == null) {
                System.out.print("\nIngrese su nombre de usuario: ");
                String username = scanner.nextLine().trim();

                System.out.print("Ingrese su contraseña: ");
                String password = scanner.nextLine().trim();

                usuarioLogueado = controller.iniciarSesion(username, password);
            }

            // --- Menú principal según el rol ---
            controller.mostrarMenu(usuarioLogueado);

            // --- Preguntar si quiere cerrar el programa o iniciar otra sesión ---
            System.out.print("\n¿Desea iniciar sesión con otro usuario? (s/n): ");
            String respuesta = scanner.nextLine();

            if (!respuesta.equalsIgnoreCase("s")) {
                salirDelPrograma = true;
                System.out.println("\nGracias por usar el sistema. Programa finalizado.");
            }
        }
    }
}
