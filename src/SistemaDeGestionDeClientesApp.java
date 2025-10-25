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
 * - Crea usuarios base (admin y estándar).
 * - Permite iniciar sesión, cerrar sesión y volver a entrar.
 * - Finaliza solo si el usuario lo decide.
 * 
 * @author Brajan
 * @version 1.1
 */
public class SistemaDeGestionDeClientesApp {

    public static void main(String[] args) {

        System.out.println("===== SISTEMA DE GESTIÓN DE CLIENTES =====");

        // Servicio con capacidad para 50 clientes
        ClienteServices clienteServices = new ClienteServices(50);

        // Controlador del sistema
        ClienteController controller = new ClienteController(clienteServices);

        // Usuarios iniciales (ID autogenerado)
        Cliente admin = new Cliente("adminUser", "Administrador del Sistema", "admin123", Rol.ADMINISTRADOR);
        Cliente user = new Cliente("brajan123", "Brajan Velásquez", "1234", Rol.ESTANDAR);

        clienteServices.crearUsuarios(admin);
        clienteServices.crearUsuarios(user);

        // Scanner general
        Scanner scanner = new Scanner(System.in);

        // Bucle principal del sistema (permite relogueo)
        boolean salirDelPrograma = false;

        while (!salirDelPrograma) {

            // --- Login ---
            Cliente usuarioLogueado = null;
            while (usuarioLogueado == null) {
                System.out.print("\nIngrese su nombre de usuario: ");
                String username = scanner.nextLine();

                System.out.print("Ingrese su contraseña: ");
                String password = scanner.nextLine();

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