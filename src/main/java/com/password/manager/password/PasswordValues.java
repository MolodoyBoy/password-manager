package com.password.manager.password;

public enum PasswordValues {

    DIGITS("0123456789"),
    LOWERCASE("abcdefghijklmnopqrstuvwxyz"),
    UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    SPECIAL_CHARACTERS("!@#$%^&*()-_=+[]{}|;:'\",.<>?/"),
    ALL_CHARACTERS(LOWERCASE.value + UPPERCASE.value + DIGITS.value + SPECIAL_CHARACTERS.value);

    private final String value;

    PasswordValues(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int length() {
        return value.length();
    }
}