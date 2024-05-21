package com.password.manager.runner;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerHolder {

    private final Scanner scanner;

    public ScannerHolder() {
        this.scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }
}