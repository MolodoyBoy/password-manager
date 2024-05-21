package com.password.manager.exception;

public abstract class ActionException extends RuntimeException {

    protected ActionException(String message) {
        super(message);
    }
}