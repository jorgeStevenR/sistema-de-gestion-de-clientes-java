package model;

/**
 * Clase que gestiona el historial de acciones de un cliente.
 * Contiene una lista (arreglo) de objetos {@link Accion}.
 */
public class HistorialAccion {
    private Accion[] acciones;
    private int indice = 0;

    /**
     * Constructor del historial.
     *
     * @param maxAcciones cantidad máxima de acciones a almacenar.
     */
    public HistorialAccion(int maxAcciones) {
        this.acciones = new Accion[maxAcciones];
    }

    /**
     * Registra una nueva acción en el historial.
     *
     * @param accion acción a registrar.
     */
    public void registrarAccion(Accion accion) {
        if (indice < acciones.length) {
            acciones[indice++] = accion;
        } else {
            System.out.println("⚠️ Historial lleno. No se pueden registrar más acciones.");
        }
    }

    /**
     * Devuelve todas las acciones registradas.
     *
     * @return arreglo de acciones.
     */
    public Accion[] getAcciones() {
        return acciones;
    }
}
