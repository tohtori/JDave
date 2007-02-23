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

import java.util.Stack;

import jdave.Specification;
import jdave.runner.Context;
import jdave.runner.ISpecVisitor;
import jdave.runner.Behavior;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * The JDaveCallback object is responsible for keeping track of test execution
 * through the hierarchy of JDave specifications, contexts, and spec methods. It
 * fires the appropriate events for the given JUnit4 RunNotifier, effectively
 * implementing the core of the JUnit4 integration.
 * 
 * @author Lasse Koskela
 */
public class JDaveCallback implements ISpecVisitor {
    private Stack<Description> contextStack;
    private Stack<Description> behaviorStack;
    private RunNotifier notifier;

    public JDaveCallback(RunNotifier notifier) {
        this.notifier = notifier;
        contextStack = new Stack<Description>();
        behaviorStack = new Stack<Description>();
    }

    public void onContext(Context context) {
        if (contextStack.size() > 0) {
            notifier.fireTestFinished(contextStack.pop());
        }

        Description desc = Description.createSuiteDescription(context.getName());
        notifier.fireTestStarted(desc);
        contextStack.push(desc);
    }

    public void onBehavior(Behavior behavior) throws Exception {
        if (behaviorStack.size() > 0) {
            notifier.fireTestFinished(behaviorStack.pop());
        }
        final Description desc = Description.createSuiteDescription(behavior.getName());
        notifier.fireTestStarted(desc);
        Specification<?> spec = behavior.run(new ResultsAdapter(notifier, desc));
        spec.verify();
        behaviorStack.push(desc);
    }

    public void runFinished() {
        if (behaviorStack.size() > 0) {
            notifier.fireTestFinished(behaviorStack.pop());
        }
        if (contextStack.size() > 0) {
            notifier.fireTestFinished(contextStack.pop());
        }
    }
}
