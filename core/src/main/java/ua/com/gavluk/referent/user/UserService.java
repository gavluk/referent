package ua.com.gavluk.referent.user;

import ua.com.gavluk.commons.EnvironmentContext;
import ua.com.gavluk.commons.security.Credentials;
import ua.com.gavluk.referent.ServiceOnBehalf;

import java.util.Optional;
import java.util.UUID;

public class UserService extends ServiceOnBehalf {

    public UserService(User owner, EnvironmentContext context) {
        super(owner, context);
    }

    public User createUser(String username) {
        // todo: implement user creation with persistence
        return new User(username);
    }

    public void addAuthentication(User user, Credentials credentials) {
        // todo: store authenticate credentials to be allowed to authenticate user
    }

    public Optional<User> findById(UUID id) {
        // todo: implement this
        return Optional.ofNullable(null);
    }
}
