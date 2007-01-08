/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave.runner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import jdave.NoContextInitializerSpecifiedException;
import jdave.Specification;
import jdave.runner.SpecRunner.Callback;
import jdave.util.Fields;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public class Context {
    private static final String INITIALIZER_NAME = "create";

    private final Class<? extends Specification<?>> specType;
    private final Class<?> contextType;

    public Context(Class<? extends Specification<?>> specType, Class<?> contextType) {
        this.specType = specType;
        this.contextType = contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }

    void run(Callback callback) {
        for (Method method : contextType.getMethods()) {
            if (isSpecificationMethod(method)) {
                run(method, callback);
            }
        }
    }

    private void run(Method method, Callback callback) {
        final Specification<?> spec = newSpecification();
        callback.onSpecMethod(spec, new SpecificationMethod(method) {
            @Override
            protected Object newContext() {
                Object context = newContextInstance(spec);
                Object contextObject = newContextObject(context);
                Fields.set(spec, "be", contextObject);
                return context;
            }
        });
    }

    private boolean isSpecificationMethod(Method method) {
        if (method.getDeclaringClass().equals(Object.class)) {
            return false;
        }
        if (method.getName().equals(INITIALIZER_NAME)) {
            return false;
        }
        return true;
    }

    private Specification<?> newSpecification() {
        try {
            return specType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object newContextInstance(Specification<?> spec) {
        Object context;
        try {
            Constructor<?> constructor = contextType.getDeclaredConstructor(spec.getClass());
            context = constructor.newInstance(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    private Object newContextObject(Object context) {
        try {
            Method method = context.getClass().getMethod(INITIALIZER_NAME);
            return method.invoke(context);
        } catch (Exception e) {
            throw new NoContextInitializerSpecifiedException("Initializer missing for "
                    + context.getClass(), e);
        }
    }
}
