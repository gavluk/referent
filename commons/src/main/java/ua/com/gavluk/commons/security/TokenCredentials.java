package ua.com.gavluk.commons.security;

public abstract class TokenCredentials implements Credentials {

    private final String token;

    TokenCredentials(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
