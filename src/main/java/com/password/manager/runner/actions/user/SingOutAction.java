package com.password.manager.runner.actions.user;

import com.password.manager.runner.Command;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.UserSession;
import com.password.manager.user.UserSignInChecker;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.SIGN_OUT;

@Component
public class SingOutAction implements Action {

    private final UserSession userSession;
    private final UserSignInChecker userSignInChecker;

    public SingOutAction(UserSession userSession, UserSignInChecker userSignInChecker) {
        this.userSession = userSession;
        this.userSignInChecker = userSignInChecker;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignIn();

        userSession.endSession();
        System.out.println("User sign out.");
    }

    @Override
    public Command getSupportedCommand() {
        return SIGN_OUT;
    }
}