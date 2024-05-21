package com.password.manager.password.encryptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class XORCipherPasswordEncryptor implements PasswordEncryptor {

    private final String key;

    public XORCipherPasswordEncryptor(@Value("${password.manager.encryption.key}") String key) {
        this.key = key;
    }

    @Override
    public String encrypt(String text) {
        if (text == null) return null;

        StringBuilder encrypted = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char plainChar = text.charAt(i);
            char keyChar = key.charAt(i % key.length());

            char encryptedChar = (char) (plainChar ^ keyChar);
            encrypted.append(encryptedChar);
        }
        return encrypted.toString();
    }

    @Override
    public String decrypt(String text) {
        if (text == null) return null;

        return encrypt(text);
    }
}