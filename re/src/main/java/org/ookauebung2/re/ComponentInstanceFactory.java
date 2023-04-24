package org.ookauebung2.re;

import org.ookauebung2.re.exceptions.NoAnnotatedMethodPresentOnComponentInstance;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * A factory class for instantiating instances of the components
 * @version 1.0
 * @author mariohoenighausen
 */
public class ComponentInstanceFactory {
    private final REComponent component;

    /**
     * Instantiates a new instance of a ComponentInstanceFactory
     * @param component A component to be instantiated
     */
    public ComponentInstanceFactory(REComponent component) {
        this.component = component;
    }

    ComponentRunnerImpl getComponentInstance() throws NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException, NoAnnotatedMethodPresentOnComponentInstance, IOException {
        ComponentLoader componentLoader = component.getComponentLoader();
        Class<?> starterClass = componentLoader.getStartClass();
        Field loggerField = componentLoader.getLogger();

        Object startClassInstance = starterClass.getDeclaredConstructor().newInstance();

        if (loggerField != null) {
            loggerField.setAccessible(true);
            loggerField.set(startClassInstance, new Logger());
            loggerField.setAccessible(false);
        }

        return new ComponentRunnerImpl(componentLoader, startClassInstance);
    }

    /**
     * Returns a component
     * @return REComponent
     */
    public REComponent getComponent() {
        return component;
    }
}
