package com.password.manager.runner.actions.user;

import com.password.manager.exception.UserCreationException;
import com.password.manager.runner.Command;
import com.password.manager.runner.InputReader;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.User;
import com.password.manager.user.UserSession;
import com.password.manager.user.UserSignInChecker;
import com.password.manager.user.UserSource;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.SIGN_UP;

@Component
public class SingUpAction implements Action {

    private final UserSource userSource;
    private final InputReader inputReader;
    private final UserSession userSession;
    private final UserSignInChecker userSignInChecker;

    public SingUpAction(UserSource userSource,
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

        System.out.println("Enter username: ");
        String username = inputReader.read();

        System.out.println("Enter password: ");
        String password = inputReader.read();

        saveUser(username, password);
    }

    private void saveUser(String username, String password) {
        User user = new User(username, password);
        boolean created = userSource.createUser(user);
        if (!created) {
            throw new UserCreationException("User with the same username already exists.");
        }

        userSession.startSession(user);

        System.out.println("User successfully created and sign in.");
    }

    @Override
    public Command getSupportedCommand() {
        return SIGN_UP;
    }
}