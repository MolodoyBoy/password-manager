package com.password.manager.runner;

import com.password.manager.exception.ActionException;
import com.password.manager.runner.actions.Action;
import com.password.manager.exception.ApplicationExitException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;
import static java.util.function.Function.identity;
import static com.password.manager.runner.Command.HELP;

@Component
public class ApplicationRunner {

    private final Scanner scanner;
    private final Map<String, Action> commands;

    public ApplicationRunner(Scanner scanner, Set<Action> actions) {
        this.scanner = scanner;
        this.commands = actions.stream()
                .collect(toMap(action -> action.getSupportedCommand().getValue(), identity()));
    }

    @SuppressWarnings("java:S2189")
    public void run() {
        try {
            while (true) {
                decorate();
            }
        } catch (ApplicationExitException e) {
            System.out.println(e.getMessage());
        }
    }

    private void decorate() {
        try {
            performAction();
        } catch (ActionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void performAction() {
        System.out.println("Waiting for command...");

        String input = scanner.next();
        Action action = commands.get(input);

        if (action != null) action.perform();
        else System.out.println(unknownCommandMessage(input));
    }

    private String unknownCommandMessage(String input) {
        return format("Unknown command: %s. Please use %s command to see list of commands.", input, HELP.getValue());
    }
}