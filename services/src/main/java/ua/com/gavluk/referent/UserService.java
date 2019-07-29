package ua.com.gavluk.referent;

import ua.com.gavluk.commons.EnvironmentContext;

public class UserService {
    public User createUser(String username) {
        // todo: implement user creation with persistance
        return new User(username);
    }

    public void addAuthentication(User user, Credentials credentials) {
        // todo: store authenticate credentials to be allowed to authenticate user
    }

    public ResourceService buildResourceServiceFor(Credentials activeUserCredentials, EnvironmentContext context) {
        if (activeUserCredentials instanceof AnonymousCredentials)
            return new ResourceService(new AnonymousUser(), context);

        // todo: check credentials and if ok, build ResourceService
        LoginAndPasswordCredentials loginAndPassword = (LoginAndPasswordCredentials) activeUserCredentials;
        return new ResourceService(new User(loginAndPassword.getLogin()), context);
    }

    public User createAnonymous() {
        return new AnonymousUser();
    }
}
