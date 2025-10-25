package model;

/**
 * Representa una acción realizada por un cliente en el sistema.
 */
public class Accion {
    private String descripcionAccion;
    private long marcaTiempo;

    /**
     * Constructor de la acción.
     *
     * @param descripcionAccion descripción breve de la acción realizada.
     */
    public Accion(String descripcionAccion) {
        this.descripcionAccion = descripcionAccion;
        this.marcaTiempo = System.currentTimeMillis();
    }

    public String getDescripcionAccion() {
        return descripcionAccion;
    }

    public long getMarcaTiempo() {
        return marcaTiempo;
    }

    @Override
    public String toString() {
        return "[" + marcaTiempo + "] " + descripcionAccion;
    }
}
