package ua.com.gavluk.referent.user.creds;

import java.util.Optional;

public interface CredentialsRecordRepository {

    Optional<CredentialsRecord> findOneByTypeAndLoginAndActive(String type, String login, boolean active);
}
