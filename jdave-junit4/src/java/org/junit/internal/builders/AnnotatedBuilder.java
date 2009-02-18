/*
 * Copyright 2009 the original author or authors.
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
package org.junit.internal.builders;

import static java.util.Arrays.asList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * <p>
 * Just like AnnotatedBuilder in JUnit4 except it supports running single
 * JDave behaviors.
 * </p>
 * <p>
 * If jdave-junit4 is in your classpath before junit4 then this will be loaded
 * first.
 * </p>
 * <p>
 * When running in your buildtool etc.  you won't care about running a single behavior. 
 * This class should be equivalent to the default JUnit4 one, so in that case it won't matter which one is loaded first.
 * </p>
 * 
 * @author Tuomas Karkkainen
 */
public class AnnotatedBuilder extends RunnerBuilder {
    private static final String CONSTRUCTOR_ERROR_FORMAT = "Custom runner class %1$s should have a public constructor with signature %1$s(Class testClass) or %1$s(Class testClass, RunnerBuilder builder)";

    private final RunnerBuilder suiteBuilder;

    public AnnotatedBuilder(final RunnerBuilder suiteBuilder) {
        this.suiteBuilder = suiteBuilder;
    }

    @Override
    public Runner runnerForClass(final Class<?> testClass) throws Exception {
        final RunWith annotation = getRunWithAnnotation(testClass);
        if (annotation != null) {
            return buildRunner(annotation.value(), testClass);
        }
        return null;
    }

    private RunWith getRunWithAnnotation(final Class<?> testClass) {
        if (testClass.isAnnotationPresent(RunWith.class)) {
            return testClass.getAnnotation(RunWith.class);
        }
        if (testClassIsANonStaticInnerClass(testClass)) {
            return testClass.getDeclaringClass().getAnnotation(RunWith.class);
        }
        return null;
    }

    private boolean testClassIsANonStaticInnerClass(final Class<?> testClass) {
        return testClass.isMemberClass() && !Modifier.isStatic(testClass.getModifiers());
    }

    private <T extends Runner> T buildRunner(final Class<T> runnerClass, final Class<?> testClass)
            throws Exception {
        return new InternalBuilder<T>(runnerClass, suiteBuilder).build(testClass);
    }

    private static class InternalBuilder<T extends Runner> {
        private final RunnerBuilder suiteBuilder;

        private final Class<T> runnerClass;

        public InternalBuilder(final Class<T> runnerClass, final RunnerBuilder suiteBuilder) {
            this.runnerClass = runnerClass;
            this.suiteBuilder = suiteBuilder;
        }

        private T build(final Class<?> testClass) throws Exception {
            if (hasValidConstructor()) {
                return instantiateRunner(testClass);
            }
            throw new InitializationError(String.format(CONSTRUCTOR_ERROR_FORMAT, runnerClass
                    .getSimpleName()));
        }

        private T instantiateRunner(final Class<?> testClass) throws Exception {
            if (hasSimpleConstructor()) {
                return getSimpleConstructor().newInstance(testClass);
            }
            return getConstructorWithSuite(runnerClass).newInstance(testClass, suiteBuilder);
        }

        private boolean hasValidConstructor() {
            return hasSimpleConstructor() || hasConstructorWithSuite();
        }

        private boolean hasSimpleConstructor() {
            return getSimpleConstructor() != null;
        }

        private boolean hasConstructorWithSuite() {
            return getConstructorWithSuite(runnerClass) != null;
        }

        private Constructor<T> getSimpleConstructor() {
            for (final Constructor<T> constructor : getConstructors(runnerClass)) {
                final List<Class<?>> parameters = asList(constructor.getParameterTypes());
                if (parameters.equals(Arrays.<Class<?>> asList(Class.class))) {
                    return constructor;
                }
            }
            return null;
        }

        private Constructor<T> getConstructorWithSuite(final Class<T> runnerClass) {
            for (final Constructor<T> constructor : getConstructors(runnerClass)) {
                final List<Class<?>> parameters = asList(constructor.getParameterTypes());
                if (parameters.equals(Arrays.<Class<?>> asList(Class.class, RunnerBuilder.class))) {
                    return constructor;
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        private List<Constructor<T>> getConstructors(final Class<T> clazz) {
            return asList((Constructor<T>[]) clazz.getConstructors());
        }
    }
}