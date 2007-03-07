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

import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jdave.ExpectationFailedException;
import jdave.Specification;

import org.netbeans.jemmy.JemmyProperties;

/**
 * @author Pekka Enberg
 */
public abstract class JemmyPanelSpecification<T extends JPanel> extends Specification<T> {
    protected T panel;

    @Override
    public void create() {
        initJemmy();
        panel = newPanel();
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.add(panel);
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

    protected abstract T newPanel();

    public void specify(ContainerContainment containment) {
        if (!containment.isIn(panel)) {
            throw new ExpectationFailedException(containment.message());
        }
    }

    public ContainerContainment containsLabel(String expected) {
        return new JLabelContainment<T>(expected);
    }
}
