/*
 * Copyright 2009 the original author or authors.
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
package jdave.unfinalizer;

import jdave.unfinalizer.internal.Filter;
import jdave.unfinalizer.internal.JVMHelper;

import com.sun.tools.attach.VirtualMachine;

public class Unfinalizer {
    private static boolean initialized;

    /**
     * Removes final from classes and methods loaded <strong>after</strong> this
     * is called.
     * <p>
     * Removing finals after a class has been loaded is not supported by the
     * JVM. In a serial environment, such as running within your build tool, you
     * currently have to use something silly like what I did in sample project
     * if another test uses the classes you want to unfinalize before
     * unfinalizer first runs.
     * 
     */
    public synchronized static void unfinalize() {
        if (!initialized) {
            try {
                final Filter filter = new Filter();
                final VirtualMachine jvm = VirtualMachine.attach(filter.getCurrentJvm(new JVMHelper()));
                jvm.loadAgent(filter.getUnfinalizerJarPath(jvm.getSystemProperties()));
                jvm.detach();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }
        initialized = true;
    }
}
