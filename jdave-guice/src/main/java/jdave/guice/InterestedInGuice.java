package jdave.guice;

import com.google.inject.Binder;

/**
 * <p>
 * Have your testclass implement this.
 * </p>
 * <p>
 * Then call new InjectorCreator().createInjector(this); before you need the
 * mocks.
 * </p>
 */
public interface InterestedInGuice {
    void addBindings(Binder binder);
}
