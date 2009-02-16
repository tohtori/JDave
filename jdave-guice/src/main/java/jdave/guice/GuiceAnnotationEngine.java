package jdave.guice;

import static java.util.Arrays.asList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.configuration.AnnotationEngine;
import com.google.inject.Binder;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;

public class GuiceAnnotationEngine implements AnnotationEngine {
    public static ThreadLocal<Binder> binder = new ThreadLocal<Binder>();

    public Object createMockFor(final Annotation annotation, final Field field) {
        if (annotation instanceof Mock) {
            return Mockito.mock(field.getType(), field.getName());
        }
        if (annotation instanceof GuiceMock) {
            return bindMock(field.getType(), field);
        }
        return null;
    }

    private <T> T bindMock(final Class<T> type, final Field field) {
        final T mock = Mockito.mock(type, field.getName());
        binder.get().bind(getKey(field)).toInstance(mock);
        return mock;
    }

    private Annotation getBindingAnnotation(final Field field) {
        final List<Annotation> annotations = asList(field.getAnnotations());
        for (final Annotation annotation : annotations) {
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(BindingAnnotation.class)) {
                return annotation;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> Key<T> getKey(final Field field) {
        final Annotation bindingAnnotation = getBindingAnnotation(field);
        if (bindingAnnotation != null) {
            return (Key<T>) Key.get(field.getGenericType(), bindingAnnotation);
        }
        return (Key<T>) Key.get(field.getGenericType());
    }
}
