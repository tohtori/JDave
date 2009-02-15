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
package jdave.unfinalizer.internal;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.Properties;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.sun.tools.attach.VirtualMachineDescriptor;

@RunWith(JDaveRunner.class)
public class FilterSpec extends Specification<Filter> {
    public class TheFilter {
        public void canFindCurrentJvm() {
            final JVMHelper jvmHelper = Mockito.mock(JVMHelper.class);
            when(jvmHelper.getCurrentRuntimeName()).thenReturn("jvmName@JDAVE");
            final VirtualMachineDescriptor anotherJvm = Mockito.mock(VirtualMachineDescriptor.class);
            when(anotherJvm.id()).thenReturn("otherJvm");
            final VirtualMachineDescriptor myJvm = Mockito.mock(VirtualMachineDescriptor.class);
            when(myJvm.id()).thenReturn("jvmName");
            when(jvmHelper.virtualMachines()).thenReturn(asList(anotherJvm, myJvm));
            final VirtualMachineDescriptor currentJvm = new Filter().getCurrentJvm(jvmHelper);
            specify(currentJvm, is(myJvm));
        }

        public void canFindUnfinalizerJarInClassPath() {
            final Properties properties = Mockito.mock(Properties.class);
            when(properties.getProperty("java.class.path")).thenReturn("hello#world#/opt/jdave/jdave-unfinalizer-1.2-SNAPSHOT.jar#jdave");
            when(properties.getProperty("path.separator")).thenReturn("#");
            final String unfinalizerJarPath = new Filter().getUnfinalizerJarPath(properties);
            specify(unfinalizerJarPath, is("/opt/jdave/jdave-unfinalizer-1.2-SNAPSHOT.jar"));
        }
    }
}
