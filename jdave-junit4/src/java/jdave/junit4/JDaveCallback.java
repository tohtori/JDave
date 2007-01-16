package jdave.junit4;

import java.util.Stack;

import jdave.runner.Context;
import jdave.runner.SpecificationMethod;
import jdave.runner.SpecRunner.Callback;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * The JDaveCallback object is responsible for keeping track of test execution
 * through the hierarchy of JDave specifications, contexts, and spec methods. It
 * fires the appropriate events for the given JUnit4 RunNotifier, effectively
 * implementing the core of the JUnit4 integration.
 * 
 * @author lkoskela
 */
public class JDaveCallback implements Callback {
    private Stack<Description> contextStack, methodStack;
    private RunNotifier notifier;

    public JDaveCallback(RunNotifier notifier) {
        this.notifier = notifier;
        contextStack = new Stack<Description>();
        methodStack = new Stack<Description>();
    }

    public void onContext(Context context) {
        if (contextStack.size() > 0) {
            notifier.fireTestFinished(contextStack.pop());
        }

        Description desc = Description.createSuiteDescription(context
                .getName());
        notifier.fireTestStarted(desc);
        contextStack.push(desc);
    }

    public void onSpecMethod(SpecificationMethod method) throws Exception {
        if (methodStack.size() > 0) {
            notifier.fireTestFinished(methodStack.pop());
        }
        final Description desc = Description
                .createSuiteDescription(method.getName());
        notifier.fireTestStarted(desc);
        method.run(new ResultsAdapter(notifier, desc));
        methodStack.push(desc);
    }

    public void runFinished() {
        if (methodStack.size() > 0) {
            notifier.fireTestFinished(methodStack.pop());
        }
        if (contextStack.size() > 0) {
            notifier.fireTestFinished(contextStack.pop());
        }
    }

}
