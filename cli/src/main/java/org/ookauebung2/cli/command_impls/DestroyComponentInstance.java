package org.ookauebung2.cli.command_impls;

import org.ookauebung2.cli.CliCommand;
import org.ookauebung2.re.ComponentAssembler;
import org.ookauebung2.re.exceptions.ComponentInstanceNotFoundInRE;

import java.io.IOException;

/**
 * A command implementation for destroying a component instance e.g. removing an instance from the runtime environment
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class DestroyComponentInstance implements CliCommand {
    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the DestroyComponentInstance command
     * @param assembler A reference to a ComponentAssembler
     */
    public DestroyComponentInstance(ComponentAssembler assembler) {
        this.componentAssembler = assembler;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] params) {
        try {
            if (params.length < 1) {
                System.out.println("Command signature mismatch!");
                System.out.println("The expected signature for the command is: destroyComponentInstance <componentInstance-id>");
                return;
            }
            componentAssembler.destroyComponentInstance(Long.parseLong(params[0]));
            System.out.println("The component-instance with the ID " + params[0] + " will be destroyed!");
        } catch (ComponentInstanceNotFoundInRE | IOException e) {
            System.err.println("The component-instance with the ID " + params[0] + "couldn't be found!");
        }
    }
}
