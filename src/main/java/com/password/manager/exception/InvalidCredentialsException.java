package com.password.manager.exception;

public class InvalidCredentialsException extends ActionException {

    public InvalidCredentialsException() {
        super("Invalid username or password. Try again.");
    }
}
