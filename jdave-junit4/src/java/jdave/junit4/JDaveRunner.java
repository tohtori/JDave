package jdave.junit4;

import jdave.Specification;
import jdave.runner.SpecRunner;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * The JDaveRunner is a JUnit4 test runner implementation for JDave
 * specifications. Just tag your JDave specification class with the
 * <code>@RunWith(JDaveRunner.class)</code> annotation and you're good to go!
 * 
 * @author lkoskela
 */
public class JDaveRunner extends Runner {

    private Class<? extends Specification<?>> spec;

    public JDaveRunner(Class<? extends Specification<?>> spec) {
        this.spec = spec;
    }

    @Override
    public Description getDescription() {
        return DescriptionFactory.create(spec);
    }

    @Override
    public void run(final RunNotifier notifier) {
        Result result = new Result();
        notifier.addListener(result.createListener());
        notifier.fireTestRunStarted(getDescription());
        runJDave(notifier);
        notifier.fireTestRunFinished(result);
    }

    private void runJDave(final RunNotifier notifier) {
        SpecRunner runner = new SpecRunner();
        JDaveCallback callback = new JDaveCallback(notifier);
        runner.run(spec, callback);
        callback.runFinished();
    }
}
