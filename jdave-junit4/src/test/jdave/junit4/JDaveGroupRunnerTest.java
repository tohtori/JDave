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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import jdave.Group;
import jdave.Specification;
import jdave.runner.AnnotatedSpecScanner;
import jdave.runner.Groups;
import jdave.runner.IAnnotatedSpecHandler;
import junit.framework.Assert;
import net.sf.cglib.asm.Attribute;
import net.sf.cglib.asm.ClassAdapter;
import net.sf.cglib.asm.ClassReader;
import net.sf.cglib.asm.ClassWriter;
import net.sf.cglib.asm.attrs.Annotation;
import net.sf.cglib.asm.attrs.Attributes;
import net.sf.cglib.asm.attrs.RuntimeVisibleAnnotations;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;


@RunWith(JMock.class)
public class JDaveGroupRunnerTest {
    private Mockery context = new JUnit4Mockery();
    private JDaveGroupRunner runner;
    private RunNotifier notifier;

    @Before
    public void setUp() throws Exception {
        context.setImposteriser(ClassImposteriser.INSTANCE);
        notifier = context.mock(RunNotifier.class);
    }
    
    @Test
    public void runsAfterOperationsEvenIfBeforeOperationsFail() {
        final SuiteGroupRunner suiteGroupRunner = new SuiteGroupRunner();
        try {
            suiteGroupRunner.run(notifier);
            Assert.fail("Should have thrown RuntimeException.");
        } catch (RuntimeException e) {}
        Assert.assertTrue(suiteGroupRunner.onAfterRunCalled);
    }
    
    private class SuiteGroupRunner extends JDaveGroupRunner {
        private boolean onAfterRunCalled = false;
        
        public SuiteGroupRunner() {
            super(Suite.class);
        }
        
        @Override
        public void onBeforeRun() {
            throw new RuntimeException();
        }
        
        @Override
        public void onAfterRun() {
            onAfterRunCalled = true;
        }
    }
    
    @Test
    public void runsBehaviorsFromEachMatchingSpec() throws Exception {
        runner = new JDaveGroupRunner(Suite.class) {
            @Override
            protected AnnotatedSpecScanner newAnnotatedSpecScanner(String suiteLocation) {
                return new AnnotatedSpecScanner("") {
                    @Override
                    public void forEach(IAnnotatedSpecHandler annotatedSpecHandler) {
                        annotatedSpecHandler.handle(Spec1.class.getName(), "any");
                        annotatedSpecHandler.handle(Spec2.class.getName(), "any");
                    }
                    
                    @Override
                    public boolean isInDefaultGroup(String classname, Collection<Annotation> annotations) {
                        return false;
                    }
                };
            }
        };
        context.checking(new Expectations() {{ 
            exactly(2).of(notifier).fireTestStarted(with(any(Description.class)));
            exactly(2).of(notifier).fireTestFinished(with(any(Description.class)));
        }});
        runner.run(notifier);
    }
    
    @Test
    public void categorizesSpecToDefaultGroupIfItHasRunWithJDaveRunnerAnnotation() throws Exception {
        runner = new JDaveGroupRunner(DefaultSpec.class);
        ClassReader reader = new ClassReader(getClass().getResourceAsStream("JDaveGroupRunnerTest$DefaultSpec.class"));
        AnnotationCollector collector = new AnnotationCollector();
        reader.accept(collector, Attributes.getDefaultAttributes(), true);
        Assert.assertTrue(runner.newAnnotatedSpecScanner("").isInDefaultGroup("", collector.annotations));
    }
    
    @Test
    public void doesNotCategorizeSpecToDefaultGroupIfItDoesNotHaveRunWithAnnotation() throws Exception {
        runner = new JDaveGroupRunner(DefaultSpec.class);
        ClassReader reader = new ClassReader(getClass().getResourceAsStream("JDaveGroupRunnerTest$SpecWithUnrecognizedAnnotation.class"));
        AnnotationCollector collector = new AnnotationCollector();
        reader.accept(collector, Attributes.getDefaultAttributes(), true);
        Assert.assertFalse(runner.newAnnotatedSpecScanner("").isInDefaultGroup("", collector.annotations));
    }

    @Test
    public void usesDirectoriesSpecifiedBySpecDirsIfAnnotationIsPresent() throws Exception {
        final List<String> dirs = new ArrayList<String>();
        runner = new JDaveGroupRunner(SuiteWithSpecifiedDirs.class) {
            @Override
            protected AnnotatedSpecScanner newAnnotatedSpecScanner(String suiteLocation) {
                dirs.add(suiteLocation);
                return new AnnotatedSpecScanner(suiteLocation) {
                    @Override
                    public void forEach(IAnnotatedSpecHandler annotatedSpecHandler) {
                    }
                    @Override
                    public boolean isInDefaultGroup(String classname, Collection<Annotation> annotations) {
                        return false;
                    }
                };
            }
        };
        Assert.assertEquals(Arrays.asList("foo", "bar"), dirs);
    }
    
    private static class AnnotationCollector extends ClassAdapter {
        List<Annotation> annotations;
        
        public AnnotationCollector() {
            super(new ClassWriter(false));
        }

        @Override
        @SuppressWarnings("unchecked")
        public void visitAttribute(Attribute attr) {
            if (attr instanceof RuntimeVisibleAnnotations) {
                annotations = ((RuntimeVisibleAnnotations) attr).annotations;
            }
        }
    }
    
    @RunWith(JDaveGroupRunner.class)
    @Groups(include="any")
    @SpecDirs({"foo", "bar"})
    public static class SuiteWithSpecifiedDirs {        
    }

    @RunWith(JDaveRunner.class)
    public static class DefaultSpec extends Specification<Void> {
        public class Context {
            public void create() {}
            public void behavior() {}
        }
    }
    
    @Ignore
    public static class SpecWithUnrecognizedAnnotation extends Specification<Void> {
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
