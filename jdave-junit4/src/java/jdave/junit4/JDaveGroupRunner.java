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

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jdave.Specification;
import jdave.runner.AnnotatedSpecScanner;
import jdave.runner.Groups;
import jdave.runner.IAnnotatedSpecHandler;
import jdave.runner.Resolution;
import jdave.support.Assert;

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

    public JDaveGroupRunner(Class<?> suite) {
        this.suite = suite;        
    }

    @Override
    public Description getDescription() {
        specs.clear();
        final Description desc = Description.createSuiteDescription(suite);
        final Resolution resolution = new Resolution(suite.getAnnotation(Groups.class));
        newAnnotatedSpecScanner(findSuiteLocation()).forEach(new IAnnotatedSpecHandler() {
            public void handle(String classname, String... groups) {
                if (resolution.includes(Arrays.asList(groups))) {
                    Class<? extends Specification<?>> spec = loadClass(classname);
                    specs.add(spec);
                    desc.addChild(DescriptionFactory.create(spec));
                }
            }
        });
        return desc;
    }
    
    protected AnnotatedSpecScanner newAnnotatedSpecScanner(String suiteLocation) {
        return new AnnotatedSpecScanner(suiteLocation);
    }

    private String findSuiteLocation() {
        String resource = suite.getName().replace('.', '/').concat(".class");
        URL location = suite.getClassLoader().getResource(resource);
        Assert.notNull(location, "can't find suite '" + suite.getName() + "' with classloader");
        String path = location.getPath();
        return path.substring(0, path.lastIndexOf(resource));
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
        for (Class<? extends Specification<?>> spec : specs) {
            new JDaveRunner(spec).run(notifier);
        }
    }
}
