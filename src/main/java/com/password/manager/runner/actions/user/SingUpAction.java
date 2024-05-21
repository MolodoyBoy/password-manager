package com.password.manager.runner.actions.user;

import com.password.manager.exception.UserCreationException;
import com.password.manager.runner.Command;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.User;
import com.password.manager.user.UserSession;
import com.password.manager.user.UserSignInChecker;
import com.password.manager.user.UserSource;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.password.manager.runner.Command.SIGN_UP;

@Component
public class SingUpAction implements Action {

    private final Scanner scanner;
    private final UserSource userSource;
    private final UserSession userSession;
    private final UserSignInChecker userSignInChecker;

    public SingUpAction(Scanner scanner,
                        UserSource userSource,
                        UserSession userSession,
                        UserSignInChecker userSignInChecker) {
        this.scanner = scanner;
        this.userSource = userSource;
        this.userSession = userSession;
        this.userSignInChecker = userSignInChecker;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignOut();

        System.out.println("Enter username: ");
        String username = scanner.next();

        System.out.println("Enter password: ");
        String password = scanner.next();

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