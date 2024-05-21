package com.password.manager.runner.actions.password;

import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordSource;
import com.password.manager.runner.Command;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.UserSignInChecker;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.password.manager.runner.Command.LIST_PASSWORD;
import static java.lang.String.format;

@Component
public class ListPasswordsAction implements Action {

    private final PasswordSource passwordSource;
    private final UserSignInChecker userSignInChecker;

    public ListPasswordsAction(PasswordSource passwordSource,
                               UserSignInChecker userSignInChecker) {
        this.passwordSource = passwordSource;
        this.userSignInChecker = userSignInChecker;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignIn();

        Map<String, PasswordBundle> userPasswords = passwordSource.getUserPasswords();
        if (userPasswords.isEmpty()) {
            System.out.println("You have not saved passwords.");
            return;
        }

        userPasswords.values().forEach(this::printPassword);
    }

    private void printPassword(PasswordBundle passwordBundle) {
        System.out.println(format("%s : %s", passwordBundle.bundle(), passwordBundle.password()));
    }

    @Override
    public Command getSupportedCommand() {
        return LIST_PASSWORD;
    }
}