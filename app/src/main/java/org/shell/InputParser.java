package org.shell;

import org.shell.utility.Tuple;

import java.util.Arrays;

public class InputParser {

    public static Tuple parser(String userInput) {
        String[] inputArray = userInput.split(" ");
        String inputCommand = inputArray[0];
        String[] parameterArray = Arrays.copyOfRange(inputArray, 1, inputArray.length);
        Tuple data = new Tuple(parameterArray, inputCommand);
        return data;
    }
}
