package org.ookauebung2.cli.command_impls;

import org.ookauebung2.cli.CliCommand;

public class IllegalCommand implements CliCommand {
    /**
     * @param params: The command as well as additional parameters for the to be executed command
     */
    @Override
    public void execute(String[] params) {
        System.err.println("An invalid command was used!");
    }
}
