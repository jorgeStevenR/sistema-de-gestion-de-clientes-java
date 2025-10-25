package model;

/**
 * Representa un cliente dentro del sistema de gestión.
 * Cada cliente tiene un ID único autoincremental, información personal,
 * un rol asignado y su propio historial de acciones.
 */
public class Cliente {
    private final int ACCIONESMAXIMASDEUSUARIO =50;
    // --- Atributos estáticos ---
    private static int contadorId = 1; // comienza en 1 y se incrementa automáticamente

    // --- Atributos de instancia ---
    private Integer id;
    private String nombre;
    private String nombreUsuario;
    private String contraseña;
    private Rol rol;
    private HistorialAccion historial;

    /**
     * Constructor principal.
     * Asigna un ID autoincremental automáticamente.
     *
     * @param nombreUsuario nombre de usuario.
     * @param nombre        nombre completo.
     * @param contraseña    contraseña del usuario.
     * @param rol           rol asignado.
     */
    public Cliente(String nombreUsuario, String nombre, String contraseña, Rol rol) {
        this.id = contadorId++; // asigna el ID actual y luego incrementa el contador
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
        this.historial = new HistorialAccion(ACCIONESMAXIMASDEUSUARIO);
    }

    // --- Getters y Setters ---
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public HistorialAccion getHistorial() {
        return historial;
    }

    /** Registra una nueva acción en el historial del cliente. */
    public void registrarAccion(Accion accion) {
        historial.registrarAccion(accion);
    }

    /**
     * Permite reiniciar el contador global de IDs (por ejemplo, para pruebas o
     * reinicios).
     */
    public static void reiniciarContadorId() {
        contadorId = 1;
    }
}
