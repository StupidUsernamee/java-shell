package org.shell;

import org.shell.utility.Tuple;

public class Shell {
    
    public static void main(String[] args) {
        while (true) {
            String input = InputReader.readInput();

            Tuple parsedInput = InputParser.parser(input);

            ExecuteCommand.execute(parsedInput.command, parsedInput.params);
        }
    }



}


