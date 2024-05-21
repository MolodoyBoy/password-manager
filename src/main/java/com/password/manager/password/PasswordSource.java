package com.password.manager.password;

import java.util.Map;

public interface PasswordSource {

    String getBundlePassword(String bundle);

    Map<String, PasswordBundle> getUserPasswords();

    boolean createPassword(PasswordBundle passwordBundle);

    boolean updatePassword(PasswordBundle passwordBundle);
}