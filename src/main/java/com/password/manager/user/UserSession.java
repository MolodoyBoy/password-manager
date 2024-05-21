package com.password.manager.user;

import org.springframework.stereotype.Component;

@Component
public class UserSession {

    private final ThreadLocal<User> currentUser;

    public UserSession() {
        this.currentUser = new ThreadLocal<>();
    }

    public void startSession(User user) {
        currentUser.set(user);
    }

    public boolean isSessionStarted() {
        return currentUser.get() != null;
    }

    public User getSessionUser() {
        return currentUser.get();
    }

    public void endSession() {
        currentUser.remove();
    }
}