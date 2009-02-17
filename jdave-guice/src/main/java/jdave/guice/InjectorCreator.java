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
