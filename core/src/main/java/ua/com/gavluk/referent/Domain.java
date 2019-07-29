package ua.com.gavluk.referent;

import java.util.UUID;

public class Domain {

    private final UUID userId;
    private final String uri;
    private final String title;

    Domain(User owner, String domainURI, String domainTitle) {
        this.userId = owner.getId();
        this.uri = domainURI;
        this.title = domainTitle;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }
}
