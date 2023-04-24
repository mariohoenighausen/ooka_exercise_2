package org.ookauebung2.cli.command_impls;

import org.ookauebung2.cli.CliCommand;
import org.ookauebung2.re.REComponent;
import org.ookauebung2.re.ComponentAssembler;
import org.ookauebung2.re.exceptions.ComponentAlreadyLoadedInRE;
import org.ookauebung2.re.exceptions.NoAnnotatedMethodPresentOnComponentInstance;

/**
 * A class for loading Components into the runtime environment
 * @version 1.0
 * @author mariohoenighausen
 */
public class LoadComponent implements CliCommand {

    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the LoadComponent
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public LoadComponent(ComponentAssembler componentAssembler){
        this.componentAssembler = componentAssembler;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] params) {
        try{
            if(params.length < 1){
                System.out.println("Command signature mismatch!");
                System.out.println("The expected signature for the command is: loadComponent <jar-file-path>");
                return;
            }
            REComponent component = this.componentAssembler.loadComponentIntoRE(params[0]);
            System.out.println("The Component: " + component.getComponentName() + " in version: " + component.getComponentVersion() + ", was loaded into the runtime environment");
        }
        catch (Exception ex){
            System.err.println();
        } catch (ComponentAlreadyLoadedInRE | NoAnnotatedMethodPresentOnComponentInstance e) {
            throw new RuntimeException(e);
        }
    }
}
