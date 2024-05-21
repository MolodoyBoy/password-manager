package com.password.manager.runner.actions;

import com.password.manager.runner.Command;
import org.springframework.stereotype.Component;

import static com.password.manager.runner.Command.HELP;
import static java.lang.String.format;
import static java.util.Arrays.stream;

@Component
public class HelpAction implements Action {

    @Override
    public void perform() {
        stream(Command.values()).forEach(this::printCommand);
    }

    private void printCommand(Command command) {
        System.out.println(format("%s : %s", command.getValue(), command.getDescription()));
    }

    @Override
    public Command getSupportedCommand() {
        return HELP;
    }
}