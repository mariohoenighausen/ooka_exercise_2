package org.ookauebung2.cli.command_impls;

import org.ookauebung2.cli.CliCommand;
import org.ookauebung2.re.REComponent;
import org.ookauebung2.re.ComponentAssembler;
import org.ookauebung2.re.exceptions.ComponentNotLoadedInRE;

import java.io.IOException;

/**
 * A command implementation for unloading a component which was previously loaded from the runtime environment
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class UnloadComponent implements CliCommand {
    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the UnloadComponent command
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public UnloadComponent(ComponentAssembler componentAssembler){
        this.componentAssembler = componentAssembler;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] params) {
        String version = null;
        try {
            if (params.length < 1) {
                System.out.println("Command signature mismatch");
                System.out.println("The expected signature for the command is: unloadComponent <component-name> [<component-version>]");
                return;
            }
            if (params.length > 1) version = params[1];
            REComponent component = componentAssembler.unloadComponentFromRE(params[0], version);
            System.out.println("REComponent " + component.getComponentName() + " version " + component.getComponentVersion() + " was removed");
        }
        catch (Exception | ComponentNotLoadedInRE e) {
            throw new RuntimeException(e);
        }
    }
}
