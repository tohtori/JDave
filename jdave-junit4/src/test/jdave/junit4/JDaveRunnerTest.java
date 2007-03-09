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

import java.util.ArrayList;
import java.util.List;

import jdave.junit4.specs.DiverseSpec;
import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

/**
 * @author Lasse Koskela
 */
public class JDaveRunnerTest extends TestCase {
    private NarrativeCreatingRunNotifier notifier;

    @Override
    @Before
    public void setUp() {
        notifier = new NarrativeCreatingRunNotifier();
        JDaveRunner runner = new JDaveRunner(DiverseSpec.class);
        runner.run(notifier);
    }

    @Test
    public void testFailingSpecMethodsAreRecordedWithFailure() throws Exception {
        shouldHaveHappened("fireTestStarted:fails(jdave.junit4.specs.DiverseSpec$FirstContext)");
        shouldHaveHappened("fireTestFailure:fails(jdave.junit4.specs.DiverseSpec$FirstContext)");
        shouldHaveHappened("fireTestFinished:fails(jdave.junit4.specs.DiverseSpec$FirstContext)");
        shouldHaveHappened("fireTestStarted:throwsException(jdave.junit4.specs.DiverseSpec$SecondContext)");
        shouldHaveHappened("fireTestFailure:throwsException(jdave.junit4.specs.DiverseSpec$SecondContext)");
        shouldHaveHappened("fireTestFinished:throwsException(jdave.junit4.specs.DiverseSpec$SecondContext)");
    }

    @Test
    public void testPassingSpecMethodsAreRecordedWithoutFailure() throws Exception {
        shouldHaveHappened("fireTestStarted:passes(jdave.junit4.specs.DiverseSpec$FirstContext)");
        shouldHaveHappened("fireTestFinished:passes(jdave.junit4.specs.DiverseSpec$FirstContext)");
        shouldNotHaveHappened("fireTestFailure:passes(jdave.junit4.specs.DiverseSpec$FirstContext)");
    }

    private void shouldHaveHappened(String event) {
        Assert.assertTrue("Expected event '" + event + "' didn't happen!", eventHappened(event));
    }

    private boolean eventHappened(String event) {
        return notifier.events.contains(event);
    }

    private void shouldNotHaveHappened(String event) {
        String msg = "Event '" + event + "' happened against our expectation!";
        Assert.assertFalse(msg, eventHappened(event));
    }

    class NarrativeCreatingRunNotifier extends RunNotifier {
        public List<String> events = new ArrayList<String>();

        @Override
        public void fireTestRunStarted(Description description) {
            events.add("fireTestRunStarted:" + description.getDisplayName());
        }

        @Override
        public void fireTestStarted(Description description) throws StoppedByUserException {
            events.add("fireTestStarted:" + description.getDisplayName());
        }

        @Override
        public void fireTestFinished(Description description) {
            events.add("fireTestFinished:" + description.getDisplayName());
        }

        @Override
        public void fireTestFailure(Failure failure) {
            events.add("fireTestFailure:" + failure.getDescription().getDisplayName());
        }

        @Override
        public void fireTestIgnored(Description description) {
            throw new RuntimeException("fireTestIgnored shouldn't be used!");
        }

        @Override
        public void fireTestRunFinished(Result result) {
            events.add("fireTestRunFinished");
        }
    }
}
