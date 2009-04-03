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
import jdave.Specification;
import jdave.runner.AnnotatedSpecScanner;
import jdave.runner.Groups;
import jdave.runner.IAnnotatedSpecHandler;
import jdave.runner.Resolution;
import net.sf.cglib.asm.Type;
import net.sf.cglib.asm.attrs.Annotation;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * @author Joni Freeman
 */
public class JDaveGroupRunner extends Runner {
    private final Class<?> suite;
    private final List<Class<? extends Specification<?>>> specs = 
        new ArrayList<Class<? extends Specification<?>>>();
    private Description description;

    public JDaveGroupRunner(Class<?> suite) {
        this.suite = suite;        
        description = Description.createSuiteDescription(suite);
        Groups groupsAnnotationOfSuite = suite.getAnnotation(Groups.class);
        if (groupsAnnotationOfSuite == null) {
            return;
        }
        final Resolution resolution = new Resolution(groupsAnnotationOfSuite);
        scanSpecsFromDisc(description, resolution);
    }

    @Override
    public Description getDescription() {
        return description;
    }

    private void scanSpecsFromDisc(Description desc, Resolution resolution) {
        specs.clear();
        for (String dir : findRootDirs()) {
            scanDir(dir, desc, resolution);
        }
    }

    private void scanDir(String dir, final Description desc, final Resolution resolution) {
        newAnnotatedSpecScanner(dir).forEach(new IAnnotatedSpecHandler() {
            public void handle(String classname, String... groups) {
                if (resolution.includes(Arrays.asList(groups))) {
                    Class<? extends Specification<?>> spec = loadClass(classname);
                    specs.add(spec);
                    desc.addChild(DescriptionFactory.create(spec));
                }
            }
        });
    }

    private List<String> findRootDirs() {
        List<String> rootDirs = new ArrayList<String>();
        if (suite.isAnnotationPresent(SpecDirs.class)) {
            rootDirs.addAll(Arrays.asList(suite.getAnnotation(SpecDirs.class).value()));
        } else {
            CodeSource codeSource = CodeSource.of(suite);
            rootDirs.add(codeSource.getDirectory());
        }
        return rootDirs;
    }
    
    protected AnnotatedSpecScanner newAnnotatedSpecScanner(String suiteLocation) {
        return new AnnotatedSpecScanner(suiteLocation) {
            @Override
            public boolean isInDefaultGroup(String classname, Collection<Annotation> annotations) {
                for (Annotation annotation : annotations) {
                    if (annotation.type.equals("Lorg/junit/runner/RunWith;")) {
                        Type runner = (Type) ((Object[]) annotation.elementValues.get(0))[1];
                        return runner.getClassName().equals(JDaveRunner.class.getName());
                    }
                }
                return false;
            }
        };
    }
    
    @SuppressWarnings("unchecked")
    private Class<? extends Specification<?>> loadClass(String classname) {
        try {
            return (Class<? extends Specification<?>>) Class.forName(classname);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            onBeforeRun();
            runSpecs(notifier);
        } finally {
            onAfterRun();
        }
    }

    private void runSpecs(RunNotifier notifier) {
        for (Class<? extends Specification<?>> spec : specs) {
            new JDaveRunner(spec).run(notifier);
        }
    }
    
    protected void onBeforeRun() {}
    protected void onAfterRun() {}
}
