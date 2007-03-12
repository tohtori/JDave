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

import java.awt.Container;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

import jdave.ExpectationFailedException;
import jdave.Specification;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JButtonOperator;

/**
 * @author Pekka Enberg
 */
public abstract class JemmyContainerSpecification<T extends Container> extends Specification<T> {
    protected JemmyOperations jemmy = new JemmyOperations();
    protected JFrame frame;
    protected T container;

    @Override
    public void create() {
        initJemmy();
        container = newContainer();
        frame = new JFrame();
        frame.setVisible(true);
        frame.add(container);
    }

    @Override
    public void destroy() {
        frame.dispose();
    }

    protected void initJemmy() {
        InputStream timeouts = getTimeouts();
        try {
            JemmyProperties.getCurrentTimeouts().load(timeouts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getTimeouts() {
        return getClass().getClassLoader().getResourceAsStream("jdave-jemmy.timeouts");
    }

    protected abstract T newContainer();

    public void specify(ContainerContainment containment) {
        if (!containment.isIn(container)) {
            throw new ExpectationFailedException(containment.error(container));
        }
    }

    public ContainerContainment containsLabel(String expected) {
        return new JLabelContainment(expected);
    }

    protected class JemmyOperations {
        public void pushButton(String text) {
            JButton button = JButtonOperator.findJButton(container, text, true, true);
            if (button == null) {
                throw new NoSuchButtonException(text);
            }
            new JButtonOperator(button).push();
        }
    }
}
