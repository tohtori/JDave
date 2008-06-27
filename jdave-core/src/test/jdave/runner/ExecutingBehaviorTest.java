/*
 * Copyright 2008 the original author or authors.
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

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import jdave.ExpectationFailedException;
import jdave.ResultsAdapter;
import jdave.Specification;
import jdave.mock.MockSupport;
import jdave.runner.ExecutingBehaviorTest.SpecWithCrashingContextDestroy.ContextWithCrashingDestroy;
import org.jmock.Expectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Timo Rantalaiho
 */
public class ExecutingBehaviorTest extends MockSupport {
    private SpecWithCrashingContextDestroy spec = mock(SpecWithCrashingContextDestroy.class);
    private ExecutingBehavior behavior;

    @Before
    public void setUp() throws NoSuchMethodException {
        Method method = ContextWithCrashingDestroy.class.getMethod("behavior");
        behavior = new ExecutingBehavior(method, SpecWithCrashingContextDestroy.class, ContextWithCrashingDestroy.class) {
            @Override
            protected Specification<?> newSpecification() {
                return spec;
            }

            @Override
            protected Object newContext(Specification<?> spec) throws Exception {
                SpecWithCrashingContextDestroy crashingSpec = (SpecWithCrashingContextDestroy) spec;
                return crashingSpec.new ContextWithCrashingDestroy();
            }
        };
    }

    @Test
    public void testCallsSpecDestroyEvenIfContextDestroyCrashes() throws Exception {
        checking(new Expectations() {{
            ignoring(spec).needsThreadLocalIsolation();
            ignoring(spec).create();
            ignoring(spec).fireAfterContextDestroy(with(anything()));
            ignoring(spec).verifyMocks();
            one(spec).destroy();
        }});

        run("java.lang.reflect.InvocationTargetException");
        verifyMocks();
    }

    @Test
    public void testCallsSpecDestroyEvenIfFireAfterContextDestroyCrashes() throws Exception {
        checking(new Expectations() {{
            ignoring(spec).needsThreadLocalIsolation();
            ignoring(spec).create();
            ignoring(spec).fireAfterContextDestroy(with(anything())); will(throwException(new RuntimeException("From after context destroy")));
            ignoring(spec).verifyMocks();
            one(spec).destroy();
        }});

        run("java.lang.RuntimeException: From after context destroy");
        verifyMocks();
    }

    private void run(String expectedError) {
        final Collection<Throwable> occurredErrors = new HashSet<Throwable>();
        behavior.run(new ResultsAdapter() {
            @Override
            public void unexpected(Method method, ExpectationFailedException e) {
                throw new RuntimeException(e);
            }

            @Override
            public void error(Method method, Throwable t) {
                occurredErrors.add(t);
            }
        });
        Assert.assertEquals(1, occurredErrors.size());
        Throwable occurred = occurredErrors.iterator().next();
        Assert.assertEquals(RuntimeException.class, occurred.getClass());
        Assert.assertEquals(expectedError, occurred.getMessage());
    }

    public static class SpecWithCrashingContextDestroy extends Specification<Void> {
        public class ContextWithCrashingDestroy {
            public void destroy() {
                throw new RuntimeException("Exception from context.destroy()");
            }

            public void behavior() {
            }
        }
    }
}
