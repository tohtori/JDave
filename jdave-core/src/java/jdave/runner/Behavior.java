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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.jmock.core.DynamicMockError;

import jdave.ExpectationFailedException;
import jdave.NoContextInitializerSpecifiedException;
import jdave.Specification;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Behavior {
    private final Method method;

    public Behavior(Method method) {
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Specification<?> run(final IBehaviorResults results) throws Exception {
        final Specification<?> spec = newSpecification();
        if (spec.needsThreadLocalIsolation()) {
            runInNewThread(results, spec);
        } else {
            runInCurrentThread(results, spec);
        }
        return spec;
    }

    private void runInCurrentThread(final IBehaviorResults results, final Specification<?> spec) throws Exception {
        runSpec(results, spec);
    }

    private void runInNewThread(final IBehaviorResults results, final Specification<?> spec) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        executor.submit(new Callable<Void>() {
            public Void call() throws Exception {
                runInCurrentThread(results, spec);
                return null;
            }
        });
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }

    private void runSpec(IBehaviorResults results, Specification<?> spec) throws Exception {
        try {
            spec.create();
            Object context = newContext(spec);
            method.invoke(context);
            results.expected(method);
        } catch (InvocationTargetException e) {
            if (e.getCause().getClass().equals(ExpectationFailedException.class)) {
                results.unexpected(method, (ExpectationFailedException) e.getCause());
            } else if (e.getCause().getClass().equals(DynamicMockError.class)) {
                DynamicMockError dynamicMockError = (DynamicMockError) e.getCause();
                /* We have to reset DynamicMock before calling DynamicMockError.getMessage()
                 * or DynamicMockError.printStackTrace(), because otherwise it (or more 
                 * specifically, AbstractDynamicMock.mockInvocation()) will end up re-throwing 
                 * the same failure we're trying to report here and we will miss the real 
                 * error message. 
                 */
                dynamicMockError.dynamicMock.reset();
                results.error(method, dynamicMockError);
            } else {
                results.error(method, e.getCause());
            }
        } catch (NoContextInitializerSpecifiedException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            destroyContext();
            spec.destroy();
        }
    }

    protected abstract Specification<?> newSpecification() throws Exception;
    protected abstract Object newContext(Specification<?> spec) throws Exception;
    protected abstract void destroyContext() throws Exception;
}
