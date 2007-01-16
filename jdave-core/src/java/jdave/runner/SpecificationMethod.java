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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jdave.ExpectationFailedException;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class SpecificationMethod {
    public interface Results {
        void expected(Method method);
        void unexpected(Method method, ExpectationFailedException e);
        void error(Method method, Throwable t);
    }

    private final Method method;

    public SpecificationMethod(Method method) {
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public void run(Results results) throws Exception {
        Object context = newContext();
        try {
            method.invoke(context);
            results.expected(method);
        } catch (InvocationTargetException e) {
            if (e.getCause().getClass().equals(ExpectationFailedException.class)) {
                results.unexpected(method, (ExpectationFailedException) e.getCause());
            } else {
                results.error(method, e.getCause());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            destroyContext(context);
        }
    }

    protected abstract Object newContext() throws Exception;
    protected abstract void destroyContext(Object context);
}
