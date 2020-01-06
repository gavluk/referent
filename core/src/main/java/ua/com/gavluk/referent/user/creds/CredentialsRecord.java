package ua.com.gavluk.referent.user.creds;

import java.util.UUID;

/**
 * Super-class for any kind of stored user credentials
 */
public class CredentialsRecord {
    public static final String TYPE_LOGIN_AND_PASSWORD = "login_and_password";
    private UUID userId;
    private String type;
    private String secretHash;

    public UUID getUserId() {
        return this.userId;
    }

    public String getType() {
        return this.type;
    }

    public String getSecretHash() {
        return this.secretHash;
    }
}
