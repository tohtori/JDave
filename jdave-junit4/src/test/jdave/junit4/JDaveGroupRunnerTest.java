/*
 * Copyright 2007 the original author or authors.
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

import jdave.Group;
import jdave.Specification;
import jdave.runner.AnnotatedSpecScanner;
import jdave.runner.Groups;
import jdave.runner.IAnnotatedSpecHandler;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;


public class JDaveGroupRunnerTest extends MockObjectTestCase {
    private JDaveGroupRunner runner;
    private RunNotifier notifier;

    @Override
    @Before
    protected void setUp() throws Exception {
        setImposteriser(ClassImposteriser.INSTANCE);
        notifier = mock(RunNotifier.class);
        runner = new JDaveGroupRunner(Suite.class) {
            @Override
            protected AnnotatedSpecScanner newAnnotatedSpecScanner(String suiteLocation) {
                return new AnnotatedSpecScanner("") {
                    @Override
                    public void forEach(IAnnotatedSpecHandler annotatedSpecHandler) {
                        annotatedSpecHandler.handle(Spec1.class.getName(), new String[] { "any" });
                        annotatedSpecHandler.handle(Spec2.class.getName(), new String[] { "any" });
                    }
                };
            }
        };
    }
    
    @Test
    public void testRunsBehaviorsFromEachMatchingSpec() throws Exception {
        runner.getDescription();
        checking(new Expectations() {{ 
            exactly(2).of(notifier).fireTestStarted(with(any(Description.class)));
            exactly(2).of(notifier).fireTestFinished(with(any(Description.class)));
        }});
        runner.run(notifier);
    }
    
    @Groups(include={ "any" })
    public static class Suite {
    }
    
    @Group("any")
    public static class Spec1 extends Specification<Void> {
        public class Context {
            public void create() {}
            public void behavior() {}
        }
    }
    
    @Group("any")
    public static class Spec2 extends Specification<Void> {
        public class Context {
            public void create() {}
            public void behavior() {}
        }
    }
}
