package com.password.manager.runner;

public enum Command {

    EXIT("exit", "Finishes application."),
    HELP("help", "Lists all commands."),
    SIGN_OUT("sign_out", "Sign out user from application."),
    SIGN_IN("sign_in", "Consumes username and password, after login to application."),
    SIGN_UP("sign_up", "Consumes username and password, after register user to application."),
    GET_PASSWORD("get", "Gets a password for a given bundle."),
    LIST_PASSWORD("list", "Lists all saved password bundles."),
    GENERATE_PASSWORD("generate", "Generates a new, strong password."),
    UPDATE_PASSWORD("update", "Updates password for specified bundle."),
    CREATE_PASSWORD("create", "Saves a new password with specified bundle.");

    private final String value;
    private final String description;

    Command(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}