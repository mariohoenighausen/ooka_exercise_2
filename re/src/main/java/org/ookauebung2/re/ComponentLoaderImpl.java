package org.ookauebung2.re;

import org.ookauebung2.annotations.Injectable;
import org.ookauebung2.annotations.Logging;
import org.ookauebung2.annotations.Startable;
import org.ookauebung2.annotations.Stoppable;
import org.ookauebung2.re.exceptions.NoAnnotatedMethodPresentOnComponentInstance;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
/**
 * A wrapper class for the component loading mechanism
 * @version 1.0
 * @author mariohoenighausen
 */
public class ComponentLoaderImpl implements ComponentLoader {
    private Attributes componentAttributes;
    private final Class<?> componentStarterClass;
    private final Method componentStartMethod;
    private final Method componentStopMethod;
    private final Field logger;
    private final URL[] urls;

    /**
     * Instantiates a ComponentLoaderImpl with a URL array, a componentVersion and a componentName
     *
     * @param urls             The urls of the jarFile of a component as a URL-Array
     */
    public ComponentLoaderImpl(URL[] urls) throws IOException, NoAnnotatedMethodPresentOnComponentInstance {
        try (URLClassLoader urlClassLoader = new URLClassLoader(urls)) {
            String jarPath = urls[0].getPath();
            this.urls = urls;
            setComponentAttributes(urlClassLoader,urls);
            componentStartMethod = findComponentMethod(urlClassLoader, jarPath, Startable.class);
            componentStopMethod = findComponentMethod(urlClassLoader, jarPath, Stoppable.class);
            componentStarterClass = this.componentStartMethod.getDeclaringClass();
            logger = findComponentField(Injectable.class, Logging.class);
        }
    }
    private void setComponentAttributes(URLClassLoader urlClassLoader, URL[] urls) throws IOException{
        URL url = urlClassLoader.findResource("META-INF/MANIFEST.MF");
        Manifest manifest = new Manifest(url.openStream());
        componentAttributes = manifest.getMainAttributes();
    }
    private Method findComponentMethod(URLClassLoader urlClassLoader, String jarPath, Class<? extends Annotation> annotation) throws NoAnnotatedMethodPresentOnComponentInstance, IOException {
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> jarEntries = jarFile.entries();
                        return Collections.list(jarEntries)
                    .stream()
                    .filter(jarEntry -> !jarEntry.isDirectory() && jarEntry.getName()
                            .endsWith(".class"))
                    .map(jarEntry -> jarEntry.getName()
                            .substring(0, jarEntry.getName()
                                    .length() - 6)
                            .replace('/', '.'))
                    .map(jarEntryName -> {
                        try {
                            Class<?> clazz = urlClassLoader.loadClass(jarEntryName);
                            return clazz;
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .map(Class::getMethods)
                    .flatMap(Arrays::stream)
                    .filter(method -> method.isAnnotationPresent(annotation))
                    .findFirst()
                    .orElseThrow(NoAnnotatedMethodPresentOnComponentInstance::new);
        }
    }
    private Field findComponentField(Class<? extends Annotation> annotation, Class<?> fieldType) {
        return Arrays.stream(componentStarterClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(annotation))
                .filter(field -> field.getType().equals(fieldType))
                .findFirst()
                .orElse(null);
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public String getComponentName() {
        return String.valueOf(componentAttributes.getValue("component-name"));
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public String getComponentVersion() {
        return String.valueOf(componentAttributes.getValue("component-version"));
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Method getStartMethod() {
        return componentStartMethod;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Method getStopMethod() {
        return componentStopMethod;
    }

    /**
     * ${@inheritDoc}
     */
    @Override
    public Class<?> getStartClass() {
        return componentStarterClass;
    }
    public ComponentLoader getComponentLoader() throws NoAnnotatedMethodPresentOnComponentInstance, IOException {
        return new ComponentLoaderImpl(urls);
    }
    /**
     * ${@inheritDoc}
     */
    @Override
    public Field getLogger() {
        return logger;
    }
}
