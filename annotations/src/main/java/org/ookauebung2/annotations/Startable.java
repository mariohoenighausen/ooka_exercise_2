package org.ookauebung2.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Startable-Annotation for a component of the runtime environment
 * @author mariohoenighausen
 * @since 1.0
 * @version 1.0
 */
@Retention (RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Startable {
}
