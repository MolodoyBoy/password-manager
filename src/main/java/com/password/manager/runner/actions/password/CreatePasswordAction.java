package com.password.manager.runner.actions.password;

import com.password.manager.exception.InvalidBundledException;
import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordSource;
import com.password.manager.runner.Command;
import com.password.manager.user.UserSignInChecker;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.password.manager.runner.Command.CREATE_PASSWORD;
import static java.lang.String.format;

@Component
public class CreatePasswordAction extends ManagePasswordAction {

    private final PasswordSource passwordSource;

    public CreatePasswordAction(Scanner scanner,
                                PasswordSource passwordSource,
                                UserSignInChecker userSignInChecker) {
        super(scanner, userSignInChecker);
        this.passwordSource = passwordSource;
    }

    @Override
    public void manage(PasswordBundle passwordBundle) {
        boolean created = passwordSource.createPassword(passwordBundle);
        if (!created) throw new InvalidBundledException(format("Bundle with name %s already exists.", passwordBundle.bundle()));

        System.out.println("Password bundle created successfully.");
    }

    @Override
    public Command getSupportedCommand() {
        return CREATE_PASSWORD;
    }
}