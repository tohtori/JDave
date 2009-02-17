/*
 * Copyright 2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package jdave.guice.internal;

import static org.hamcrest.Matchers.is;
import java.util.List;
import jdave.Specification;
import jdave.guice.GuiceMock;
import jdave.junit4.JDaveRunner;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;

@RunWith(JDaveRunner.class)
public class GuiceAnnotationEngineSpec extends Specification<GuiceAnnotationEngine> {
    private Injector injector;
    private TestClass testClass;

    public class CanInject {
        public void simpleMocks() {
            final InjectionTarget target = injector.getInstance(InjectionTarget.class);
            specify(target.object, is(testClass.object));
        }

        public void constructors() {
            final InjectableConstructor target = injector.getInstance(InjectableConstructor.class);
            specify(target.list, is(testClass.list));
        }

        public void generifiedMocks() {
            final InjectionTarget target = injector.getInstance(InjectionTarget.class);
            specify(target.list, is(testClass.list));
        }

        public void namedMocks() {
            final InjectionTarget target = injector.getInstance(InjectionTarget.class);
            specify(target.namedList, is(testClass.namedList));
        }
    }

    @Override
    public void create() {
        injector = Guice.createInjector(new Module() {
            public void configure(final Binder binder) {
                GuiceAnnotationEngine.binder.set(binder);
                testClass = new TestClass();
                MockitoAnnotations.initMocks(testClass);
            }
        });
    }

    private static class TestClass {
        @GuiceMock
        private List<String> list;
        @GuiceMock
        @Named("hello")
        private List<String> namedList;
        @GuiceMock
        private Object object;
    }

    private static class InjectionTarget {
        @Inject
        private List<String> list;
        @Inject
        @Named("hello")
        private List<String> namedList;
        @Inject
        private Object object;
    }

    private static class InjectableConstructor {
        private final List<String> list;

        @Inject
        public InjectableConstructor(final List<String> list) {
            this.list = list;
        }
    }
}
