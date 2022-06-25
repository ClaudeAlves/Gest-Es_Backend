package dev.claude.service.exception;

import java.io.Serial;

public class WrongCredentialsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public WrongCredentialsException() {
        super("Wrong credentials provided !");
    }

    public WrongCredentialsException(String message) {
        super(message);
    }

}
