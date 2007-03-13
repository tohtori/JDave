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

import jdave.Specification;
import jdave.runner.Behavior;
import jdave.runner.Context;
import jdave.runner.ISpecVisitor;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * The JDaveCallback object is responsible for keeping track of test execution
 * through the hierarchy of JDave specifications, contexts, and spec methods. It
 * fires the appropriate events for the given JUnit4 RunNotifier, effectively
 * implementing the core of the JUnit4 integration.
 * 
 * @author Lasse Koskela
 * @author Joni Freeman
 */
public class JDaveCallback implements ISpecVisitor {
    private RunNotifier notifier;
    
    public JDaveCallback(RunNotifier notifier) {
        this.notifier = notifier;
    }

    public void onContext(Context context) {
    }

    public void onBehavior(Behavior behavior) throws Exception {
        Description desc = Description.createSuiteDescription(behavior.getName());
        notifier.fireTestStarted(desc);
        try {
            Specification<?> spec = behavior.run(new ResultsAdapter(notifier, desc));
            spec.verify();
        } catch (Throwable t) {
            notifier.fireTestFailure(new Failure(desc, t));
        } finally {
            notifier.fireTestFinished(desc);
        }
    }
}
