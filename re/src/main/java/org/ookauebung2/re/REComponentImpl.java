package org.ookauebung2.re;

import org.ookauebung2.re.exceptions.NoAnnotatedMethodPresentOnComponentInstance;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for the component wrapper
 * @version 1.0
 * @author mariohoenighausen
 */
public class REComponentImpl implements REComponent {
    private final URL[] urlPath;
    private final Map<Long, ComponentRunnerImpl> instances;
    private final String componentVersion;
    private final String componentName;

    /**
     * ${@inheritDoc}
     */
    public REComponentImpl(URL[] urlPath) throws NoAnnotatedMethodPresentOnComponentInstance, IOException {
        this.urlPath = urlPath;
        this.instances = new HashMap<>();
        ComponentLoader componentLoader = new ComponentLoaderImpl(urlPath);
        this.componentVersion = componentLoader.getComponentVersion();
        this.componentName = componentLoader.getComponentName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, ComponentRunnerImpl> getComponentInstances() {
        return instances;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getURLPath() {
        return urlPath[0].toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdNameCombinationOfComponent() {
        return componentName + "-" + componentVersion;
    }

    /**
     * @return
     */
    @Override
    public String getComponentName() {
        return componentName;
    }

    /**
     * @return
     */
    @Override
    public String getComponentVersion() {
        return componentVersion;
    }

    public ComponentLoader getComponentLoader() throws NoAnnotatedMethodPresentOnComponentInstance,IOException{
        return new ComponentLoaderImpl(urlPath);
    }
}