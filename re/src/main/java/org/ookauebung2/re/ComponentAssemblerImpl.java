package org.ookauebung2.re;

import org.ookauebung2.re.exceptions.ComponentAlreadyLoadedInRE;
import org.ookauebung2.re.exceptions.ComponentInstanceNotFoundInRE;
import org.ookauebung2.re.exceptions.ComponentNotLoadedInRE;
import org.ookauebung2.re.exceptions.NoAnnotatedMethodPresentOnComponentInstance;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class for the component assembly
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
public class ComponentAssemblerImpl implements ComponentAssembler {
    private final Map<String, ComponentInstanceFactory> components;
    private final Map<Long, REComponent> componentInstances;
    private long componentInstanceCount;
    public ComponentAssemblerImpl() throws IOException {
        this.components = new HashMap<>();
        this.componentInstances = new HashMap<>();

    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public REComponent loadComponentIntoRE(String pathToJarFile) throws NoAnnotatedMethodPresentOnComponentInstance, ComponentAlreadyLoadedInRE, NoAnnotatedMethodPresentOnComponentInstance, IOException {
        URL[] urlPath= new URL[]{new File(pathToJarFile).toURI().toURL()};
        REComponent component = new REComponentImpl(urlPath);

        if (this.components.containsKey(component.getIdNameCombinationOfComponent())) {
            throw new ComponentAlreadyLoadedInRE();
        }
        this.components.put(component.getIdNameCombinationOfComponent(), new ComponentInstanceFactory(component));
        return component;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public long startComponentInstance(String name, String version) throws ComponentNotLoadedInRE, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoAnnotatedMethodPresentOnComponentInstance, IOException {
        String componentName = getComponentName(name,version);
        ComponentRunnerImpl componentRunner = components.get(componentName).getComponentInstance();
        componentRunner.start();
        REComponent component = this.components.get(componentName).getComponent();
        component.getComponentInstances().put(componentInstanceCount, componentRunner);
        componentInstances.put(componentInstanceCount, component);
        return componentInstanceCount++;
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public void stopComponentInstance(long instanceId) throws InterruptedException, ComponentInstanceNotFoundInRE, IOException{
        if (!componentInstances.containsKey(instanceId)) {
            throw new ComponentInstanceNotFoundInRE();
        }
        ComponentRunnerImpl componentRunner = componentInstances.get(instanceId).getComponentInstances().get(instanceId);
        componentRunner.stopComponentInstance();
        for (int i = 0; !componentRunner.getState().equals(Thread.State.TERMINATED) && i < 600; i++) {
            Thread.sleep(100);
        }
        destroyComponentInstance(instanceId);
    }
    /**
     * ${@inheritDoc}
     */
    public void destroyComponentInstance(long instanceId) throws ComponentInstanceNotFoundInRE, IOException{
        if (!componentInstances.containsKey(instanceId)) {
            throw new ComponentInstanceNotFoundInRE();
        }
        Map<Long, ComponentRunnerImpl> componentInstances = this.componentInstances.get(instanceId).getComponentInstances();
        componentInstances.get(instanceId).interrupt();
        componentInstances.remove(instanceId);
        this.componentInstances.remove(instanceId);
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public REComponent unloadComponentFromRE(String name, String version) throws ComponentNotLoadedInRE {
        String componentName = getComponentName(name,version);

        REComponent component = this.components
                .get(componentName)
                .getComponent();
        component.getComponentInstances()
                .keySet()
                .forEach(instanceId ->{
            try {
                this.stopComponentInstance(instanceId);
            } catch (Exception | ComponentInstanceNotFoundInRE ex/*InterruptedException | InstanceNotFound IOException e|*/ ) {
                throw new RuntimeException(ex);
            }
        });
        this.components.remove(name);
        return component;
    }
    public Set<REComponent> getComponentStates(){
        return this.components.values().stream().map(ComponentInstanceFactory::getComponent).collect(Collectors.toSet());
    }
    public Map<Long, ComponentRunnerImpl> getComponentInstances(String name, String version){
        return components.get(name + "-" + version).getComponent().getComponentInstances();
    }
    private String getComponentName(String name, String version) throws ComponentNotLoadedInRE {
        String componentName = name + "-" + version;
        if (version == null || version.isEmpty()) {
            componentName = this.components.keySet()
                    .stream()
                    .filter(k -> k.startsWith(name))
                    .findFirst()
                    .orElseThrow(ComponentNotLoadedInRE::new);
        } else if (!this.components.containsKey(componentName)) {
            throw new ComponentNotLoadedInRE();
        }
        return componentName;
    }
}
