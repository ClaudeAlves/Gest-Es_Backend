package dev.claude.service.exception;

import java.io.Serial;

public class IncompleteBodyException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public IncompleteBodyException(String message) {
        super(message);
    }

    public IncompleteBodyException() {
        super("The provided payload body was incomplete !");
    }
}
