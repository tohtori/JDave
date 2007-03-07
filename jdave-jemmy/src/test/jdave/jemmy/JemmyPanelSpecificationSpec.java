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

import javax.swing.JLabel;
import javax.swing.JPanel;

import jdave.Block;
import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

/**
 * @author Pekka Enberg
 */
@RunWith(JDaveRunner.class)
public class JemmyPanelSpecificationSpec extends Specification<JemmyPanelSpecification<JPanel>> {
    public class EmptyPanel {
        private JemmyPanelSpecification<JPanel> spec;

        public JemmyPanelSpecification<JPanel> create() {
            spec = new JemmyPanelSpecification<JPanel>() {
                @Override
                protected JPanel newPanel() {
                    return new JPanel();
                }
            };
            spec.create();
            return spec;
        }

        public void doesNotContainLabel() {
            specify(new Block() {
                public void run() throws Throwable {
                    spec.specify(spec.containsLabel("Hello, Jemmy!"));
                }
            }, raise(ExpectationFailedException.class, "No label with text \"Hello, Jemmy!\" found in panel."));
        }
    }

    public class PanelWithLabel {
        private JemmyPanelSpecification<JPanel> spec;

        public JemmyPanelSpecification<JPanel> create() {
            spec = new JemmyPanelSpecification<JPanel>() {
                @Override
                protected JPanel newPanel() {
                    JPanel panel = new JPanel();
                    panel.add(new JLabel("Hello, Jemmy!"));
                    return panel;
                }
            };
            spec.create();
            return spec;
        }

        public void containsLabelWithExactTextMatch() {
            spec.specify(spec.containsLabel("Hello, Jemmy!"));
        }
        
        public void doesNotContainLabelWithCaseInsensitiveMatch() {
            specify(new Block() {
                public void run() throws Throwable {
                    spec.specify(spec.containsLabel("hello, jemmy!"));
                }
            }, raise(ExpectationFailedException.class, "No label with text \"hello, jemmy!\" found in panel."));
        }
    }
}
