package jdave.guice;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * Annotate fields in your test class with this.
 * </p>
 * <p>
 * Annotated fields will be injected with a Mockito mock, and they will be bound
 * in the Guice injector.
 * </p>
 * <p>
 * You can also use BindingAnnotations, i.e. @Named on your fields.
 * </p>
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface GuiceMock {
}