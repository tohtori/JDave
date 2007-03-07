/*
 * Copyright (c) 2007 Reaktor Innovations Oy
 * All rights reserved.
 */
package jdave.jemmy;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;

import jdave.Specification;
import jdave.junit4.JDaveRunner;
import jdave.mock.Mock;

import org.junit.runner.RunWith;

/**
 * @author Pekka Enberg
 */
@RunWith(JDaveRunner.class)
public class JLabelContainmentSpec extends Specification<JLabelContainment> {
    public class EmptyContainer {
        private Mock<Container> containerMock;
        private JLabelContainment containment;

        public JLabelContainment create() {
            containerMock = mock(Container.class);
            containment = new JLabelContainment("Hello");
            return containment;
        }

        public void doesNotContainLabel() {
            containerMock.expects(once()).method("getComponents").will(returnValue(new Component[] { }));
            specify(containment.error(containerMock.proxy()), "Expected label with text \"Hello\", but there are no labels in container.");
        }
    }
    
    public class ContainerWithChildrenThatHaveLabels {
        private Mock<Container> childContainerMock;
        private Mock<Container> containerMock;
        private JLabelContainment containment;

        public JLabelContainment create() {
            containerMock = mock(Container.class);
            childContainerMock = mock(Container.class);
            containment = new JLabelContainment("Hello");
            return containment;
        }

        public void doesNotContainLabel() {
            containerMock.expects(once()).method("getComponents").will(returnValue(new Component[] { childContainerMock.proxy() }));
            childContainerMock.expects(once()).method("getComponents").will(returnValue(new Component[] { new JLabel("Text 1"), new JLabel("Text 2") }));
            specify(containment.error(containerMock.proxy()), "Expected label with text \"Hello\", but container has only the following labels: [Text 1, Text 2].");
        }
    }
}
