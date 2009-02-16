/*
 * Copyright 2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package jdave.unfinalizer.agent;

import java.lang.instrument.Instrumentation;

import jdave.unfinalizer.internal.DelegatingClassFileTransformer;
import jdave.unfinalizer.internal.UnfinalizingClassVisitorDelegator;

/**
 * Adds unfinalizing transformer to instrumentation class loading stack.
 * 
 * @author Tuomas Karkkainen
 */
public class InstrumentationAgent {
    public static void agentmain(@SuppressWarnings("unused")
    final String agentArgs, final Instrumentation instrumentation) {
        addTransformer(instrumentation);
    }

    public static void premain(@SuppressWarnings("unused")
    final String agentArgs, final Instrumentation instrumentation) {
        addTransformer(instrumentation);
    }

    private static void addTransformer(final Instrumentation instrumentation) {
        instrumentation.addTransformer(new DelegatingClassFileTransformer(new UnfinalizingClassVisitorDelegator()));
    }
}
