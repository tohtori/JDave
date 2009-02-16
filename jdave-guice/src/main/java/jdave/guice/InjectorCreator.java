package jdave.guice;

import org.mockito.MockitoAnnotations;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class InjectorCreator {
    public Injector createInjector(final InterestedInGuice interested) {
        return Guice.createInjector(new Module() {
            public void configure(final Binder binder) {
                GuiceAnnotationEngine.binder.set(binder);
                interested.addBindings(binder);
                MockitoAnnotations.initMocks(interested);
                GuiceAnnotationEngine.binder.remove();
            }
        });
    }
}
