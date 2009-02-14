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

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Properties;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class Unfinalizer {
    public static void unfinalize() {
        try {
            final VirtualMachine jvm = VirtualMachine.attach(getJvm());
            jvm.loadAgent(getJarPath(jvm.getSystemProperties()), null);
            jvm.detach();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static VirtualMachineDescriptor getJvm() {
        final List<VirtualMachineDescriptor> jvms = VirtualMachine.list();
        for (final VirtualMachineDescriptor virtualMachineDescriptor : jvms) {
            final RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
            if (runtimeMxBean.getName().startsWith(virtualMachineDescriptor.id())) {
                return virtualMachineDescriptor;
            }
        }
        throw new RuntimeException("could not find current virtual machine.");
    }

    private static String getJarPath(final Properties properties) {
        final String classPath = properties.getProperty("java.class.path");
        final String[] classPathItems = classPath.split(System.getProperty("path.separator"));
        for (final String classPathItem : classPathItems) {
            if (classPathItem.matches("(.*)jdave-unfinalizer(.*).jar")) {
                return classPathItem;
            }
        }
        throw new RuntimeException("cannot find jdave unfinalizer in class path");
    }
}
