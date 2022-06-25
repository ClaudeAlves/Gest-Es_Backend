package dev.claude.service.exception;

import java.io.Serial;

public class UnknownEntityException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UnknownEntityException(String message) {
        super(message);
    }

    public UnknownEntityException() {
        super("This entity doesn't exist !");
    }

}
