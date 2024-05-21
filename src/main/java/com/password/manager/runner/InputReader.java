package com.password.manager.runner;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class InputReader {

    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public String read() {
        return scanner.next();
    }

    @PreDestroy
    void close() {
        scanner.close();
    }
}