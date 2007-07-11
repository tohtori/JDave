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
import net.sf.cglib.asm.Attribute;
import net.sf.cglib.asm.ClassAdapter;
import net.sf.cglib.asm.ClassReader;
import net.sf.cglib.asm.ClassWriter;
import net.sf.cglib.asm.attrs.Annotation;
import net.sf.cglib.asm.attrs.Attributes;
import net.sf.cglib.asm.attrs.RuntimeVisibleAnnotations;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunNotifier;


public class JDaveGroupRunnerTest extends MockObjectTestCase {
    private JDaveGroupRunner runner;
    private RunNotifier notifier;

    @Override
    @Before
    protected void setUp() throws Exception {
        setImposteriser(ClassImposteriser.INSTANCE);
        notifier = mock(RunNotifier.class);
    }
    
    @Test
    public void testRunsBehaviorsFromEachMatchingSpec() throws Exception {
        runner = new JDaveGroupRunner(Suite.class) {
            @Override
            protected AnnotatedSpecScanner newAnnotatedSpecScanner(String suiteLocation) {
                return new AnnotatedSpecScanner("") {
                    @Override
                    public void forEach(IAnnotatedSpecHandler annotatedSpecHandler) {
                        annotatedSpecHandler.handle(Spec1.class.getName(), new String[] { "any" });
                        annotatedSpecHandler.handle(Spec2.class.getName(), new String[] { "any" });
                    }
                    
                    @Override
                    public boolean isInDefaultGroup(String classname, Collection<Annotation> annotations) {
                        return false;
                    }
                };
            }
        };
        runner.getDescription();
        checking(new Expectations() {{ 
            exactly(2).of(notifier).fireTestStarted(with(any(Description.class)));
            exactly(2).of(notifier).fireTestFinished(with(any(Description.class)));
        }});
        runner.run(notifier);
    }
    
    @Test
    public void testCategorizesSpecToDefaultGroupIfItHasRunWithJDaveRunnerAnnotation() throws Exception {
        runner = new JDaveGroupRunner(DefaultSpec.class);
        ClassReader reader = new ClassReader(getClass().getResourceAsStream("JDaveGroupRunnerTest$DefaultSpec.class"));
        AnnotationCollector collector = new AnnotationCollector();
        reader.accept(collector, Attributes.getDefaultAttributes(), true);
        assertTrue(runner.newAnnotatedSpecScanner("").isInDefaultGroup("", collector.annotations));
    }
    
    @Test
    public void testDoesNotCategorizeSpecToDefaultGroupIfItDoesNotHaveRunWithAnnotation() throws Exception {
        runner = new JDaveGroupRunner(DefaultSpec.class);
        ClassReader reader = new ClassReader(getClass().getResourceAsStream("JDaveGroupRunnerTest$SpecWithUnrecognizedAnnotation.class"));
        AnnotationCollector collector = new AnnotationCollector();
        reader.accept(collector, Attributes.getDefaultAttributes(), true);
        assertFalse(runner.newAnnotatedSpecScanner("").isInDefaultGroup("", collector.annotations));
    }
    
    public void testUsesDirectoriesSpecifiedBySpecDirsIfAnnotationIsPresent() throws Exception {
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
        runner.getDescription();
        assertEquals(Arrays.asList("foo", "bar"), dirs);
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
