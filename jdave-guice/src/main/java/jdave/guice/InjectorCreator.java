package jdave.guice;

import jdave.guice.internal.GuiceAnnotationEngine;
import org.mockito.MockitoAnnotations;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * <p>
 * Create an instance of this, and call createInjector(interested) to create
 * mocks and bind them.
 * </p>
 */
public class InjectorCreator {
    public Injector createInjector(final InterestedInGuice interested) {
        return Guice.createInjector(new Module() {
            public void configure(final Binder binder) {
                GuiceAnnotationEngine.binder.set(binder);
                MockitoAnnotations.initMocks(interested);
                interested.addBindings(binder);
                GuiceAnnotationEngine.binder.remove();
            }
        });
    }
}
