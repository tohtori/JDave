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

import jdave.runner.Behavior;
import jdave.runner.Context;
import jdave.runner.ISpecVisitor;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.notification.RunNotifier;

/**
 * The JDaveCallback object is responsible for keeping track of test execution
 * through the hierarchy of JDave specifications, contexts, and spec methods.
 * It fires the appropriate events for the given JUnit4 RunNotifier,
 * effectively implementing the core of the JUnit4 integration.
 * 
 * @author Lasse Koskela
 * @author Joni Freeman
 */
public class JDaveCallback implements ISpecVisitor {
    private final RunNotifier notifier;
    private Filter filter;

    public JDaveCallback(final RunNotifier notifier) {
        this.notifier = notifier;
    }

    public JDaveCallback(final RunNotifier notifier, final Filter filter) {
        this.notifier = notifier;
        this.filter = filter;
    }

    public void onContext(final Context context) {
    }

    public void afterContext(final Context context) {
    }

    public void onBehavior(final Behavior behavior) {
        final Description desc = DescriptionFactory.newDescription(behavior);
        final boolean shouldRun = shouldRun(desc);
        if (shouldRun) {
            notifier.fireTestStarted(desc);

            try {
                final ResultsAdapter resultsAdapter = new ResultsAdapter(notifier, desc);
                behavior.run(resultsAdapter);
            } finally {
                notifier.fireTestFinished(desc);
            }
        }
    }

    private boolean shouldRun(final Description desc) {
        if (filter != null) {
            final boolean shouldRun = filter.shouldRun(desc);
            return shouldRun;
        }
        return true;
    }
}
