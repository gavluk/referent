package ua.com.gavluk.referent.user.creds;

import ua.com.gavluk.commons.security.CredentialsChecker;

public class CredentialsCheckerFactory {

    public CredentialsChecker buildCheckerFor(CredentialsRecord credsRecord) {

        if (CredentialsRecord.TYPE_LOGIN_AND_PASSWORD.equals(credsRecord.getType())) {
            return new LoginAndPasswordCredentialsChecker(credsRecord.getSecretHash());
        }

        // todo: add other credentials checkers (Oauth, JWT, etc)

        throw new IllegalArgumentException("Credentials of type " + credsRecord.getType() + " not supported");
    }

}
