package com.password.manager.exception;

public class ApplicationExitException extends RuntimeException {

    public ApplicationExitException() {
        super("Password manager application finished.");
    }
}
