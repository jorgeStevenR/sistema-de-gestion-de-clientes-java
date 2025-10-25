package model;
import java.time.LocalDateTime;

/**
 * Representa una acción realizada por un cliente en el sistema.
 */
public class Accion {
    private String descripcionAccion;
    private LocalDateTime marcaTiempo;

    /**
     * Constructor de la acción.
     *
     * @param descripcionAccion descripción breve de la acción realizada.
     */
    public Accion(String descripcionAccion) {
        this.descripcionAccion = descripcionAccion;
        this.marcaTiempo =LocalDateTime.now();
    }

    public String getDescripcionAccion() {
        return descripcionAccion;
    }

    public LocalDateTime getMarcaTiempo() {
        return marcaTiempo;
    }

    @Override
    public String toString() {
        return "[" + marcaTiempo + "] " + descripcionAccion;
    }
}
