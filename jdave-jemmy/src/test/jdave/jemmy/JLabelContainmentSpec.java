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
package jdave.jemmy;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

/**
 * @author Pekka Enberg
 */
@RunWith(JDaveRunner.class)
public class JLabelContainmentSpec extends Specification<JLabelContainment> {
    public class EmptyContainer {
        private Container containerMock;
        private JLabelContainment containment;

        public JLabelContainment create() {
            containerMock = mock(Container.class);
            containment = new JLabelContainment("Hello");
            return containment;
        }

        public void doesNotContainLabel() {
            checking(new Expectations() {{
                one(containerMock).getComponents(); will(returnValue(new Component[] { }));
            }});
            specify(containment.error(containerMock), "Expected label with text \"Hello\", but there are no labels in container.");
        }
    }
    
    public class ContainerWithChildrenThatHaveLabels {
        private Container childContainerMock;
        private Container containerMock;
        private JLabelContainment containment;

        public JLabelContainment create() {
            containerMock = mock(Container.class);
            childContainerMock = mock(Container.class);
            containment = new JLabelContainment("Hello");
            return containment;
        }

        public void doesNotContainLabel() {
            checking(new Expectations() {{
                one(containerMock).getComponents(); will(returnValue(new Component[] { childContainerMock }));
                one(childContainerMock).getComponents(); will(returnValue(new Component[] { new JLabel("Text 1"), new JLabel("Text 2") }));
            }});
            specify(containment.error(containerMock), "Expected label with text \"Hello\", but container has only the following labels: [Text 1, Text 2].");
        }
    }
}
