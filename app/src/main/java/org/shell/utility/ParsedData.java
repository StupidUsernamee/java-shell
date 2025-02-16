package org.shell.utility;

public class ParsedData {
    public String command;

    public String[] params;

    public ParsedData(String[] params, String command) {
        this.params = params;
        this.command = command;
    }
}
