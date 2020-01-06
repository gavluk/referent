package ua.com.gavluk.referent;

import ua.com.gavluk.commons.security.SuperAdminCredentials;

public class ReferentSuperAdminCredentials extends SuperAdminCredentials {

    @Override
    public Object getSubjectIdentifier() {
        return SuperAdminUser.USERNAME;
    }
}
