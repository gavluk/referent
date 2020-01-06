package ua.com.gavluk.commons.exception;

import ua.com.gavluk.commons.security.Credentials;

public class AuthenticationException extends Exception {

    public AuthenticationException(Credentials creds) {
    }
}
