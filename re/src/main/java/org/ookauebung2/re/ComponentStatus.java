package org.ookauebung2.re;

/**
 * A wrapper class for a ComponentStatus of a to be loaded component
 * @since 1.0
 * @author mariohoenighausen
 */
public class ComponentStatus {
    private int componentId;
    private String name;
    private enum States{
     LOADED,
     UNLOADED,
        STARTING,
        STARTED,
        STOPPING,
        STOPPED
    }

    /**
     * Returns a componentId
     * @return The id of a component
     */
    public int getComponentId() {
        return componentId;
    }

    /**
     * Sets a componentId
     * @param componentId The id of a component
     */
    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    /**
     * Returns the name of a component
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of a component
     * @param name A name of the component
     */
    public void setName(String name) {
        this.name = name;
    }

}
