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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import jdave.ExpectationFailedException;
import jdave.NoContextInitializerSpecifiedException;
import jdave.Specification;
import jdave.util.Fields;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public class ExecutingBehavior extends Behavior {
    private final Class<?> contextType;
    private final Class<? extends Specification<?>> specType;
    private Object context;
    
    public ExecutingBehavior(Method method, Class<? extends Specification<?>> specType, Class<?> contextType) {
        super(method);
        this.specType = specType;
        this.contextType = contextType;
    }

    @Override
    public void run(final IBehaviorResults results) {
        Specification<?> spec = newSpecification();
        if (spec.needsThreadLocalIsolation()) {
            runInNewThread(results, spec);
        } else {
            runInCurrentThread(results, spec);
        }
    }

    private void runInCurrentThread(final IBehaviorResults results, final Specification<?> spec) {
        runSpec(results, spec);
    }

    private void runInNewThread(final IBehaviorResults results, final Specification<?> spec) {
        ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                runSpec(results, spec);
                return null;
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // do not mind
        }
    }

    private void runSpec(IBehaviorResults results, Specification<?> spec) {
        try {
            spec.create();
            Object context = newContext(spec);
            method.invoke(context);
            spec.verifyMocks();
            results.expected(method);
        } catch (InvocationTargetException e) {
            if (e.getCause().getClass().equals(ExpectationFailedException.class)) {
                results.unexpected(method, (ExpectationFailedException) e.getCause());
            } else {
                results.error(method, e.getCause());
            }
        } catch (ExpectationFailedException e) {
            results.unexpected(method, (ExpectationFailedException) e.getCause());
        } catch (Throwable t) {
            results.error(method, t.getCause());
        } finally {
            destroyContext();
            spec.destroy();
        }
    }

    protected Specification<?> newSpecification() {
        try {
            return specType.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Object newContext(Specification<?> spec) throws Exception {
        context = newContextInstance(spec);
        spec.fireAfterContextInstantiation(context);
        Object contextObject = newContextObject(context);
        Fields.set(spec, "be", contextObject);
        Fields.set(spec, "context", contextObject);
        spec.fireAfterContextCreation(context, contextObject);
        return context;
    }

    protected void destroyContext() {
        invokeDisposer(context);
    }
        
    private Object newContextInstance(Specification<?> spec) {
        try {
            Constructor<?> constructor = contextType.getDeclaredConstructor(spec.getClass());
            return constructor.newInstance(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object newContextObject(Object context) throws Exception {
        try {
            Method method = context.getClass().getMethod(Context.INITIALIZER_NAME);
            return method.invoke(context);
        } catch (NoSuchMethodException e) {
            throw new NoContextInitializerSpecifiedException(
                    "Initializer missing for " + context.getClass(), e);
        }
    }

    private void invokeDisposer(Object context) {
        try {
            Method method = context.getClass().getMethod(Context.DISPOSER_NAME);
            method.invoke(context);
        } catch (Exception e) {
            /* Disposer is optional so ignore the exception.  */
            return;
        }
    }
}