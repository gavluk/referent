package ua.com.gavluk.commons.security;

/**
 * SuperAdmin user credentials.
 * Inherited class should restrict visibility of constructor to allow create these credentials
 * only in the way it should be created (e.g. strict service method building this credentials)
 */
public abstract class SuperAdminCredentials implements Credentials {

    /**
     * Constructor must be called strictly to avoid any unwilling or unwanted instantiations
     */
    protected SuperAdminCredentials() {
    }
}
