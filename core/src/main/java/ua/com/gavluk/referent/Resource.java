package ua.com.gavluk.referent;

public abstract class Resource {
    private final String uri;

    Resource(Domain domain, String uri, String resourceURI) {
        this.uri = resourceURI;
    }
}
