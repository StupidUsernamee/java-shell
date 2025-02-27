package org.shell;

import org.shell.utility.ParsedData;

import java.io.IOException;

public class Shell {
    
    public static void main(String[] args) throws IOException {
        while (true) {
            String input = InputReader.readInput();

            ParsedData parsedInput = InputParser.parser(input);

            ExecuteCommand.execute(parsedInput.command, parsedInput.params);
        }
    }



}


