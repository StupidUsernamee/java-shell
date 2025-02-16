package org.shell;

import org.shell.utility.ParsedData;

import java.util.Arrays;

public class InputParser {

    public static ParsedData parser(String userInput) {
        String[] inputArray = userInput.split(" ");
        String inputCommand = inputArray[0];
        String[] parameterArray = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        ParsedData data = new ParsedData(parameterArray, inputCommand);
        return data;
    }
}
