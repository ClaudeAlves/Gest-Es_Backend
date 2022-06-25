package dev.claude.service.exception;

import java.io.Serial;

public class MethodNotAllowedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public MethodNotAllowedException(String message) {
        super(message);
    }

    public MethodNotAllowedException() {
        super("Method is not allowed !");
    }

}
