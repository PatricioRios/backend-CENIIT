
package ar.edu.ceniit.demo.security;

public final class Roles {

    // Roles Compuestos de Alto Nivel
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String ADMIN_USUARIOS = "ADMIN_USUARIOS";
    public static final String ADMIN_RECURSOS = "ADMIN_RECURSOS";
    public static final String USUARIO_COMUN = "USUARIO_COMUN";

    // --- Roles Granulares para Backend Service ---

    // Auth Controller
    public static final String PUT_AUTH_ROLES = "PUT_AUTH_ROLES";
    public static final String GET_AUTH_ROLES = "GET_AUTH_ROLES";
    public static final String POST_AUTH_REGISTER = "POST_AUTH_REGISTER";

    // User Controller
    public static final String GET_USER_BY_UUID = "GET_USER_BY_UUID";
    public static final String PUT_UPDATE_USER = "PUT_UPDATE_USER";
    public static final String DELETE_USER = "DELETE_USER";
    public static final String POST_SEARCH_USERS = "POST_SEARCH_USERS";

    // Reservas Controller
    public static final String GET_RESERVAS_AGENDA = "GET_RESERVAS_AGENDA";
    public static final String POST_CREATE_RESERVA = "POST_CREATE_RESERVA";
    public static final String PATCH_APROBAR_RESERVA = "PATCH_APROBAR_RESERVA";
    public static final String PATCH_CANCELAR_RESERVA = "PATCH_CANCELAR_RESERVA";
    public static final String GET_RESERVA_BY_ID = "GET_RESERVA_BY_ID";
    public static final String POST_SEARCH_RESERVAS = "POST_SEARCH_RESERVAS";


    // --- Roles Granulares para Resources Service ---

    // Recursos Controller
    public static final String POST_RECURSOS = "POST_RECURSOS";
    public static final String DELETE_RECURSO_BY_ID = "DELETE_RECURSO_BY_ID";
    public static final String PATCH_RECURSO_BY_ID = "PATCH_RECURSO_BY_ID";
    public static final String GET_RECURSO_BY_ID = "GET_RECURSO_BY_ID";
    public static final String GET_RECURSOS = "GET_RECURSOS";
    public static final String POST_SEARCH_RECURSOS = "POST_SEARCH_RECURSOS";
    public static final String POST_RECURSO_FOTO = "POST_RECURSO_FOTO";
    public static final String DELETE_RECURSO_FOTO = "DELETE_RECURSO_FOTO";

    private Roles() {
        // Clase de utilidad, no instanciable
    }
}
