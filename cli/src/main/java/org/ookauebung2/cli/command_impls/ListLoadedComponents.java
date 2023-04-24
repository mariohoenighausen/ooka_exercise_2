package org.ookauebung2.cli.command_impls;

import org.ookauebung2.cli.CliCommand;
import org.ookauebung2.re.ComponentAssembler;
import org.ookauebung2.re.REComponent;

public class ListLoadedComponents implements CliCommand {

    /**
     * A reference to a ComponentAssembler
     */
    private final ComponentAssembler componentAssembler;

    /**
     * Constructor for creating an instance of the DestroyComponentInstance command
     * @param componentAssembler A reference to a ComponentAssembler
     */
    public ListLoadedComponents(ComponentAssembler componentAssembler) {
        this.componentAssembler = componentAssembler;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public void execute(String[] params) {
        for(REComponent reComponent : this.componentAssembler.getComponentStates()){
            System.out.println(reComponent.getComponentName() + " " + reComponent.getComponentVersion());
        }
    }
}
