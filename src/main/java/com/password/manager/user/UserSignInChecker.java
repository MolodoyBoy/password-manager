package com.password.manager.user;

import com.password.manager.exception.UserSignInException;
import org.springframework.stereotype.Component;

@Component
public class UserSignInChecker {

    private final UserSession userSession;

    public UserSignInChecker(UserSession userSession) {
        this.userSession = userSession;
    }

    public void checkSignIn() {
        if (!userSession.isSessionStarted()) {
            throw new UserSignInException("Please sign in.");
        }
    }

    public void checkSignOut() {
        if (userSession.isSessionStarted()) {
            throw new UserSignInException("Please sign out.");
        }
    }
}