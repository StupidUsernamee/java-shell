package org.shell;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ExecuteCommand {

    public static void execute(String command, String[] args) {
        List<String> commandList = new ArrayList<>();
        commandList.add("exit");
        commandList.add("echo");

        if (command.equals(commandList.get(0))) {
            try {
                System.exit(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                inValidParameterError(command, args[0]);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else if (command.equals(commandList.get(1))) {
            try {
                for (String s : args) {
                    System.out.print(s + " ");
                }
                System.out.println();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        else {
            notFoundError(command);
        }
    }
    private static void notFoundError(String s) {
        System.out.println(MessageFormat.format("Jshell: command not found: {0}", s));
    }


    private static void inValidParameterError(String command, String parameter) {
        System.out.println(MessageFormat.format("Jshell: {0} has no parameter: {1}", command, parameter));
    }
}
