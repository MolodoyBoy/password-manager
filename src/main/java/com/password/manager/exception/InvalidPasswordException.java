package com.password.manager.exception;

public class InvalidPasswordException extends ActionException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}