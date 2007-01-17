/**
 * 
 */
package jdave.junit4;

import java.lang.reflect.Method;

import jdave.ExpectationFailedException;
import jdave.runner.SpecMethodCallback;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * The ResultsAdapter receives failure notifications from JDave and forwards
 * them to JUnit's RunNotifier.
 * 
 * @author lkoskela
 */
public class ResultsAdapter implements SpecMethodCallback {

    private final RunNotifier notifier;

    private final Description desc;

    ResultsAdapter(RunNotifier notifier, Description desc) {
        this.notifier = notifier;
        this.desc = desc;
    }

    public void error(Method m, Throwable e) {
        notifier.fireTestFailure(new Failure(desc, e));
    }

    public void expected(Method m) {
        // ignored
    }

    public void unexpected(Method m, ExpectationFailedException e) {
        notifier.fireTestFailure(new Failure(desc, e));
    }
}