package org.shell;

import org.shell.utility.ParsedData;

public class Shell {
    
    public static void main(String[] args) {
        while (true) {
            String input = InputReader.readInput();

            ParsedData parsedInput = InputParser.parser(input);

            ExecuteCommand.execute(parsedInput.command, parsedInput.params);
        }
    }



}


