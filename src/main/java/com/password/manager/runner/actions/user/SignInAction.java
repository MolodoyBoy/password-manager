package com.password.manager.runner.actions.user;

import com.password.manager.exception.InvalidCredentialsException;
import com.password.manager.runner.Command;
import com.password.manager.runner.InputReader;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.User;
import com.password.manager.user.UserSession;
import com.password.manager.user.UserSignInChecker;
import com.password.manager.user.UserSource;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.SIGN_IN;

@Component
public class SignInAction implements Action {

    private final UserSource userSource;
    private final InputReader inputReader;
    private final UserSession userSession;
    private final UserSignInChecker userSignInChecker;

    public SignInAction(UserSource userSource,
                        InputReader inputReader,
                        UserSession userSession,
                        UserSignInChecker userSignInChecker) {
        this.userSource = userSource;
        this.inputReader = inputReader;
        this.userSession = userSession;
        this.userSignInChecker = userSignInChecker;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignOut();

        System.out.println("Enter your username: ");
        String username = inputReader.read();
        System.out.println("Enter your password: ");
        String password = inputReader.read();

        User user = new User(username, password);
        boolean exists = userSource.existUser(user);
        if (!exists) {
            throw new InvalidCredentialsException();
        }

        System.out.println("Sign in successfully.");
        userSession.startSession(user);
    }

    @Override
    public Command getSupportedCommand() {
        return SIGN_IN;
    }
}
