package org.shell;

import java.util.Scanner;

public class InputReader {

    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("$ ");
        String input = scanner.nextLine();
        return input;
    }
}
