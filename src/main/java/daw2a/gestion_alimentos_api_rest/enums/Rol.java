package daw2a.gestion_alimentos_api_rest.enums;

/**
 * Enum que define los roles disponibles en el sistema.
 * <p>Los roles determinan los permisos y accesos que tienen los usuarios dentro de la aplicación.</p>
 *
 * <p><b>Roles disponibles:</b></p>
 * <ul>
 *   <li><b>USUARIO:</b> Rol estándar para usuarios regulares. Tiene permisos básicos, como consultar datos.</li>
 *   <li><b>ADMINISTRADOR:</b> Rol avanzado con permisos elevados. Puede gestionar usuarios y realizar configuraciones del sistema.</li>
 * </ul>
 */
public enum Rol {

    /**
     * Rol estándar para usuarios regulares.
     * <p>Los usuarios con este rol tienen permisos básicos, como consultar y gestionar sus propios datos.</p>
     */
    USUARIO,

    /**
     * Rol avanzado para administradores del sistema.
     * <p>Los usuarios con este rol tienen acceso completo, incluyendo la gestión de usuarios y configuraciones globales.</p>
     */
    ADMINISTRADOR
}
