package org.shell.utility;

public class Tuple {
    public String command;

    public String[] params;

    public Tuple(String[] params, String command) {
        this.params = params;
        this.command = command;
    }
}
