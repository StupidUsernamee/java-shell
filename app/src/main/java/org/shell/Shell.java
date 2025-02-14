package app.src.main.java.org.shell;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shell {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        List<String> commandList = new ArrayList<>();
        commandList.add("exit");
        commandList.add("echo");

        while (true) {
            System.out.print("$ ");
            input = scanner.nextLine();

            String[] inputArray = input.split(" ");
            String inputCommand = inputArray[0];
            inputArray[0] = "";
            String parameter = "";
            StringBuilder stringBuilder = new StringBuilder();
            for (String s: inputArray) {
                stringBuilder.append(s).append(" ");
            }
            parameter = String.valueOf(stringBuilder).trim();

            if (inputCommand.equals(commandList.get(0)) && parameter.equals("0")) {
                System.exit(0);
            }
            else if (inputCommand.equals(commandList.get(1))) {
                System.out.println(parameter);
            }

            else {
                notFoundError(inputCommand);
            }
        }
    }


    private static void notFoundError(String s) {
        System.out.println(MessageFormat.format("Jshell: command not found: {0}", s));
    }
}


