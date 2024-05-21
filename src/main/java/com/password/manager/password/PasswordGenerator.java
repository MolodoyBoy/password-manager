package com.password.manager.password;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

import static com.password.manager.password.PasswordValues.*;

@Component
public class PasswordGenerator {

    private static final int PASSWORD_LENGTH = 16;

    private final SecureRandom secureRandom;

    public PasswordGenerator() {
        this.secureRandom = new SecureRandom();
    }

    public String generateStrongPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        password.append(LOWERCASE.getValue().charAt(secureRandom.nextInt(LOWERCASE.length())));
        password.append(UPPERCASE.getValue().charAt(secureRandom.nextInt(UPPERCASE.length())));
        password.append(DIGITS.getValue().charAt(secureRandom.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARACTERS.getValue().charAt(secureRandom.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(ALL_CHARACTERS.getValue().charAt(secureRandom.nextInt(ALL_CHARACTERS.length())));
        }

        return shuffle(password.toString());
    }

    private String shuffle(String password) {
        char[] passwordChars = password.toCharArray();
        for (int i = passwordChars.length - 1; i > 0; i--) {
            int j = secureRandom.nextInt(i + 1);

            char temp = passwordChars[i];
            passwordChars[i] = passwordChars[j];
            passwordChars[j] = temp;
        }

        return new String(passwordChars);
    }
}