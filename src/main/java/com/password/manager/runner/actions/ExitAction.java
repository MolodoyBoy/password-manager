package com.password.manager.runner.actions;

import com.password.manager.exception.ApplicationExitException;
import com.password.manager.runner.Command;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.EXIT;

@Component
public class ExitAction implements Action {

    @Override
    public void perform() {
        throw new ApplicationExitException();
    }

    @Override
    public Command getSupportedCommand() {
        return EXIT;
    }
}