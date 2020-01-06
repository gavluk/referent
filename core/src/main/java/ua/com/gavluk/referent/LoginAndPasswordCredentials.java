package ua.com.gavluk.referent;

import ua.com.gavluk.commons.security.Credentials;
import ua.com.gavluk.referent.user.User;

public class LoginAndPasswordCredentials implements Credentials {

    private final String login;
    private final String password;

    public LoginAndPasswordCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginAndPasswordCredentials(User user, String password) {
        this.login = user.getUsername();
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    String getPassword() {
        return password;
    }

    /**
     * @return user login
     */
    @Override
    public Object getSubjectIdentifier() {
        return this.getLogin();
    }
}
