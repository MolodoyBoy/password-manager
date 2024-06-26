package com.password.manager.runner.actions.password;

import com.password.manager.exception.PasswordNotFoundException;
import com.password.manager.password.PasswordSource;
import com.password.manager.runner.Command;
import com.password.manager.runner.InputReader;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.UserSignInChecker;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.GET_PASSWORD;
import static java.lang.String.format;

@Component
public class GetPasswordAction implements Action {

    private final InputReader inputReader;
    private final PasswordSource passwordSource;
    private final UserSignInChecker userSignInChecker;

    public GetPasswordAction(InputReader inputReader,
                             PasswordSource passwordSource,
                             UserSignInChecker userSignInChecker) {
        this.inputReader = inputReader;
        this.passwordSource = passwordSource;
        this.userSignInChecker = userSignInChecker;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignIn();

        System.out.println("Enter bundle name: ");
        String bundle = inputReader.read();

        String password = passwordSource.getBundlePassword(bundle);
        if (password == null) {
            throw new PasswordNotFoundException(format("Password for bundle %s not found.", bundle));
        }

        System.out.println(format("Password: %s", password));
    }

    @Override
    public Command getSupportedCommand() {
        return GET_PASSWORD;
    }
}