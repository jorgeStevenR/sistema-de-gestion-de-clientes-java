package controller;

import model.*;
import services.ClienteServices;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Controlador principal del sistema de gestión de clientes.
 * 
 * Se encarga de manejar el flujo lógico del sistema:
 * - Iniciar sesión.
 * - Mostrar el menú según el rol.
 * - Ejecutar las operaciones CRUD.
 * - Registrar acciones de los usuarios.
 * 
 * @author Jorge
 * @version 1.0
 */
public class ClienteController {

    private final ClienteServices clienteServices;
    private final Scanner scanner;

    public ClienteController(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
        this.scanner = new Scanner(System.in);
    }

    // Login: valida usuario y contraseña
    public Cliente iniciarSesion(String username, String password) {
        Cliente cliente = clienteServices.buscarClientePorIdONombre(null, username);
        if (cliente != null && cliente.getContraseña().equals(password)) {
            cliente.registrarAccion(new Accion("Inicio de sesión exitoso"));
            System.out.printf("Bienvenido, %s (%s)%n", cliente.getNombre(), cliente.getRol());
            return cliente;
        } else {
            System.out.println("Credenciales incorrectas. Intente nuevamente.");
            return null;
        }
    }

    // Menú principal según el rol del usuario
    public void mostrarMenu(Cliente usuario) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Ver mi información");
            System.out.println("2. Actualizar mi información");
            System.out.println("3. Ver mi historial de acciones");

            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                System.out.println("4. Crear nuevo usuario");
                System.out.println("5. Buscar usuario");
                System.out.println("6. Eliminar usuario");
            }

            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> verInformacion(usuario);
                case 2 -> actualizarInformacion(usuario);
                case 3 -> mostrarHistorial(usuario);
                case 4 -> {
                    if (usuario.getRol() == Rol.ADMINISTRADOR) crearUsuario(usuario);
                    else System.out.println("No tiene permisos para crear usuarios.");
                }
                case 5 -> {
                    if (usuario.getRol() == Rol.ADMINISTRADOR) buscarUsuario(usuario);
                    else System.out.println("No tiene permisos para buscar usuarios.");
                }
                case 6 -> {
                    if (usuario.getRol() == Rol.ADMINISTRADOR) eliminarUsuario(usuario);
                    else System.out.println("No tiene permisos para eliminar usuarios.");
                }
                case 0 -> {
                    usuario.registrarAccion(new Accion("Cerró sesión"));
                    salir = true;
                    System.out.println("Sesión cerrada correctamente.");
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    // Ver información del usuario logueado
    private void verInformacion(Cliente usuario) {
        System.out.println("\n===== MI INFORMACIÓN =====");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Usuario: " + usuario.getNombreUsuario());
        System.out.println("Rol: " + usuario.getRol());
        usuario.registrarAccion(new Accion("Consultó su información de perfil"));
    }

    // Actualizar nombre o contraseña del usuario
    private void actualizarInformacion(Cliente usuario) {
        System.out.println("\n===== ACTUALIZAR INFORMACIÓN =====");
        System.out.print("Nuevo nombre (deje vacío para mantener): ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Contraseña actual: ");
        String actual = scanner.nextLine();
        System.out.print("Nueva contraseña (deje vacío para mantener): ");
        String nueva = scanner.nextLine();

        boolean actualizado = clienteServices.actualizarInformacion(
                usuario.getId(),
                usuario.getNombreUsuario(),
                actual,
                nueva,
                nuevoNombre
        );

        if (actualizado) {
            usuario.registrarAccion(new Accion("Actualizó su información"));
        }else{
            usuario.registrarAccion(new Accion("No puedo actualizar su informacion algo fallo"));
        }
    }

    // Mostrar historial de acciones del usuario
    private void mostrarHistorial(Cliente usuario) {
    System.out.println("\n===== HISTORIAL DE ACCIONES =====");
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    for (Accion accion : usuario.getHistorial().getAcciones()) {
        if (accion != null) {
            String fechaFormateada = accion.getMarcaTiempo().format(formato);
            System.out.printf("[%s] %s%n", fechaFormateada, accion.getDescripcionAccion());
        }
    }
}
    // Crear nuevo usuario (solo para administradores)
    // Crear nuevo usuario (solo para administradores)
    private void crearUsuario(Cliente admin) {
    System.out.println("\n===== CREAR NUEVO USUARIO =====");

    System.out.print("Nombre completo: ");
    String nombre = scanner.nextLine().trim();

    System.out.print("Nombre de usuario: ");
    String username = scanner.nextLine().trim();

    System.out.print("Contraseña: ");
    String password = scanner.nextLine().trim();

    System.out.print("Rol (1=ADMINISTRADOR, 2=ESTÁNDAR): ");
    int opcionRol;
        try {
            opcionRol = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debe ingresar 1 o 2.");
            return;
        }
        
        Rol rol = (opcionRol == 1) ? Rol.ADMINISTRADOR : Rol.ESTANDAR;

        // Llamamos al servicio con los datos, NO con un objeto Cliente
        boolean creado = clienteServices.crearUsuarios(username, nombre, password, rol);

        if (creado) {
            admin.registrarAccion(new Accion("Creó un nuevo usuario: " + username));
            System.out.println("Usuario creado correctamente.");
        } else {
            System.out.println("No se pudo crear el usuario (datos inválidos o sin espacio).");
        }
    }


    // Buscar usuario (solo para administradores)
    private void buscarUsuario(Cliente admin) {
        System.out.println("\n===== BUSCAR USUARIO =====");
        System.out.print("Ingrese ID o nombre de usuario: ");
        String entrada = scanner.nextLine();
        Cliente encontrado;

        try {
            int id = Integer.parseInt(entrada);
            encontrado = clienteServices.buscarClientePorIdONombre(id, null);
        } catch (NumberFormatException e) {
            encontrado = clienteServices.buscarClientePorIdONombre(null, entrada);
        }

        if (encontrado != null) {
            System.out.printf("✅ Usuario encontrado: %s (%s)%n", encontrado.getNombre(), encontrado.getRol());
            admin.registrarAccion(new Accion("Buscó al usuario: " + encontrado.getNombreUsuario()));
        } else {
            System.out.println("No se encontró ningún usuario con los datos ingresados.");
        }
    }

    // Eliminar usuario (solo para administradores)
    private void eliminarUsuario(Cliente admin) {
        System.out.println("\n===== ELIMINAR USUARIO =====");
        System.out.print("Ingrese ID o nombre de usuario: ");
        String entrada = scanner.nextLine();
        boolean eliminado;

        try {
            int id = Integer.parseInt(entrada);
            eliminado = clienteServices.eliminarCliente(id, null);
        } catch (NumberFormatException e) {
            eliminado = clienteServices.eliminarCliente(null, entrada);
        }

        if (eliminado) {
            admin.registrarAccion(new Accion("Eliminó un usuario"));
        }
    }
}
