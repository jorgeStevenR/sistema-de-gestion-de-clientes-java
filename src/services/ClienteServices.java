package services;

import javax.swing.text.StyledEditorKit.BoldAction;

/**
 * Clase de servicio encargada de gestionar las operaciones relacionadas con los clientes.
 * <p>
 * Permite crear nuevos usuarios, buscar clientes por su ID o nombre de usuario, 
 * y actualizar información como el nombre completo o la contraseña de un cliente existente.
 * </p>
 *
 * <h3>Notas importantes:</h3>
 * <ul>
 *     <li>La clase usa un arreglo de tipo {@link Cliente} para almacenar los usuarios.</li>
 *     <li>Los métodos actúan directamente sobre las referencias de los objetos contenidos en el arreglo,
 *     por lo tanto, cualquier modificación realizada sobre un cliente se refleja automáticamente en el arreglo.</li>
 * </ul>
 *
 * @author Jorge
 * @version 1.0
 */


import model.Cliente;
import model.Rol;

public class ClienteServices {

    /** Arreglo que contiene los clientes registrados en el sistema. */
    private  Cliente[] clientes ;

    public ClienteServices(int tamaño) {
        this.clientes = new Cliente[tamaño];
    }

    /**
     * Crea un nuevo usuario en el primer espacio disponible del arreglo de clientes.
     *
     * @param usuario objeto {@link Cliente} que se desea registrar.
     * @return {@code true} si el usuario fue agregado correctamente,
     *         {@code false} si el arreglo está lleno o el objeto es nulo.
     */
    public Boolean crearUsuarios(String nombreUsuario, String nombre, String contraseña, Rol rol){
        if(nombreUsuario==null && nombre ==null && contraseña==null && rol==null)return false;
        Cliente usuario;
        if(!nombreUsuario.trim().isEmpty() &&
            !nombre.trim().isEmpty() && 
            !contraseña.trim().isEmpty() && 
            (rol == Rol.ADMINISTRADOR || rol == Rol.ESTANDAR)){
                for (int i = 0; i < clientes.length; i++) {
                    if(clientes[i] == null){
                        clientes[i] = usuario = new Cliente( nombreUsuario,  nombre,  contraseña, rol);;
                        System.out.println("Cliente creado correctamente");
                        return true;
                    }
                }
        System.out.println("No hay espacio para mas clientes");
        return false;
        }
        return false;

        
    }


     /**
     * Busca un cliente en el arreglo según su ID o nombre de usuario.
     *
     * @param id             identificador único del cliente (puede ser {@code null} si se busca por nombre).
     * @param nombreUsuario  nombre de usuario del cliente (puede ser {@code null} si se busca por ID).
     * @return el objeto {@link Cliente} si se encuentra una coincidencia, o {@code null} si no existe.
     */
    public Cliente buscarClientePorIdONombre(Integer id,String nombreUsuario){
        if((id==null || id<0)&&(nombreUsuario==null || nombreUsuario.isEmpty())){
            System.out.println("Debe proporcionar un id o un nombre valido"); 
            return null;
        }
        
        for (int i = 0; i < clientes.length; i++) {
            if(clientes[i] !=null){
                boolean coincidenciaId = (id!=null && clientes[i].getId().equals(id));
                boolean coincidenciaNombreUsuario =(nombreUsuario!=null && clientes[i].getNombreUsuario().equalsIgnoreCase(nombreUsuario));
                
                if(coincidenciaId || coincidenciaNombreUsuario){
                    return clientes[i];
                }
            }
        }
        System.out.println("No se econtro cliente con los datos proporcinados");
        return null;
    }


