package ua.com.gavluk.referent;

import ua.com.gavluk.commons.security.AnonymousCredentials;

public final class ReferentAnonymousCredentials extends AnonymousCredentials {

    @Override
    public Object getSubjectIdentifier() {
        return AnonymousUser.USERNAME;
    }
}
