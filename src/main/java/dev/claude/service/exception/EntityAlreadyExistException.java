package dev.claude.service.exception;

import java.io.Serial;

public class EntityAlreadyExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException() {
        super("Entity already exists !");
    }
}
