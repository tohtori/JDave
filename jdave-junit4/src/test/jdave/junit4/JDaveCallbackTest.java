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
package jdave.junit4;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import jdave.Specification;
import jdave.runner.Behavior;
import jdave.runner.Context;
import jdave.runner.ExecutingBehavior;
import jdave.runner.IBehaviorResults;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

/**
 * @author Lasse Koskela
 */
public class JDaveCallbackTest extends TestCase {
    private final class FakeBehavior extends ExecutingBehavior {
        private final Class<?> target;
        public boolean allowedToRun = true;

        private FakeBehavior(final Method method, final Class<? extends Specification<?>> specType,
                final Class<?> contextType, final Class<?> target) {
            super(method, specType, contextType);
            this.target = target;
        }

        @Override
        protected void destroyContext() {
        }

        @Override
        protected Object newContext(final Specification<?> spec) throws Exception {
            try {
                return target.newInstance();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected Specification<?> newSpecification() {
            return new StackSpec();
        }

        @Override
        public void run(final IBehaviorResults results) {
            if (allowedToRun) {
                super.run(results);
            } else {
                throw new IllegalStateException("should not run behavior");
            }
        }
    }

    private final class AlwaysFilter extends Filter {
        private final boolean shouldRun;

        public AlwaysFilter(final boolean shouldRun) {
            this.shouldRun = shouldRun;

        }

        @Override
        public String describe() {
            return null;
        }

        @Override
        public boolean shouldRun(final Description description) {
            return shouldRun;
        }
    }

    private final List<String> events = new ArrayList<String>();
    private JDaveCallback callback;
    private RunNotifier notifier;

    @Override
    @Before
    public void setUp() throws Exception {
        notifier = new RunNotifier() {
            @Override
            public void fireTestRunStarted(final Description d) {
                events.add("fireTestRunStarted(" + d.getDisplayName() + ")");
            }

            @Override
            public void fireTestStarted(final Description d) throws StoppedByUserException {
                events.add("fireTestStarted(" + d.getDisplayName() + ")");
            }

            @Override
            public void fireTestFailure(final Failure d) {
            }

            @Override
            public void fireTestFinished(final Description d) {
                events.add("fireTestFinished(" + d.getDisplayName() + ")");
            }

            @Override
            public void fireTestRunFinished(final Result result) {
                events.add("fireTestRunFinished(" + result.getFailureCount() + ")");
            }
        };
        callback = new JDaveCallback(notifier);
    }

    public void testIfFilterIsSetAndItSaysRunThenRun() throws NoSuchMethodException {
        final Behavior method = createSpecMethodByName(getClass(), "testEventOrder");
        callback = new JDaveCallback(notifier, new AlwaysFilter(true));
        callback.onBehavior(method);

    }

    public void testIfFilterIsSetAndItSaysDontRunThenDontRun() throws NoSuchMethodException {
        final FakeBehavior method = createSpecMethodByName(getClass(), "testEventOrder");
        method.allowedToRun = false;
        callback = new JDaveCallback(notifier, new AlwaysFilter(false));
        callback.onBehavior(method);
    }

    @Test
    public void testEventOrder() throws Exception {
        final Context context = new Context(StackSpec.class, StackSpec.EmptyStack.class) {
            @Override
            protected Behavior newBehavior(final Method method,
                    final Class<? extends Specification<?>> specType, final Class<?> contextType) {
                throw new UnsupportedOperationException();
            }
        };
        callback.onContext(context);
        final Behavior method = createSpecMethodByName(getClass(), "testEventOrder");
        callback.onBehavior(method);
    }

    private FakeBehavior createSpecMethodByName(final Class<?> target, final String name)
            throws NoSuchMethodException {
        final FakeBehavior method = new FakeBehavior(target.getDeclaredMethod(name), null,
                Object.class, target);
        return method;
    }

}
