package com.password.manager.runner.actions.password;

import com.password.manager.exception.InvalidBundledException;
import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordSource;
import com.password.manager.runner.Command;
import com.password.manager.runner.InputReader;
import com.password.manager.user.UserSignInChecker;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.*;

@Component
public class UpdatePasswordBundleAction extends ManagePasswordAction {

    private final PasswordSource passwordSource;

    public UpdatePasswordBundleAction(InputReader inputReader,
                                      PasswordSource passwordSource,
                                      UserSignInChecker userSignInChecker) {
        super(inputReader, userSignInChecker);
        this.passwordSource = passwordSource;
    }

    @Override
    public void manage(PasswordBundle passwordBundle) {
        boolean updated = passwordSource.updatePassword(passwordBundle);
        if (!updated) throw new InvalidBundledException("Bundle not exists!");

        System.out.println("Bundle successfully updated.");
    }

    @Override
    public Command getSupportedCommand() {
        return UPDATE_PASSWORD;
    }
}
