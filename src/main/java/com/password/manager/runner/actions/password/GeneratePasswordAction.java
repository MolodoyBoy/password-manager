package com.password.manager.runner.actions.password;

import com.password.manager.password.PasswordGenerator;
import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordSource;
import com.password.manager.runner.Command;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.UserSignInChecker;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.lang.String.format;
import static com.password.manager.runner.Command.GENERATE_PASSWORD;

@Component
public class GeneratePasswordAction implements Action {

    private final PasswordSource passwordSource;
    private final UserSignInChecker userSignInChecker;
    private final PasswordGenerator passwordGenerator;

    public GeneratePasswordAction(PasswordSource passwordSource,
                                  UserSignInChecker userSignInChecker,
                                  PasswordGenerator passwordGenerator) {
        this.passwordSource = passwordSource;
        this.userSignInChecker = userSignInChecker;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignIn();

        String password = passwordGenerator.generateStrongPassword();
        Map<String, PasswordBundle> userPasswords = passwordSource.getUserPasswords();
        while (userPasswords.containsKey(password)) {
            password = passwordGenerator.generateStrongPassword();
        }

        System.out.println(format("Password generated: %s", password));
    }

    @Override
    public Command getSupportedCommand() {
        return GENERATE_PASSWORD;
    }
}
