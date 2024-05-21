package com.password.manager.runner.actions.password;

import com.password.manager.exception.InvalidPasswordException;
import com.password.manager.password.PasswordBundle;
import com.password.manager.password.PasswordValues;
import com.password.manager.runner.InputReader;
import com.password.manager.runner.actions.Action;
import com.password.manager.user.UserSignInChecker;

import static com.password.manager.password.PasswordValues.DIGITS;
import static com.password.manager.password.PasswordValues.UPPERCASE;
import static java.lang.String.format;

public abstract class ManagePasswordAction implements Action {

    private static final int MIN_PASSWORD_LENGTH = 10;
    private static final int MAX_PASSWORD_LENGTH = 50;

    private final InputReader inputReader;
    private final UserSignInChecker userSignInChecker;

    protected ManagePasswordAction(InputReader inputReader, UserSignInChecker userSignInChecker) {
        this.inputReader = inputReader;
        this.userSignInChecker = userSignInChecker;
    }

    @Override
    public void perform() {
        userSignInChecker.checkSignIn();

        System.out.println("Enter bundle name: ");
        String bundle = inputReader.read();
        System.out.println("Enter password: ");
        String password = inputReader.read();
        isPasswordValid(password);

        manage(new PasswordBundle(bundle, password));
    }

    protected abstract void manage(PasswordBundle passwordBundle);

    protected void isPasswordValid(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) throw new InvalidPasswordException(
                format("Password length should be at least %d symbols.", MIN_PASSWORD_LENGTH)
        );

        if (password.length() > MAX_PASSWORD_LENGTH) throw new InvalidPasswordException(
                format("Password length should be at most %d symbols.", MAX_PASSWORD_LENGTH)
        );

        if (!containsPattern(password, DIGITS)) throw new InvalidPasswordException(
                "Password must contain at least one digit."
        );

        if (!containsPattern(password, UPPERCASE)) throw new InvalidPasswordException(
                "Password must contain at least one upper case letter."
        );
    }

    private boolean containsPattern(String password, PasswordValues values) {
        for (char upperCase: values.getValue().toCharArray()) {
            if (password.indexOf(upperCase) != -1) {
                return true;
            }
        }

        return false;
    }
}