    /**
     * Actualiza la información de un cliente existente, permitiendo cambiar su nombre completo
     * y/o su contraseña (solo si la contraseña actual proporcionada es correcta).
     *
     * <p>La búsqueda del cliente se realiza por ID o nombre de usuario.
     * Si alguno de los campos es inválido, no se realiza ninguna modificación.</p>
     *
     * @param id                identificador único del cliente.
     * @param nombreUsuario     nombre de usuario (opcional si se usa ID).
     * @param contraseñaActual  contraseña actual del cliente (obligatoria para cambiar la contraseña).
     * @param contraseñaNueva   nueva contraseña (solo se cambia si coincide la actual).
     * @param nuevoNombre       nuevo nombre completo (se cambia si es diferente al actual).
     * @return {@code true} si al menos un campo fue actualizado correctamente, 
     *         {@code false} si no se realizó ningún cambio.
     */
    public boolean actualizarInformacion(Integer id,String nombreUsuario, String contraseñaAcual, String contraseñaNueva, String nuevoNombre){
        if((id==null || id<0)&&(nombreUsuario==null || nombreUsuario.isEmpty())){
            System.out.println("Debe proporcionar un id o un nombre valido");
            return false;
        }
        Cliente cliente = buscarClientePorIdONombre(id, nombreUsuario);
        if(cliente ==null){
            System.out.println("No se encontro cliente con el ID proporcionado");
            return false;
        }

        boolean acutalizado = false;
        boolean actualizadoNombre = false;
        boolean actualizadoContraseña =false;

        // --- Actualización del nombre ---
        if(nuevoNombre != null && !nuevoNombre.trim().isEmpty()){ // el .trim() es para quitar los espacios en blanco al principio y al final y es isEmpty es para ver si esta vacio
            if(!cliente.getNombre().equalsIgnoreCase(nuevoNombre.trim())){
                 cliente.setNombre(nuevoNombre.trim());
                 actualizadoNombre = true;
            }
        }else{
             System.out.println("ℹEl nuevo nombre es igual al actual, no se realizó ningún cambio.");
             actualizadoNombre = false;
        }

        // --- Actualización de la contraseña ---
        if(contraseñaNueva!=null && !contraseñaNueva.trim().isEmpty()){
            if(contraseñaAcual !=null && cliente.getContraseña().equals(contraseñaAcual)){
                if(!cliente.getContraseña().equals(contraseñaNueva)){
                    cliente.setContraseña(contraseñaNueva);
                    actualizadoContraseña = true;
                    
                }else{
                    System.out.println("la nueva contraseña es igual a la actual, no se realizo ningun cambio");
                }
            }else{
                System.out.println("La contraseña actual no es correcta. No se actualizó la contraseña.");
            }
        }

        acutalizado = actualizadoContraseña || actualizadoNombre;

        // --- Mensajes finales ---
        if(acutalizado){
            System.out.println("Cliente actualizado correctamente.");
            if(actualizadoNombre){
                System.out.printf("El nombre se ha actualizado correctamente : %s%n",cliente.getNombre());
            }else{
                System.out.println("El nombre no se pudo actualizar o no fue modificado.");
            }

            if(actualizadoContraseña){
                System.out.println("La contraseña se actualizo correctamente.");
            }else{
                System.out.println("   - La contraseña no se pudo actualizar o no fue modificada.");
            }
        }else{
            System.out.println("No se realizó ningún cambio en los datos del cliente.");         
        }

        return acutalizado;
    }

    /**
     * Elimina un cliente existente del arreglo de clientes, buscando por su ID o nombre de usuario.
     * <p>
     * Si el cliente se encuentra, su posición en el arreglo se establece en {@code null},
     * liberando así el espacio para futuros registros.
     * </p>
     *
     * @param id             identificador único del cliente a eliminar (puede ser {@code null} si se busca por nombre).
     * @param nombreUsuario  nombre de usuario del cliente a eliminar (puede ser {@code null} si se usa el ID).
     * @return {@code true} si el cliente fue eliminado correctamente,
     *         {@code false} si no se encontró o los parámetros son inválidos.
     */
    public boolean eliminarCliente(Integer id, String nombreUsuario) {
        // Validar que al menos un criterio sea válido
        if ((id == null || id < 0) && (nombreUsuario == null || nombreUsuario.trim().isEmpty())) {
            System.out.println("Debe proporcionar un ID o un nombre de usuario válido para eliminar un cliente.");
            return false;
        }

        // Recorrer el arreglo en busca del cliente
        for (int i = 0; i < clientes.length; i++) {
            if (clientes[i] != null) {
                boolean coincideId = (id != null && clientes[i].getId().equals(id));
                boolean coincideNombre = (nombreUsuario != null &&
                        clientes[i].getNombreUsuario().equalsIgnoreCase(nombreUsuario));

                // Si se encuentra coincidencia, eliminar
                if (coincideId || coincideNombre) {
                    System.out.printf("Cliente eliminado correctamente: %s%n", clientes[i].getNombre());
                    clientes[i] = null; // libera la posición del arreglo
                    return true;
                }
            }
        }

        System.out.println("No se encontró ningún cliente con los datos proporcionados.");
        return false;
    }


    
}
