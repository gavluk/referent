package ua.com.gavluk.referent.user;

import java.util.UUID;

public class User {

    private UUID id;
    private final String username;

    protected User(String username) {
        this.username = username;
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }
}
