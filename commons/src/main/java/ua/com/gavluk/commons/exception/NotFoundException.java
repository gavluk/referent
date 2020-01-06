package ua.com.gavluk.commons.exception;

import java.util.UUID;

public class NotFoundException extends Exception {

    private final String type;
    private final String notFoundId;

    public NotFoundException(String entityType, String entityId) {
        this.type = entityType;
        this.notFoundId = entityId;
    }

    public NotFoundException(Class notFoundEntityClass, String notFoundEntityId) {
        this(notFoundEntityClass.getSimpleName(), notFoundEntityId);
    }

    public NotFoundException(Class notFoundEntityClass, UUID notFoundEntityId) {
        this(notFoundEntityClass.getSimpleName(), notFoundEntityId.toString());
    }

}
