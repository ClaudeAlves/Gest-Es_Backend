package dev.claude.service.exception;

import java.io.Serial;

public class EntityDoesNotExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityDoesNotExistException(String message) {
        super(message);
    }

    public EntityDoesNotExistException() {
        super("Entity does not exist !");
    }
}
