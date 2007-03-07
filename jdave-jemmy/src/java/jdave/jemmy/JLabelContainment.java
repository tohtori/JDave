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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.netbeans.jemmy.operators.JLabelOperator;

/**
 * @author Pekka Enberg
 */
class JLabelContainment implements ContainerContainment {
    private final String expected;

    public JLabelContainment(String expected) {
        this.expected = expected;
    }

    public boolean isIn(Container container) {
        JLabel label = JLabelOperator.findJLabel(container, expected, true, true);
        return label != null;
    }

    public String error(Container container) {
        List<JLabel> labels = findLabels(container);
        if (labels.isEmpty()) {
            return "Expected label with text \"" + expected + "\", but there are no labels in container.";
        }
        return "Expected label with text \"" + expected + "\", but container has only the following labels: " + format(labels) + ".";
    }

    private List<JLabel> findLabels(Container container) {
        List<JLabel> result = new ArrayList<JLabel>();
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel) {
                result.add((JLabel) component);
            }
        }
        return result;
    }

    private String format(List<JLabel> labels) {
        List<String> result = new ArrayList<String>(); 
        for (JLabel label : labels) {
            result.add(label.getText());
        }
        return result.toString();
    }
}