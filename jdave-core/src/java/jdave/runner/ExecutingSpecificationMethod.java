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
import jdave.util.Fields;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
final class ExecutingSpecificationMethod extends SpecificationMethod {
    private final Class<?> contextType;
    private final Class<? extends Specification<?>> specType;
    private Object context;
    
    ExecutingSpecificationMethod(Method method, Class<? extends Specification<?>> specType, Class<?> contextType) {
        super(method);
        this.specType = specType;
        this.contextType = contextType;
    }

    @Override
    protected Specification<?> newSpecification() throws Exception {
        try {
            return specType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Object newContext(Specification<?> spec) throws Exception {
        context = newContextInstance(spec);
        Object contextObject = newContextObject(context);
        Fields.set(spec, "be", contextObject);
        Fields.set(spec, "context", contextObject);
        return context;
    }

    @Override
    protected void destroyContext() throws Exception {
        invokeDisposer(context);
    }
        
    private Object newContextInstance(Specification<?> spec) {
        Object context = null;
        try {
            Constructor<?> constructor = contextType.getDeclaredConstructor(spec.getClass());
            context = constructor.newInstance(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return context;
    }

    private Object newContextObject(Object context) throws Exception {
        Method method = null;
        try {
            method = context.getClass().getMethod(Context.INITIALIZER_NAME);
        } catch (NoSuchMethodException e) {
            throw new NoContextInitializerSpecifiedException(
                    "Initializer missing for " + context.getClass(), e);
        }
        return method.invoke(context);
    }

    private void invokeDisposer(Object context) throws Exception {
        Method method = null;
        try {
            method = context.getClass().getMethod(Context.DISPOSER_NAME);
        } catch (Exception e) {
            /* Disposer is optional so ignore the exception.  */
            return;
        }
        method.invoke(context);
    }
}