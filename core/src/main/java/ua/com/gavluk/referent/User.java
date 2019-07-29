package ua.com.gavluk.referent;

import java.util.UUID;

public class User {

    private final String username;
    private UUID id;

    User(String username) {
        this.username = username;
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return username;
    }
}
