package jdave.guice;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import jdave.Specification;
import jdave.guice.internal.GuiceAnnotationEngine;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import com.google.inject.Binder;

@RunWith(JDaveRunner.class)
public class InjectorCreatorSpec extends Specification<InjectorCreator> {
    public class Callback {
        public void isCalled() {
            final InterestedInGuice interestedInGuice = Mockito.mock(InterestedInGuice.class);
            new InjectorCreator().createInjector(interestedInGuice);
            verify(interestedInGuice).addBindings(isA(Binder.class));
        }
    }

    public class ThreadLocalBinder {
        public void isRemoved() {
            final InterestedInGuice interestedInGuice = Mockito.mock(InterestedInGuice.class);
            new InjectorCreator().createInjector(interestedInGuice);
            specify(GuiceAnnotationEngine.binder.get(), is(nullValue()));
        }
    }

    public class MockInitialization {
        public void isInvoked() {
            final InjectableClass injectableClass = new InjectableClass();
            new InjectorCreator().createInjector(injectableClass);
            specify(injectableClass.object != null);
        }
    }

    private static class InjectableClass implements InterestedInGuice {
        @GuiceMock
        private Object object;

        public void addBindings(final Binder binder) {
            if (object == null) {
                throw new IllegalStateException("mocks have not yet been injected");
            }
        }
    }
}
