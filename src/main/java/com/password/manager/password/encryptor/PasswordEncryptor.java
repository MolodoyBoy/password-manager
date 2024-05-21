package com.password.manager.password.encryptor;

public interface PasswordEncryptor {

    String encrypt(String plaintext);

    String decrypt(String ciphertext);
}
